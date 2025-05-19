/**
 * 
 */
package com.ewallet.services;

import java.util.List;

import com.ewallet.entities.Customer;
import com.ewallet.exceptions.AdminException;
import com.ewallet.exceptions.CustomerException;
import com.ewallet.exceptions.LoginException;

/**
 * @author tejas
 *
 */

public interface CustomerService {

	public Customer addCustomer(Customer customer) throws CustomerException;

	public Customer updateCustomer(String key, Customer customer) throws CustomerException, LoginException;

	public String removeCustomer(String key, String customer_Id) throws CustomerException, LoginException;

	public Customer viewCustomer(String key, String customer_Id) throws CustomerException, LoginException;

	// Check for Admin Role
	public List<Customer> viewAllCustomers(String key) throws AdminException, LoginException, CustomerException;

}
