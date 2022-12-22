package com.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value="select c from Customer c where c.users=?1")
    Customer findByUser(User user);
}
