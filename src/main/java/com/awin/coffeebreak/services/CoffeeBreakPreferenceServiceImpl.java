package com.awin.coffeebreak.services;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import com.awin.coffeebreak.repository.CoffeeBreakPreferenceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("CoffeeBreakService")
//@Qualifier("CoffeeBreakService")
public class CoffeeBreakPreferenceServiceImpl implements CoffeeBreakPreferenceService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
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
    public void saveJson(String payload) {
        System.out.println("save payload: " +payload);
        ObjectMapper mapper = new ObjectMapper();
        CoffeeBreakPreference coffeeBreakPreference = new CoffeeBreakPreference();
        try {
            coffeeBreakPreference = mapper.readValue(payload, CoffeeBreakPreference.class);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("save payload: something wrong!");
        }
        if (coffeeBreakPreference.getSubType().isEmpty()) {
            System.out.println("preference is empty!");
        }
        else {
            coffeeBreakPreferenceRepository.save(coffeeBreakPreference);
        }
    }
    public void addOnePre(Map<String,Object> map){
        CoffeeBreakPreference cf = new CoffeeBreakPreference();
        StaffMember person = new StaffMember();
        Map<String,Object> requestedBy = (Map<String,Object>)map.get("requestedBy");
        person.setName((String)requestedBy.get("name"));
        person.setEmail((String)requestedBy.get("email"));
        person.setSlackIdentifier((String)requestedBy.get("slackIdentifier"));
        Map<String,String> details = (Map<String,String>)map.get("details");
        String type = (String)map.get("type");
        String subType = (String)map.get("subType");
        Instant requestedDate = Instant.parse((String)map.get("requestedDate"));

        cf.setType(type);
        cf.setSubType(subType);
        cf.setRequestedDate(requestedDate);
        cf.setDetails(details);
        cf.setRequestedBy(person);

        coffeeBreakPreferenceRepository.save(cf);
        notify(person.getSlackIdentifier(), person.getEmail(), cf, person);
        }
    private void notify(String slackId, String email, CoffeeBreakPreference cf, StaffMember person){
        boolean flag = false;
        if (slackId == "" || slackId.isEmpty()){
            //send email
            Emailer mailer = new Emailer();
            flag = mailer.Send("somebody@awin.com", "another@awin.com", "onemore@awin.com", "subject", "hello, hello");
        }
        else {
            //slack
            List<CoffeeBreakPreference> l = new ArrayList<>();
            l.add(cf);
            SlackNotifier slack = new SlackNotifier();
            flag = slack.notifyStaffMember(person, l);

        }
        if (!flag){ // do something to let user knows
             }

    }
 public void getAllPreferenceContent(Content content, String format){
        List<CoffeeBreakPreference> t = getAllPreference();
        if (format == null) {
            format = "html";
        }
        getContent(format, t, content);
    }

    public void getPreferencesForTodayContent(Content content, String format){
        if (format == null) {
            format = "html";
        }

        List<CoffeeBreakPreference> t = getPreferencesForToday();

        getContent(format, t, content);
    }
    public List<CoffeeBreakPreference> findCoffeeBreakPreferenceByTeam(String teamName, Content content, String format){
        List<CoffeeBreakPreference> cp = new ArrayList<>();
        coffeeBreakPreferenceRepository.findCoffeeBreakPreferenceByTeam(teamName).forEach(cp::add);
        return cp;
    }
    public void getCoffeeBreakPreferenceByTeam(String teamName, Content content, String format){
        if (format == null) {
            format = "html";
        }

        List<CoffeeBreakPreference> t = findCoffeeBreakPreferenceByTeam(teamName, content, format);

        getContent(format, t, content);
    }
    public void getContent(String format, List<CoffeeBreakPreference> t, Content content) {

        switch (format) {
            case "json":
                content.setResponseContent(getJsonForResponse(t));
                content.setContentType("application/json");
                break;

            case "xml":
                content.setResponseContent(getXmlForResponse(t));
                content.setContentType("text/xml");
                break;

            default:
                content.setResponseContent(getHtmlForResponse(t));
                content.setContentType("text/html");

        }

    }
    private String getJsonForResponse(final List<CoffeeBreakPreference> list) {
        String responseJson = "{\"preferences\":[";
        log.debug("getJsonForResponse");
        for (final CoffeeBreakPreference p : list) {
            log.debug("JSON:for loop");
            responseJson += getAsJson(p);
        }

        return responseJson += "]}";
    }

    private String getXmlForResponse(List<CoffeeBreakPreference> list) {
        String responseJson = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        responseJson += "<Preferences>";

        for (final CoffeeBreakPreference p : list) {
            responseJson += getAsXml(p);
        }

        responseJson += "</Preferences>";

        return responseJson;
    }

    private String getHtmlForResponse(final List<CoffeeBreakPreference> list) {
        String responseJson = "<ul>";

        for (final CoffeeBreakPreference p : list) {
            responseJson += getAsListElement(p);
        }

        return responseJson + "</ul>";
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
