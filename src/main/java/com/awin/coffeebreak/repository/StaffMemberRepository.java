package com.awin.coffeebreak.repository;

import com.awin.coffeebreak.entity.StaffMember;
import com.awin.coffeebreak.entity.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface StaffMemberRepository extends CrudRepository<StaffMember, Integer> {
    List<StaffMember> findByTeam(Team team);
    List<StaffMember> findStaffMemberByTeam(int teamId);
}
