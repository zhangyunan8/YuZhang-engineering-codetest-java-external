package com.awin.coffeebreak.repository;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

// for dependency injection

public interface CoffeeBreakPreferenceRepository extends CrudRepository<CoffeeBreakPreference, Integer> {

    List<CoffeeBreakPreference> findByRequestedDateBetween(Instant start, Instant end);
    List<CoffeeBreakPreference> findAll();
    /*
    List<CoffeeBreakPreference> findCoffeeBreakPreferenceByTeam(@Param("teamName") String teamName, Pageable pageable);
    From client, such as GUI can pass the following parameters for paging
        .param("page", "5")
        .param("size", "10")
        .param("sort", "id,desc")   // <-- no space after comma!
        .param("sort", "name,asc")) // <-- no space after comma!
        .andExpect(status().isOk())
     http://localhost:8080/team/sales?page=2&size=50&sort=id,desc
     */
    //@Query(value="select t.team_name, s.name, s.email, s.slack_identifier, c.sub_type, c.type, c.requested_date from coffee_break_preference c, staff_member s, team t where c.staff_id = s.Id and s.team_id = t.Id and t.team_name = :teamName",
    //    nativeQuery = true)
    @Query("select p from CoffeeBreakPreference p where p.requestedBy = (select s from StaffMember s where s.team = (select t from Team t where t.teamName= :teamName)) ")
    List<CoffeeBreakPreference> findCoffeeBreakPreferenceByTeam(@Param("teamName") String teamName);
    /** the implementation should not be here, move to implementation service class **/
    /*
    default List<CoffeeBreakPreference> getPreferencesForToday() {
        return getPreferencesForToday(
              Instant.now().truncatedTo(ChronoUnit.DAYS),
              Instant.now().truncatedTo(ChronoUnit.DAYS).plus(1, ChronoUnit.DAYS)
        );
    }
    */
    //Native Query with indexed parameters
    /*
    @Query(
        value = "select * from CoffeeBreakPreference p where p.requestedDate >= :start and p.requestedDate < :end,
        nativeQuery = true)
    List<CoffeeBreakPreference> getPreferencesForToday(
          @Param("start") Instant start,
          @Param("end") Instant end
    );
     */
    // with named parameters
    /*

    @Query(
        value = "select * from CoffeeBreakPreference p where p.requestedDate >= ?1 and p.requestedDate < ?2,
        nativeQuery = true)
    List<CoffeeBreakPreference> getPreferencesForToday(Instant start, Instant end);
     */
    // having examinated closely, the following JPQL needs to change from "> :start" to ">= :start" in order to provide full coverage

    @Query("select p from CoffeeBreakPreference p " +
          "where p.requestedDate >= :start " +
          "and p.requestedDate < :end")
    List<CoffeeBreakPreference> getPreferencesForToday(
          @Param("start") Instant start,
          @Param("end") Instant end
    );

    // pagination
    /*
    @Query("select p from CoffeeBreakPreference p " +
          "where p.requestedDate >= :start " +
          "and p.requestedDate < :end")
    Page<CoffeeBreakPreference> getPreferencesForTodayByPage(
          @Param("start") Instant start,
          @Param("end") Instant end,
          Pageable pageable
    );
     */
    /*
    From client, such as GUI can pass the following parameters for paging
        .param("page", "5")
        .param("size", "10")
        .param("sort", "id,desc")   // <-- no space after comma!
        .param("sort", "name,asc")) // <-- no space after comma!
        .andExpect(status().isOk())
     http://localhost:8080/today?page=2&size=50&sort=id,desc
     */
}
