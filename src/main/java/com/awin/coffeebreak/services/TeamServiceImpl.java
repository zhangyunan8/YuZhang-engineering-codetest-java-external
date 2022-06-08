package com.awin.coffeebreak.services;


import com.awin.coffeebreak.entity.Team;

import com.awin.coffeebreak.repository.TeamRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.io.IOException;


import java.util.ArrayList;
import java.util.List;


@Service("TeamService")
public class TeamServiceImpl implements TeamService{
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    //@Autowired
    // dependency injection, when an instance of this service class is created, an instance of TeamRepository will be injected to it
    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
    public List<Team> getByTeamName(String teamName){
        List<Team> teams = new ArrayList<Team>();
        teamRepository.findTeamByTeamNameEquals(teamName).forEach(teams::add);
        return teams;
    }

    @Override
    public void findByTeamName(Content content, String format, String teamName) {
        List<Team> t = getByTeamName(teamName);
        if (format == null) {
            format = "html";
        }
        getContent(format, t, content);
    }

    public List<Team> getAll(){
        List<Team> cp = new ArrayList<>();
        teamRepository.findAll().forEach(cp::add);
        return cp;
    }
    public void findAll(Content content, String format){
        List<Team> t = getAll();
        if (format == null) {
            format = "html";
        }
        getContent(format, t, content);
    }
    //

    public void getContent(String format, List<Team> t, Content content) {

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
    private String getJsonForResponse(final List<Team> list) {
        String responseJson = "{\"preferences\":[";
        log.debug("getJsonForResponse");
        for (final Team p : list) {
            log.debug("JSON:for loop");
            responseJson += getAsJson(p);
        }

        return responseJson += "]}";
    }

    private String getXmlForResponse(List<Team> list) {
        String responseJson = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        responseJson += "<Preferences>";

        for (final Team p : list) {
            responseJson += getAsXml(p);
        }

        responseJson += "</Preferences>";

        return responseJson;
    }

    private String getHtmlForResponse(final List<Team> list) {
        String responseJson = "<ul>";

        for (final Team p : list) {
            responseJson += getAsListElement(p);
        }

        return responseJson + "</ul>";
    }

    public String getAsJson(Team Team) {
        System.out.println("getId: "+ Team.getId() +" type: " + Team.getTeamName() + ", details: " +Team.getContactEmail().toString());
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(Team);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return jsonString;

    }

    public String getAsXml(Team Team) {
        return "<team Team_name=\""+Team.getTeamName()+"\" id=\""+Team.getId()+"\">" +
                "<location>"+Team.getLocation()+"</requestedBy>" +
                "<contact_email>"+Team.getContactEmail()+"</details>" +
                "<contact_person_name>"+Team.getContactPersonName()+"</details>" +
                "</preference>";
    }

    public String getAsListElement(Team Team) {
        
        return "<li>" + Team.getTeamName() + ": location = " + Team.getLocation() + ", contact_email = " + Team.getContactEmail() + "contact_person_name = "+ Team.getContactPersonName() + "</li>";
    }

}
