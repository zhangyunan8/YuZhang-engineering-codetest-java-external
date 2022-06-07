package com.awin.coffeebreak.controller;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import com.awin.coffeebreak.repository.StaffMemberRepository;
import com.awin.coffeebreak.services.CoffeeBreakPreferenceService;
import com.awin.coffeebreak.services.Content;
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

    @GetMapping(path = "/findAll")
    @Qualifier("CoffeeBreakService")
    public ResponseEntity<?> findAll(@RequestParam("format") String format){
        Content content = new Content("", "");
        coffeeBreakPreferenceService.getAllPreferenceContent(content, format);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(content.getContentType()))
                .body(content.getResponseContent());
    }
    /**
     * Publishes the list of preferences in the requested format
     */
    @GetMapping(path = "/today")
    @Qualifier("CoffeeBreakService")
    public ResponseEntity<?> today(@RequestParam("format") String format) {
        log.debug("/today" + ": format: "+format);
        Content content = new Content("", "");
        coffeeBreakPreferenceService.getPreferencesForTodayContent(content, format);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(content.getContentType()))
                .body(content.getResponseContent());
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


    @PostMapping("/addPreference")
    @Qualifier("CoffeeBreakService")
    public void addCoffeeBreakPreference(@RequestBody String payload){
        coffeeBreakPreferenceService.saveJson(payload);
    }
}
