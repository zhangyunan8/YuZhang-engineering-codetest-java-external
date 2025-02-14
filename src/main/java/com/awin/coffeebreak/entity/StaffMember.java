package com.awin.coffeebreak.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "staff_member")
public class StaffMember {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE) // for the issue of "ids for this class must be manually assigned before calling save()"
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String slackIdentifier;

    @JsonManagedReference // for infinite recursion
    @OneToMany(mappedBy="requestedBy") //requestedBy
    private List<CoffeeBreakPreference> coffeeBreakPreferences = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "team_Id")
    private Team team;

    // add a constructor here in order to be able to initialize a staffmember
    public StaffMember(){}
    public StaffMember(String name, String email, String slackId){
        setName(name);
        setEmail(email);
        setSlackIdentifier(slackId);
        //setCoffeeBreakPreferences(coffeeBreakPreferences);
    }
    public int getId() {
        return this.id != null ? this.id.intValue() : 0;
    }

    public void setId(int id) {
        this.id = Long.valueOf(id);
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
