package com.awin.coffeebreak.controller;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import com.awin.coffeebreak.repository.StaffMemberRepository;
import com.awin.coffeebreak.services.CoffeeBreakPreferenceService;
import com.awin.coffeebreak.services.SlackNotifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CoffeeBreakPreferenceController {

    //public CoffeeBreakPreferenceRepository coffeeBreakPreferenceRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CoffeeBreakPreferenceService coffeeBreakPreferenceService;
    public StaffMemberRepository staffMemberRepository;
    /*
    public CoffeeBreakPreferenceController(
          CoffeeBreakPreferenceRepository coffeeBreakPreferenceRepository
    ) {
        this.coffeeBreakPreferenceRepository = coffeeBreakPreferenceRepository;
    }
    */
    @Autowired
    public CoffeeBreakPreferenceController(
            CoffeeBreakPreferenceService coffeeBreakPreferenceService
    ) {
        this.coffeeBreakPreferenceService = coffeeBreakPreferenceService;
    }
    /**
     * Publishes the list of preferences in the requested format
     */
    @GetMapping(path = "/today")
    @Qualifier("CoffeeBreakService")
    public ResponseEntity<?> today(@RequestParam("format") String format) {
        log.debug("/today" + ": format: "+format);
        if (format == null) {
            format = "html";
        }

        List<CoffeeBreakPreference> t = coffeeBreakPreferenceService.getPreferencesForToday();

        String responseContent;
        String contentType = "text/html";

        switch (format) {
            case "json":
                responseContent = getJsonForResponse(t);
                contentType = "application/json";
                break;

            case "xml":
                responseContent = getXmlForResponse(t);
                contentType = "text/xml";
                break;

            default:
                String formattedPreferences = getHtmlForResponse(t);
                return ResponseEntity.ok().contentType(MediaType.valueOf(contentType))
                      .body(formattedPreferences);
        }

        return ResponseEntity.ok()
              .contentType(MediaType.valueOf(contentType))
              .body(responseContent);
    }

    @GetMapping("/notifyStaffMember")
    @Qualifier("CoffeeBreakService")
    public ResponseEntity<Object> notifyStaffMember(@RequestParam("staffMemberId") int id) {
        Optional<StaffMember> staffMember = this.staffMemberRepository.findById(id);

        List<CoffeeBreakPreference> preferences = new ArrayList<>();

        SlackNotifier notifier = new SlackNotifier();
        boolean ok = notifier.notifyStaffMember(staffMember.get(), preferences);

        return ResponseEntity.ok(ok ? "OK" : "NOT OK");
    }

    private String getJsonForResponse(final List<CoffeeBreakPreference> list) {
        String responseJson = "{\"preferences\":[";
        log.debug("getJsonForResponse");
        for (final CoffeeBreakPreference p : list) {
            log.debug("JSON:for loop");
            responseJson += coffeeBreakPreferenceService.getAsJson(p);
        }

        return responseJson += "]}";
    }

    private String getXmlForResponse(List<CoffeeBreakPreference> list) {
        String responseJson = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        responseJson += "<Preferences>";

        for (final CoffeeBreakPreference p : list) {
            responseJson += coffeeBreakPreferenceService.getAsXml(p);
        }

        responseJson += "</Preferences>";

        return responseJson;
    }

    private String getHtmlForResponse(final List<CoffeeBreakPreference> list) {
        String responseJson = "<ul>";

        for (final CoffeeBreakPreference p : list) {
            responseJson += coffeeBreakPreferenceService.getAsListElement(p);
        }

        return responseJson + "</ul>";
    }


    @PostMapping("/addPreference")
    @Qualifier("CoffeeBreakService")
    public void addCoffeeBreakPreference(@RequestBody CoffeeBreakPreference coffeeBreakPreference){
        coffeeBreakPreferenceService.addCoffeeBreakPreference(coffeeBreakPreference);
    }
}
