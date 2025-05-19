package com.ewallet.servicesImplementation;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewallet.entities.CurrentCustomerSession;
import com.ewallet.entities.Customer;
import com.ewallet.entities.User;
import com.ewallet.exceptions.CustomerException;
import com.ewallet.exceptions.LoginException;
import com.ewallet.exceptions.LogoutException;
import com.ewallet.exceptions.UserException;
import com.ewallet.repository.CustomerSessionRepo;
import com.ewallet.repository.CustomerRepo;
import com.ewallet.services.CustomerLoginService;

import net.bytebuddy.utility.RandomString;

@Service
public class CustomerLoginServiceImplementation implements CustomerLoginService {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private CustomerSessionRepo customerSessionRepo;

	@Override
	public CurrentCustomerSession loginCustomer(User user) throws LoginException, CustomerException {

		if ("Customer".equals(user.getRole())) {

			Optional<Customer> optional_customer = customerRepo.findById(user.getId());

			if (optional_customer.isPresent()) {

				Customer customer = optional_customer.get();

				Optional<CurrentCustomerSession> optional_CurrentUserSession = customerSessionRepo
						.findByCustomerMobileNumber(customer.getMobileNumber());

				if (optional_CurrentUserSession.isPresent()) {

					throw new LoginException(
							"User Already Logged In With This Customer Id : " + customer.getMobileNumber());
				} else {

					if (user.getId().equals(customer.getMobileNumber())
							&& user.getPassword().equals(customer.getPassword())) {

						CurrentCustomerSession currentCustomerSession = new CurrentCustomerSession();

						String key = RandomString.make(6);

						currentCustomerSession.setCustomerMobileNumber(customer.getMobileNumber());
						currentCustomerSession.setKey(key);
						currentCustomerSession.setLocalDateTime(LocalDateTime.now());

						return customerSessionRepo.save(currentCustomerSession);

					} else {
						throw new LoginException("Invalid User_Id or Password");
					}
				}

			} else {
				throw new CustomerException("No Registered Customer Found With This User_Id : " + user.getId());
			}

		} else {

			throw new LoginException("Please, Select Customer as Role to Login !");
		}

	}

	@Override
	public String logoutCustomer(String key) throws LogoutException {

		Optional<CurrentCustomerSession> currentCustomerSession = customerSessionRepo.findByKey(key);

		if (currentCustomerSession.isPresent()) {

			customerSessionRepo.delete(currentCustomerSession.get());

			return "Logged Out Successfully !";

		} else {
			throw new LogoutException("Invalid Key, No User Logged In !");
		}

	}

	// Use this methods for Payments or some Very Highly Secured Operations//
	@Override
	public User authenticateCustomer(User user, String key) throws UserException, LoginException, CustomerException {

		Optional<CurrentCustomerSession> optional_currentCustomerSession = customerSessionRepo.findByKey(key);

		if (optional_currentCustomerSession.isPresent()) {

			CurrentCustomerSession currentCustomerSession = optional_currentCustomerSession.get();

			Optional<Customer> optional_customer = customerRepo
					.findById(currentCustomerSession.getCustomerMobileNumber());

			if (optional_customer.isPresent()) {

				Customer customer = optional_customer.get();

				if (customer.getMobileNumber().equals(user.getId())
						&& customer.getPassword().equals(user.getPassword())) {

					return user;
				} else {
					throw new UserException("Invalid UserId or Password");
				}

			} else {
				throw new CustomerException("No Customer Found with this Customer Id : "
						+ currentCustomerSession.getCustomerMobileNumber());
			}

		} else {
			throw new LoginException("Invalid Key, Please Login In !");
		}
	}

	@Override
	public Customer validateCustomer(String key) throws CustomerException, LoginException {

		Optional<CurrentCustomerSession> optional_currentCustomerSession = customerSessionRepo.findByKey(key);

		if (optional_currentCustomerSession.isPresent()) {

			CurrentCustomerSession currentCustomerSession = optional_currentCustomerSession.get();

			Optional<Customer> optional_customer = customerRepo
					.findById(currentCustomerSession.getCustomerMobileNumber());

			if (optional_customer.isPresent()) {

				Customer customer = optional_customer.get();

				return customer;

			} else {
				throw new CustomerException("No Customer Found with this Customer Id : "
						+ currentCustomerSession.getCustomerMobileNumber());
			}

		} else {
			throw new LoginException("Invalid Key, Please Login In !");
		}
	}

}
