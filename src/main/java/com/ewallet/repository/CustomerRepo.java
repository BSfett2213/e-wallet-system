package com.ewallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ewallet.entities.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, String> {

}
