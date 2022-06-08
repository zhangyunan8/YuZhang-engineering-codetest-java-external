package com.awin.coffeebreak.controller;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import com.awin.coffeebreak.repository.StaffMemberRepository;
import com.awin.coffeebreak.services.CoffeeBreakPreferenceService;
import com.awin.coffeebreak.services.Content;
import com.awin.coffeebreak.services.SlackNotifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.awin.coffeebreak.services.TeamService;
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
    private final TeamService teamService;
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
            CoffeeBreakPreferenceService coffeeBreakPreferenceService,
            TeamService teamService) {
        this.coffeeBreakPreferenceService = coffeeBreakPreferenceService;
        this.teamService = teamService;
    }

    @GetMapping(path = "/CoffeeBreakPreference")
    @Qualifier("CoffeeBreakService")
    public ResponseEntity<?> findAll(@RequestParam("format") String format){
        Content content = new Content("", "");
        coffeeBreakPreferenceService.getAllPreferenceContent(content, format);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(content.getContentType()))
                .body(content.getResponseContent());
    }
    @GetMapping(path = "/CoffeeBreakPreference/{name}")
    @Qualifier("CoffeeBreakService")
    public ResponseEntity<?> findAll(@RequestParam("format") String format, @PathVariable("name") String teamName){
        Content content = new Content("", "");
        coffeeBreakPreferenceService.getCoffeeBreakPreferenceByTeam(teamName, content, format);

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
    @PostMapping("/addOnePreference")
    @Qualifier("CoffeeBreakService")
    public void addPreForOne(@RequestBody Map<String,Object> map){
        coffeeBreakPreferenceService.addOnePre(map);

    }

    // Team endpoints
    @GetMapping("/team")
    @Qualifier("TeamService")
    public ResponseEntity<?> findAllTeams(@RequestParam("format") String format){
        Content content = new Content("", "");
        teamService.findAll(content, format);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(content.getContentType()))
                .body(content.getResponseContent());
    }
    @GetMapping("/team/{name}")
    @Qualifier("TeamService")
    public ResponseEntity<?> findCoffeeBreakPreferenceByTeam(@PathVariable("name") String teamName, @RequestParam("format") String format){
        Content content = new Content("", "");

            teamService.findByTeamName(content, format, teamName);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(content.getContentType()))
                .body(content.getResponseContent());
    }
}
