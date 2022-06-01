package com.awin.coffeebreak.repository;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

// for dependency injection

public interface CoffeeBreakPreferenceRepository extends CrudRepository<CoffeeBreakPreference, Integer> {

    List<CoffeeBreakPreference> findByRequestedDateBetween(Instant start, Instant end);
    /** the implementation should not be here, move to implementation service class **/
    /*
    default List<CoffeeBreakPreference> getPreferencesForToday() {
        return getPreferencesForToday(
              Instant.now().truncatedTo(ChronoUnit.DAYS),
              Instant.now().truncatedTo(ChronoUnit.DAYS).plus(1, ChronoUnit.DAYS)
        );
    }
    */
    // the following query no longer working, and we have a better replacement of findby between  for this porpose
    /*
    @Query("select p from CoffeeBreakPreference p " +
          "where p.requestedDate > :start " +
          "and p.requestedDate < :end")
    List<CoffeeBreakPreference> getPreferencesForToday(
          @Param("start") Instant start,
          @Param("end") Instant end
    );
    */

}
