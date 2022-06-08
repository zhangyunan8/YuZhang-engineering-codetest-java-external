package com.awin.coffeebreak.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.*;

@Entity
@Table(name = "coffee_break_preference")
public class CoffeeBreakPreference {
    //private final Logger log = LoggerFactory.getLogger(this.getClass());
    public static List<String> TYPES = List.of("food", "drink");
    public static List<String> DRINK_TYPES = List.of("coffee", "tea");
    public static List<String> FOOD_TYPES = List.of("sandwich", "crisps", "toast");

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column
    private Long id;

    @Column
    private String type;

    @Column
    private String subType;

    @JsonBackReference // stop infinite recursion on bidirectional relationship
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "staff_Id")
    private StaffMember requestedBy;

    @Column
    private Instant requestedDate;

    //JPA 2 implementation
    @ElementCollection
    @Column
    private Map<String, String> details;

    public CoffeeBreakPreference(
            final int id, final String type, final String subType, final StaffMember requestedBy, final Map<String, String> details
    ) {
        setId(id);
        if (!TYPES.contains(type)) {
            throw new IllegalArgumentException();
        }
        if (type == "food") {
            if (!FOOD_TYPES.contains(subType)) {
                throw new IllegalArgumentException();
            }
        } else {
            if (!DRINK_TYPES.contains(subType)) {
                throw new IllegalArgumentException();
            }
        }

        this.type = type;
        //save subtype
        setSubType(subType);
        //set current date
        //setRequestedDate(Instant.now().truncatedTo(ChronoUnit.DAYS));
        setRequestedDate(Instant.now());
        this.requestedBy = requestedBy;
        //debug: check if details is null as well
        if(details != null && !details.isEmpty()) {
            setDetails(details);
        } else {
            setDetails(new HashMap<>());
        }
    }

    public CoffeeBreakPreference() {

    }

    public String getType() {
        return type;
    }
    public void setId(final int id) {
        this.id = Long.valueOf(id);
    }
    public int getId(){
        //JDK 11 compiler automatically convert Integer to int, but, thought it would be good to keep the old fashion
        return this.id != null ? this.id.intValue() : 0;
    }
    public void setType(final String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(final String subType) {
        this.subType = subType;
    }

    public StaffMember getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(final StaffMember requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Instant getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(final Instant requestedDate) {
        this.requestedDate = requestedDate;
    }

    public void setDetails(final Map<String, String> details) {
        this.details = details;
    }

    public Map<String, String> getDetails() {
        return details;
    }
/*
    public String getAsJson() {
        System.out.println("id: " + id + "; getId: "+ getId() +" type: " + type + ", details: " +details.toString());
        return "{" +
                "\"id\":" + id +
                ", \"type\":\"" + type + '"' +
                ", \"subType\":\"" + subType + '"' +
                ", \"requestedBy\":\"" + requestedBy.getName() + '"' + // changed to get name, requestedBy is an object
                ", \"requestedDate\":\"" + requestedDate + '"' +
                ", \"details\":\"" + details.toString() + '"' + // convert Map to string
                '}';
    }

    public String getAsXml() {
        return "<preference type=\""+type+"\" subtype=\""+subType+"\">" +
                "<requestedBy>"+requestedBy.getName()+"</requestedBy>" +
                "<details>"+details.toString()+"</details>" +
                "</preference>";
    }

    public String getAsListElement() {
        final String detailsString = details.keySet().stream()
                .map(e -> e + " : " + details.get(e))
                .collect(Collectors.joining(","));

        return "<li>" + requestedBy.getName() + " would like a " + subType + ". (" + detailsString + ")</li>";
    }

 */
}
