package com.awin.coffeebreak.repository;


import com.awin.coffeebreak.entity.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Integer> {
    List<Team> findAll();
    List<Team> findTeamByTeamNameEquals(String TeamName);
}
