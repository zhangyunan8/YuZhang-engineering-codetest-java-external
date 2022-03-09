package com.awin.coffeebreak.services;

import com.awin.coffeebreak.dto.CoffeeBreakRequest;
import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import com.awin.coffeebreak.repository.StaffMemberRepository;
import java.util.Optional;

public class CoffeeBreakService {

    private final StaffMemberRepository staffMemberRepository;

    public CoffeeBreakService(
          StaffMemberRepository staffMemberRepository
    ) {
        this.staffMemberRepository = staffMemberRepository;
    }

    public void requestCoffeeBreak(CoffeeBreakRequest request) {
        Optional<StaffMember> requesting = staffMemberRepository.findById(request.getRequestingStaffMember());
        Optional<StaffMember> requested = staffMemberRepository.findById(request.getRequestedStaffMember());

        if (requesting.isPresent() && requested.isPresent()) {
            final CoffeeBreakPreference coffeeBreakPreference = new CoffeeBreakPreference(
                  request.getType(),
                  request.getSubType(),
                  requested.get(),
                  request.getDetails()
            );

            requested.get().getCoffeeBreakPreferences().add(coffeeBreakPreference);
        }
    }

}