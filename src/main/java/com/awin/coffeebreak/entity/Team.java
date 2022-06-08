package com.awin.coffeebreak.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Team")
public class Team {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column
    private Long Id;
    @OneToMany(mappedBy = "team")
    private List<StaffMember> staff;
    @Column
    private String teamName;
    @Column
    private String contactPersonName;
    @Column
    private String contactEmail;
    @Column
    private String location;

    public Team(){}

    public Long getId() {
        return this.Id != null ? this.Id : 0;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public String getContactPersonName() {
        return this.contactPersonName;
    }

    public String getContactEmail() {
        return this.contactEmail;
    }

    public String getLocation() {
        return this.location;
    }

    public void setId(int id){
        this.Id = Long.valueOf(id);
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
