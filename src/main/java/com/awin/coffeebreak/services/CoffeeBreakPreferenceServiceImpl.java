package com.awin.coffeebreak.services;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.repository.CoffeeBreakPreferenceRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
@Service("CoffeeBreakService")
//@Qualifier("CoffeeBreakService")
public class CoffeeBreakPreferenceServiceImpl implements CoffeeBreakPreferenceService {

    //@Autowired // dependency injection, when an instance of this service class is created, an instance of CoffeeBreakPreferenceRepository will be injected to it
    private final CoffeeBreakPreferenceRepository coffeeBreakPreferenceRepository;

    public CoffeeBreakPreferenceServiceImpl(CoffeeBreakPreferenceRepository coffeeBreakPreferenceRepository) {
        this.coffeeBreakPreferenceRepository = coffeeBreakPreferenceRepository;
    }
    //private Object CoffeeBreakPreference;
    @Override
    public List<CoffeeBreakPreference> getPreferencesForToday(){
        List<CoffeeBreakPreference> cf = new ArrayList<>();
        System.out.println("service start:" + Instant.now() +"");
        System.out.println("service end:" + Instant.now().truncatedTo(ChronoUnit.DAYS).plus(1, ChronoUnit.DAYS) +"");
        coffeeBreakPreferenceRepository.getPreferencesForToday(Instant.now().truncatedTo(ChronoUnit.DAYS),
                Instant.now().truncatedTo(ChronoUnit.DAYS).plus(1, ChronoUnit.DAYS)).forEach(cf::add);
        return cf;
    }
    @Override
    public List<CoffeeBreakPreference> findByRequestedDateBetween(Instant start, Instant end){
        List<CoffeeBreakPreference> cp = new ArrayList<>();
        coffeeBreakPreferenceRepository.findByRequestedDateBetween(start, end).forEach(cp::add);
        return cp;
    }
    @Override
    public List<CoffeeBreakPreference> getAllPreference(){
        List<CoffeeBreakPreference> cp = new ArrayList<>();
        coffeeBreakPreferenceRepository.findAll().forEach(cp::add);
        return cp;
    }

    public void addCoffeeBreakPreference(CoffeeBreakPreference coffeeBreakPreference) {
        coffeeBreakPreferenceRepository.save(coffeeBreakPreference);
    }
}
