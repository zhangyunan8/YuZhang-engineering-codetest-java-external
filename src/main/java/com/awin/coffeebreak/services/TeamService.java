package com.awin.coffeebreak.services;

import com.awin.coffeebreak.entity.Team;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface TeamService {
    public List<Team> getByTeamName(String teamName);
    public void findByTeamName(Content content, String format, String teamName);

    public List<Team> getAll();
    public void findAll(Content content, String format);
}
