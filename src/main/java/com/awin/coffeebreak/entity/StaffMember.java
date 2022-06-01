package com.awin.coffeebreak.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "staff_member")
public class StaffMember {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // for the issue of "ids for this class must be manually assigned before calling save()"
    Integer id;

    @Column
    String name;

    @Column
    String email;

    @Column
    String slackIdentifier;

    //why ?
    @OneToMany
    List<CoffeeBreakPreference> coffeeBreakPreferences = new ArrayList<>();

    // add a constructor here in order to be able to initialize a staffmember
    public StaffMember(){}
    public StaffMember(String name, String email, String slackId){
        setName(name);
        setEmail(email);
        setSlackIdentifier(slackId);
        //setCoffeeBreakPreferences(coffeeBreakPreferences);
    }
    public Integer getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getSlackIdentifier() {
        return slackIdentifier;
    }

    public void setSlackIdentifier(final String slackIdentifier) {
        this.slackIdentifier = slackIdentifier;
    }

    public List<CoffeeBreakPreference> getCoffeeBreakPreferences() {
        return coffeeBreakPreferences;
    }

    public void setCoffeeBreakPreferences(
          final List<CoffeeBreakPreference> coffeeBreakPreferences
    ) {
        this.coffeeBreakPreferences = coffeeBreakPreferences;
    }
}
