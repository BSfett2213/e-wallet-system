package com.ewallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ewallet.entities.BankAccount;

@Repository
public interface BankAccountRepo extends JpaRepository<BankAccount, String> {

}
