package com.awin.coffeebreak.services;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.repository.CoffeeBreakPreferenceRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
@Service
public class CoffeeBreakPreferenceService {

    //@Autowired // dependency injection, when an instance of this service class is created, an instance of CoffeeBreakPreferenceRepository will be injected to it
    private final CoffeeBreakPreferenceRepository coffeeBreakPreferenceRepository;

    public CoffeeBreakPreferenceService(CoffeeBreakPreferenceRepository coffeeBreakPreferenceRepository) {
        this.coffeeBreakPreferenceRepository = coffeeBreakPreferenceRepository;
    }
    //private Object CoffeeBreakPreference;

    public List<CoffeeBreakPreference> getPreferencesForToday(){
        List<CoffeeBreakPreference> cf = new ArrayList<>();
        //return coffeeBreakPreferenceRepository.getPreferencesForToday();
        coffeeBreakPreferenceRepository.findByRequestedDateBetween(Instant.now().truncatedTo(ChronoUnit.DAYS),
                Instant.now().truncatedTo(ChronoUnit.DAYS).plus(1, ChronoUnit.DAYS)).forEach(cf::add);
        return cf;
    }
    public List<CoffeeBreakPreference> findByRequestedDateBetween(Instant start, Instant end){
        List<CoffeeBreakPreference> cp = new ArrayList<>();
        coffeeBreakPreferenceRepository.findByRequestedDateBetween(start, end).forEach(cp::add);
        return cp;
    }
    public List<CoffeeBreakPreference> getAllPreference(){
        List<CoffeeBreakPreference> cp = new ArrayList<>();
        coffeeBreakPreferenceRepository.findAll().forEach(cp::add);
        return cp;
    }
    public void addCoffeeBreakPreference(CoffeeBreakPreference coffeeBreakPreference) {
        coffeeBreakPreferenceRepository.save(coffeeBreakPreference);
    }
}
