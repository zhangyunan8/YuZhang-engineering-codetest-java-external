package com.awin.coffeebreak;
import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import com.awin.coffeebreak.services.CoffeeBreakPreferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
//@SpringBootApplication
public class TestAwinCoffeeBreakApplication {

    private static final Logger log = LoggerFactory.getLogger(TestAwinCoffeeBreakApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(TestAwinCoffeeBreakApplication.class, args);
    }
	@Bean
	public CommandLineRunner commandLineRunner(CoffeeBreakPreferenceService coffeeBreakPreferenceService) {
		return args -> {
            StaffMember staff = new StaffMember("Tester", "tester@somewhere.com", "ABC234");
            //staff.setSlackIdentifier("ABC123");
            CoffeeBreakPreference mycp = new CoffeeBreakPreference(1,"drink", "tea", staff, Map.of("cup","1", "size","Large"));
			System.out.println("TestAwinCoffeeBreakApplication starts:");
            System.out.println("add some CoffeeBreakPreferences");
            //coffeeBreakPreferenceService.addCoffeeBreakPreference(new CoffeeBreakPreference(1,"drink", "tea", staff, Map.of("cup","1", "size","Large")));
            coffeeBreakPreferenceService.addCoffeeBreakPreference(new CoffeeBreakPreference(2, "drink", "tea", staff, Map.of("cup","1", "size","Small")));
            coffeeBreakPreferenceService.addCoffeeBreakPreference(new CoffeeBreakPreference(3, "drink", "coffee", staff, Map.of("cup","1", "size","large", "coffee","black")));
            /*  */
            System.out.println("coffee break preference for findByRequestedDateBetween: ");
            for (CoffeeBreakPreference coffeeBreakPreference : coffeeBreakPreferenceService.findByRequestedDateBetween(Instant.now().truncatedTo(ChronoUnit.DAYS),
                    Instant.now().truncatedTo(ChronoUnit.DAYS).plus(1, ChronoUnit.DAYS))) {
                System.out.println(coffeeBreakPreference.getType().toString() + ", " + coffeeBreakPreference.getSubType().toString() + ", " + coffeeBreakPreference.getRequestedDate()+ " .");
                System.out.println(coffeeBreakPreference.toString());
            }

            System.out.println("getPreferencesForToday: ");
            System.out.println(coffeeBreakPreferenceService.getPreferencesForToday());
            /*for (CoffeeBreakPreference coffeeBreakPreference : coffeeBreakPreferenceService.getPreferencesForToday()){
                System.out.println(coffeeBreakPreference.getType().toString() + ", " + coffeeBreakPreference.getSubType().toString() + ", " + coffeeBreakPreference.getRequestedDate()+ " .");
                System.out.println(coffeeBreakPreference.toString());
            }*/
            System.out.println("StaffMember");
            System.out.println(staff.getId() + " " + staff.getName());
            System.out.println(mycp.getType() + " " +mycp.getSubType() + ", " + mycp.getRequestedDate() + " .");
            System.out.println("adding end");


		};
	}
}
