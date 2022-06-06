package com.awin.coffeebreak.services;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.repository.CoffeeBreakPreferenceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    public String getAsJson(CoffeeBreakPreference coffeeBreakPreference) {
        System.out.println("getId: "+ coffeeBreakPreference.getId() +" type: " + coffeeBreakPreference.getType() + ", details: " +coffeeBreakPreference.getDetails().toString());
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(coffeeBreakPreference);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return jsonString;
        /*
        return "{" +
                "\"id\":" + coffeeBreakPreference.getId() +
                ", \"type\":\"" + coffeeBreakPreference.getType() + '"' +
                ", \"subType\":\"" + coffeeBreakPreference.getSubType() + '"' +
                ", \"requestedBy\":\"" + coffeeBreakPreference.getRequestedBy().getName() + '"' + // changed to get name, requestedBy is an object
                ", \"requestedDate\":\"" + coffeeBreakPreference.getRequestedDate() + '"' +
                ", \"details\":\"" + coffeeBreakPreference.getDetails().toString() + '"' + // convert Map to string
                '}';

         */
    }

    public String getAsXml(CoffeeBreakPreference coffeeBreakPreference) {
        return "<preference type=\""+coffeeBreakPreference.getType()+"\" subtype=\""+coffeeBreakPreference.getSubType()+"\">" +
                "<requestedBy>"+coffeeBreakPreference.getRequestedBy().getName()+"</requestedBy>" +
                "<details>"+coffeeBreakPreference.getDetails().toString()+"</details>" +
                "</preference>";
    }

    public String getAsListElement(CoffeeBreakPreference coffeeBreakPreference) {
        final String detailsString = coffeeBreakPreference.getDetails().keySet().stream()
                .map(e -> e + " : " + coffeeBreakPreference.getDetails().get(e))
                .collect(Collectors.joining(","));

        return "<li>" + coffeeBreakPreference.getRequestedBy().getName() + " would like a " + coffeeBreakPreference.getSubType() + ". (" + detailsString + ")</li>";
    }
}
