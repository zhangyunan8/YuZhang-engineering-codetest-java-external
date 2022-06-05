package com.awin.coffeebreak.services;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import java.util.List;

public class SlackNotifier {

    /**
     * Imagine that this method:
     * Sends a notification to the user on Slack that their coffee break refreshment today will be $preference
     * returns true of false status of notification sent
     */
    public boolean notifyStaffMember(final StaffMember staffMember, final List<CoffeeBreakPreference> preferences) {
        // debug: also need to check if slack identifier exist or if it is empty
        if (staffMember.getSlackIdentifier() == null || staffMember.getSlackIdentifier().isEmpty()) {
            throw new RuntimeException();
        }

        return true;
    }

}
