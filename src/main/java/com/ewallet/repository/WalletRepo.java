package com.ewallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ewallet.entities.Wallet;

@Repository
public interface WalletRepo extends JpaRepository<Wallet, String> {

}
