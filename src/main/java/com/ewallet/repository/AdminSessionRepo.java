package com.ewallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ewallet.entities.CurrentAdminSession;


@Repository
public interface AdminSessionRepo extends JpaRepository<CurrentAdminSession, Integer> {

	public Optional<CurrentAdminSession> findByKey(String key);

	public Optional<CurrentAdminSession> findByAdminId(Integer adminId);

}
