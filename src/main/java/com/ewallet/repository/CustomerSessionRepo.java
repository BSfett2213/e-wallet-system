package com.ewallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ewallet.entities.CurrentCustomerSession;

@Repository
public interface CustomerSessionRepo extends JpaRepository<CurrentCustomerSession, Integer> {

	public Optional<CurrentCustomerSession> findByKey(String key);

	public Optional<CurrentCustomerSession> findByCustomerMobileNumber(String customerMobileNumber);
}
