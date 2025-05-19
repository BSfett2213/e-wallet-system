package com.ewallet.services;

import java.util.List;

import com.ewallet.entities.Address;
import com.ewallet.exceptions.AddressException;
import com.ewallet.exceptions.AdminException;
import com.ewallet.exceptions.CustomerException;
import com.ewallet.exceptions.LoginException;

public interface AddressService {

	public String addAddress(String key, Address address) throws CustomerException, LoginException;

	public String updateAddress(String key, Address address) throws CustomerException, LoginException;

	public String removeAddress(String key, Integer addressId)
			throws CustomerException, LoginException, AddressException;

	public List<Address> viewAllAddress(String key) throws AdminException, LoginException, AddressException;

	public Address viewAddress(String key) throws CustomerException, LoginException, AddressException;

}
