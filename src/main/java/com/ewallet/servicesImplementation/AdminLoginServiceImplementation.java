package com.ewallet.servicesImplementation;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewallet.entities.Admin;
import com.ewallet.entities.CurrentAdminSession;
import com.ewallet.entities.User;
import com.ewallet.exceptions.AdminException;
import com.ewallet.exceptions.LoginException;
import com.ewallet.exceptions.LogoutException;
import com.ewallet.exceptions.UserException;
import com.ewallet.repository.AdminRepo;
import com.ewallet.repository.AdminSessionRepo;
import com.ewallet.services.AdminLoginService;

import net.bytebuddy.utility.RandomString;

@Service
public class AdminLoginServiceImplementation implements AdminLoginService {

	@Autowired
	private AdminRepo adminRepo;

	@Autowired
	private AdminSessionRepo adminSessionRepo;

	@Override
	public String logoutAdmin(String key) throws LogoutException {

		Optional<CurrentAdminSession> optional_currentAdminSession = adminSessionRepo.findByKey(key);

		if (optional_currentAdminSession.isPresent()) {

			adminSessionRepo.delete(optional_currentAdminSession.get());

			return "Logged Out Successfully !";

		} else {
			throw new LogoutException("No User Logged In !");

		}
	}

	@Override
	public User authenticateAdmin(User user, String key) throws UserException, AdminException, LoginException {

		Optional<CurrentAdminSession> optional_currentAdminSession = adminSessionRepo.findByKey(key);

		if (optional_currentAdminSession.isPresent()) {

			CurrentAdminSession currentAdminSession = optional_currentAdminSession.get();

			Optional<Admin> optional_admin = adminRepo.findById(currentAdminSession.getAdminId());

			if (optional_admin.isPresent()) {

				Admin admin = optional_admin.get();

				if (admin.getMobileNumber().equals(user.getId()) && admin.getPassword().equals(user.getPassword())) {

					return user;
				} else {
					throw new UserException("Invalid UserId or Password");
				}

			} else {
				throw new AdminException(
						"No Registered Admin Found with this Admin Id : " + currentAdminSession.getAdminId());
			}

		} else {
			throw new LoginException("Invalid Admin Key, Please Login In !");
		}

	}

	@Override
	public Admin validateAdmin(String key) throws AdminException, LoginException {

		Optional<CurrentAdminSession> optional_currentAdminSession = adminSessionRepo.findByKey(key);

		if (optional_currentAdminSession.isPresent()) {

			CurrentAdminSession currentAdminSession = optional_currentAdminSession.get();

			Optional<Admin> optional_admin = adminRepo.findById(currentAdminSession.getAdminId());

			if (optional_admin.isPresent()) {

				Admin admin = optional_admin.get();

				return admin;

			} else {
				throw new AdminException(
						"No Registered Admin Found with this Admin Id : " + currentAdminSession.getAdminId());
			}

		} else {
			throw new LoginException("Invalid Admin Key, Please Login In !");
		}

	}

	@Override
	public CurrentAdminSession loginAdmin(User user) throws LoginException, AdminException {

		if ("Admin".equals(user.getRole())) {

			Optional<Admin> optionalAdmin = adminRepo.findByMobileNumber(user.getId());

			if (optionalAdmin.isPresent()) {

				Admin admin = optionalAdmin.get();

				Optional<CurrentAdminSession> optional_currentAdminSession = adminSessionRepo
						.findByAdminId(admin.getAdminId());

				if (optional_currentAdminSession.isEmpty()) {

					CurrentAdminSession currentAdminSession = new CurrentAdminSession();

					String key = RandomString.make(6);

					currentAdminSession.setAdminId(admin.getAdminId());
					currentAdminSession.setLocalDateTime(LocalDateTime.now());
					currentAdminSession.setKey(key);

					return adminSessionRepo.save(currentAdminSession);
				} else {
					throw new LoginException("User Already Logged In With This Admin Id : " + admin.getAdminId());
				}

			} else {
				throw new AdminException("No Registered Admin Found With This User_Id : " + user.getId());
			}

		} else {
			throw new LoginException("Please, Select Admin as Role to Login !");
		}

	}

}
