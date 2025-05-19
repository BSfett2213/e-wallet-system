package com.ewallet.servicesImplementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewallet.entities.Admin;
import com.ewallet.entities.CurrentAdminSession;
import com.ewallet.exceptions.AdminException;
import com.ewallet.exceptions.AdminSessionException;
import com.ewallet.repository.AdminRepo;
import com.ewallet.repository.AdminSessionRepo;
import com.ewallet.services.AdminSessionService;

@Service
public class AdminSessionServiceImplementation implements AdminSessionService {

	@Autowired
	private AdminSessionRepo adminSessionRepo;

	@Autowired
	private AdminRepo adminRepo;

	@Override
	public CurrentAdminSession getCurrentAdminSession(String key) throws AdminSessionException {

		Optional<CurrentAdminSession> optional_CurrentAdminSession = adminSessionRepo.findByKey(key);

		if (optional_CurrentAdminSession.isPresent()) {

			CurrentAdminSession currentAdminSession = optional_CurrentAdminSession.get();

			return currentAdminSession;
		} else {
			throw new AdminSessionException("Invalid key, Please Login In !");
		}
	}

	@Override
	public Admin getAdminDetails(String key) throws AdminException, AdminSessionException {

		Optional<CurrentAdminSession> optional_CurrentAdminSession = adminSessionRepo.findByKey(key);

		if (optional_CurrentAdminSession.isPresent()) {

			CurrentAdminSession currentAdminSession = optional_CurrentAdminSession.get();

			Integer current_CustomerId = currentAdminSession.getAdminId();

			Optional<Admin> optional_admin = adminRepo.findById(current_CustomerId);

			if (optional_admin.isPresent()) {

				Admin admin = optional_admin.get();

				return admin;

			} else {
				throw new AdminException("No Registered Admin Found !");
			}

		} else {
			throw new AdminSessionException("Invalid key, Please Login In !");
		}

	}

	@Override
	public Integer getAdminId(String key) throws AdminSessionException {

		Optional<CurrentAdminSession> optional_CurrentAdminSession = adminSessionRepo.findByKey(key);

		if (optional_CurrentAdminSession.isPresent()) {

			CurrentAdminSession currentAdminSession = optional_CurrentAdminSession.get();

			Integer current_adminId = currentAdminSession.getAdminId();

			return current_adminId;

		} else {
			throw new AdminSessionException("Invalid key, Please Login In !");
		}

	}

}
