package com.awin.coffeebreak.services

import com.awin.coffeebreak.dto.CoffeeBreakRequest
import com.awin.coffeebreak.entity.StaffMember
import com.awin.coffeebreak.repository.StaffMemberRepository
import spock.lang.Specification

class CoffeeBreakServiceTest extends Specification {

    def staffMemberRepository = Mock(StaffMemberRepository)
    def coffeeBreakService = new CoffeeBreakService(staffMemberRepository)

    def "it should save a coffee new coffee break"() {
        given:
        def request = new CoffeeBreakRequest()
        request.requestedStaffMember = 1
        request.requestingStaffMember = 2
        request.type = "food"
        request.subType = "toast"
        request.details = [:]

        def firstStaffMember = new StaffMember()
        def secondStaffMember = new StaffMember()

        when:
        coffeeBreakService.requestCoffeeBreak(request)

        then:
        1 * staffMemberRepository.findById(1) >> [firstStaffMember]
        1 * staffMemberRepository.findById(2) >> [secondStaffMember]

        firstStaffMember.getCoffeeBreakPreferences().size() == 1
    }
}
