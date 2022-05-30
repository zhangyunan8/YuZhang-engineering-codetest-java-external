package com.awin.coffeebreak.entity;

import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.*;

@Entity
@Table(name = "coffee_break_preference")
public class CoffeeBreakPreference {

    public static List<String> TYPES = List.of("food", "drink");
    public static List<String> DRINK_TYPES = List.of("coffee", "tea");
    public static List<String> FOOD_TYPES = List.of("sandwich", "crisps", "toast");

    @Id
    Integer id;

    @Column
    String type;

    @Column
    String subType;

    @ManyToOne
    StaffMember requestedBy;

    @Column
    Instant requestedDate;

    //JPA 2 implementation
    @ElementCollection
    @Column
    Map<String, String> details;

    public CoffeeBreakPreference(
            final String type, final String subType, final StaffMember requestedBy, final Map<String, String> details
    ) {
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

        this.requestedBy = requestedBy;
        if(!details.isEmpty()) {
            setDetails(details);
        } else {
            setDetails(new HashMap<>());
        }
    }

    public String getType() {
        return type;
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

    public String getAsJson() {
        return "{" +
                "\"id\":" + id +
                ", \"type\":\"" + type + '"' +
                ", \"subType\":\"" + subType + '"' +
                ", \"requestedBy\":\"" + requestedBy + '"' +
                ", \"requestedDate\":\"" + requestedDate + '"' +
                ", \"details\":\"" + details + '"' +
                '}';
    }

    public String getAsXml() {
        return "<preference type=\""+type+"\" subtype=\""+subType+"\">" +
                "<requestedBy>"+requestedBy+"</requestedBy>" +
                "<details>"+details+"</details>" +
                "</preference>";
    }

    public String getAsListElement() {
        final String detailsString = details.keySet().stream()
                .map(e -> e + " : " + details.get(e))
                .collect(Collectors.joining(","));

        return "<li>" + requestedBy.getName() + " would like a " + subType + ". (" + detailsString + ")</li>";
    }
}
