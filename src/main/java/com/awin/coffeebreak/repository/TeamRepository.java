package com.awin.coffeebreak.repository;

import com.awin.coffeebreak.entity.StaffMember;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<StaffMember, Integer> {
}
