package com.awin.coffeebreak.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Team")
public class Team {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long Id;
    @OneToMany(mappedBy = "team")
    private List<StaffMember> staff;
    @Column
    private String TeamName;
    @Column
    private String ContactPersonName;
    @Column
    private String ContactEmail;
    @Column
    private String Location;

    public Team(){}

    public Long getId() {
        return this.Id != null ? this.Id : 0;
    }

    public String getTeamName() {
        return TeamName;
    }

    public String getContactPersonName() {
        return ContactPersonName;
    }

    public String getContactEmail() {
        return ContactEmail;
    }

    public String getLocation() {
        return Location;
    }

    public void setId(int id){
        this.Id = Long.valueOf(id);
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public void setContactPersonName(String contactPersonName) {
        ContactPersonName = contactPersonName;
    }

    public void setContactEmail(String contactEmail) {
        ContactEmail = contactEmail;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
