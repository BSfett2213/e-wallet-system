package com.ewallet.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ewallet.entities.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

	public List<Transaction> findByDate(LocalDate date);

}
