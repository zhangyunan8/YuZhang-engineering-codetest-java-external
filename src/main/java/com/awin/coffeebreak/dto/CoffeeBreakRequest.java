package com.awin.coffeebreak.dto;

import java.time.Instant;
import java.util.Map;

public class CoffeeBreakRequest {
    private int requestingStaffMember;
    private int requestedStaffMember;
    private String type;
    private String subType;
    private Instant requestDate;
    private Map<String, String> details;

    public int getRequestingStaffMember() {
        return requestingStaffMember;
    }

    public void setRequestingStaffMember(final int requestingStaffMember) {
        this.requestingStaffMember = requestingStaffMember;
    }

    public int getRequestedStaffMember() {
        return requestedStaffMember;
    }

    public void setRequestedStaffMember(final int requestedStaffMember) {
        this.requestedStaffMember = requestedStaffMember;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(final String subType) {
        this.subType = subType;
    }

    public Instant getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(final Instant requestDate) {
        this.requestDate = requestDate;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(final Map<String, String> details) {
        this.details = details;
    }
}
