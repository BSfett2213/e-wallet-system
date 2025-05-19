package com.ewallet.services;

import com.ewallet.entities.CurrentCustomerSession;
import com.ewallet.entities.Customer;
import com.ewallet.exceptions.CustomerSessionException;
import com.ewallet.exceptions.CustomerException;

public interface CustomerSessionService {

	public CurrentCustomerSession getCurrentCustomerSession(String key) throws CustomerSessionException;

	public Customer getCustomerDetails(String key) throws CustomerSessionException, CustomerException;

	public String getCurrentCustomerId(String key) throws CustomerSessionException;

}
