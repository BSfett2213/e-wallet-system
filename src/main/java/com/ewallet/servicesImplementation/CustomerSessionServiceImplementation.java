package com.ewallet.servicesImplementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewallet.entities.CurrentCustomerSession;
import com.ewallet.entities.Customer;
import com.ewallet.exceptions.CustomerSessionException;
import com.ewallet.exceptions.CustomerException;
import com.ewallet.repository.CustomerSessionRepo;
import com.ewallet.repository.CustomerRepo;
import com.ewallet.services.CustomerSessionService;

@Service
public class CustomerSessionServiceImplementation implements CustomerSessionService {

	@Autowired
	private CustomerSessionRepo customerSessionRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Override
	public CurrentCustomerSession getCurrentCustomerSession(String key) throws CustomerSessionException {

		Optional<CurrentCustomerSession> optional_CurrentCustomerSession = customerSessionRepo.findByKey(key);

		if (optional_CurrentCustomerSession.isPresent()) {

			CurrentCustomerSession currentCustomerSession = optional_CurrentCustomerSession.get();

			return currentCustomerSession;
		} else {
			throw new CustomerSessionException("Invalid key, Please Login In !");
		}

	}

	@Override
	public Customer getCustomerDetails(String key) throws CustomerSessionException, CustomerException {

		Optional<CurrentCustomerSession> optional_CurrentUserSession = customerSessionRepo.findByKey(key);

		if (optional_CurrentUserSession.isPresent()) {

			CurrentCustomerSession currentCustomerSession = optional_CurrentUserSession.get();

			String current_customerId = currentCustomerSession.getCustomerMobileNumber();

			Optional<Customer> current_Customer = customerRepo.findById(current_customerId);

			if (current_Customer.isPresent()) {

				Customer customer = current_Customer.get();

				return customer;

			} else {
				throw new CustomerException("No Registered Customer Found !");
			}

		} else {
			throw new CustomerSessionException("Invalid key, Please Login In !");
		}

	}

	@Override
	public String getCurrentCustomerId(String key) throws CustomerSessionException {

		Optional<CurrentCustomerSession> optional_CurrentCustomerSession = customerSessionRepo.findByKey(key);

		if (optional_CurrentCustomerSession.isPresent()) {

			CurrentCustomerSession currentCustomerSession = optional_CurrentCustomerSession.get();

			String current_customerId = currentCustomerSession.getCustomerMobileNumber();

			return current_customerId;

		} else {

			throw new CustomerSessionException("Invalid key, Please Login In !");
		}

	}

}
