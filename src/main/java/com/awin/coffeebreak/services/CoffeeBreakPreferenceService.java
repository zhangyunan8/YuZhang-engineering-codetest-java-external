package com.awin.coffeebreak.services;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;


import java.time.Instant;
import java.util.List;

public interface  CoffeeBreakPreferenceService {

    public List<CoffeeBreakPreference> getPreferencesForToday();
    public List<CoffeeBreakPreference> findByRequestedDateBetween(Instant start, Instant end);
    public List<CoffeeBreakPreference> getAllPreference();
    public void addCoffeeBreakPreference(CoffeeBreakPreference coffeeBreakPreference);
    public String getAsJson(CoffeeBreakPreference coffeeBreakPreference);
    public String getAsXml(CoffeeBreakPreference coffeeBreakPreference);
    public String getAsListElement(CoffeeBreakPreference coffeeBreakPreference);
}
