package com.awin.coffeebreak.services;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import com.awin.coffeebreak.repository.CoffeeBreakPreferenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CoffeeBreakPreferenceServiceImplTest {

    @Autowired
    CoffeeBreakPreferenceService coffeeBreakPreferenceService;
    private CoffeeBreakPreference mycp;
    private StaffMember staff;

    @BeforeEach
    void setUp() throws Exception {
        staff = new StaffMember("Tester", "tester@somewhere.com", "ABC234");
        mycp = new CoffeeBreakPreference(1,"drink", "tea", staff, Map.of("cup","1", "size","Large"));


    }
    // Mockito is unable to mock for-each
    /*
    @InjectMocks
    CoffeeBreakPreferenceServiceImpl coffeeBreakPreferenceService;

    //@Autowired
    @Mock
    CoffeeBreakPreferenceRepository coffeeBreakPreferenceRepository;

    @BeforeEach
    void setUp() throws Exception {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPreferencesForToday() {
        StaffMember staff = new StaffMember("Tester", "tester@somewhere.com", "ABC234");
        CoffeeBreakPreference mycp = new CoffeeBreakPreference(1,"drink", "tea", staff, Map.of("cup","1", "size","Large"));
        List<CoffeeBreakPreference> coffeeList = new ArrayList<CoffeeBreakPreference>();
        coffeeList.add(mycp);


        when(coffeeBreakPreferenceService.getPreferencesForToday()).thenReturn(coffeeList);
        int response = coffeeBreakPreferenceService.getPreferencesForToday().size();

        assertEquals(1,response);
    }

     */


    @Test
    void getPreferencesForToday() {
        coffeeBreakPreferenceService.addCoffeeBreakPreference(mycp);
        int response = coffeeBreakPreferenceService.getPreferencesForToday().size();
        assertEquals(1, response);
    }
    @Test
    void findByRequestedDateBetween() {
        coffeeBreakPreferenceService.addCoffeeBreakPreference(mycp);
        coffeeBreakPreferenceService.addCoffeeBreakPreference(new CoffeeBreakPreference(2, "drink", "tea", staff, Map.of("cup","1", "size","Small")));
        int response = coffeeBreakPreferenceService.findByRequestedDateBetween(Instant.now().truncatedTo(ChronoUnit.DAYS),
                Instant.now().truncatedTo(ChronoUnit.DAYS).plus(1, ChronoUnit.DAYS)).size();
        assertEquals(2, response);
    }

    @Test
    void getAllPreference() {
        coffeeBreakPreferenceService.addCoffeeBreakPreference(mycp);
        coffeeBreakPreferenceService.addCoffeeBreakPreference(new CoffeeBreakPreference(2, "drink", "tea", staff, Map.of("cup","1", "size","Small")));
        coffeeBreakPreferenceService.addCoffeeBreakPreference(new CoffeeBreakPreference(3, "drink", "coffee", staff, Map.of("cup","1", "size","large", "coffee","black")));

        int response = coffeeBreakPreferenceService.getAllPreference().size();
        assertEquals(3, response);
    }

    @Test
    void addCoffeeBreakPreference() {
        int response = coffeeBreakPreferenceService.getAllPreference().size();
        assertEquals(0, response);
        coffeeBreakPreferenceService.addCoffeeBreakPreference(mycp);
        response = coffeeBreakPreferenceService.getAllPreference().size();
        assertEquals(1, response);

    }


}