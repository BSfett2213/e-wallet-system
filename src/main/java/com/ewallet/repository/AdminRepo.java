package com.ewallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ewallet.entities.Admin;
import com.ewallet.entities.Customer;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer> {

	public Optional<Admin> findByMobileNumber(String mobileNumber);
	
}
