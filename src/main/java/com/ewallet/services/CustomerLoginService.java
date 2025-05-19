package com.ewallet.services;

import com.ewallet.entities.CurrentCustomerSession;
import com.ewallet.entities.Customer;
import com.ewallet.entities.User;
import com.ewallet.exceptions.CustomerException;
import com.ewallet.exceptions.LoginException;
import com.ewallet.exceptions.LogoutException;
import com.ewallet.exceptions.UserException;

public interface CustomerLoginService {

	public CurrentCustomerSession loginCustomer(User user) throws LoginException, CustomerException;

	public String logoutCustomer(String key) throws LogoutException;
	
	public User authenticateCustomer(User user, String key) throws UserException, LoginException, CustomerException;

	public Customer validateCustomer(String key) throws LoginException, CustomerException;
}
