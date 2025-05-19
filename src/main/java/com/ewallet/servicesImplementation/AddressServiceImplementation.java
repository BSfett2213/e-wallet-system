package com.ewallet.servicesImplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewallet.entities.Address;
import com.ewallet.entities.Admin;
import com.ewallet.entities.Customer;
import com.ewallet.exceptions.AddressException;
import com.ewallet.exceptions.AdminException;
import com.ewallet.exceptions.CustomerException;
import com.ewallet.exceptions.LoginException;
import com.ewallet.repository.AddressRepo;
import com.ewallet.services.AddressService;
import com.ewallet.services.AdminLoginService;
import com.ewallet.services.CustomerLoginService;

@Service
public class AddressServiceImplementation implements AddressService {

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private CustomerLoginService loginLogoutCustomerServiceImplementation;

	@Autowired
	private AdminLoginService loginLogoutAdminServiceImplementation;

	@Override
	public String addAddress(String key, Address address) throws CustomerException, LoginException {

		Customer customer = loginLogoutCustomerServiceImplementation.validateCustomer(key);

		if (customer != null) {

			customer.setAddress(address);

			addressRepo.save(address);

			return "Address Added Successfully !";

		} else {
			throw new CustomerException("No Customer Found, Please Login In !");
		}
	}

	@Override
	public String updateAddress(String key, Address address) throws CustomerException, LoginException {

		Customer customer = loginLogoutCustomerServiceImplementation.validateCustomer(key);

		if (customer != null) {

			Address old_address = customer.getAddress();
			
			
			customer.setAddress(address);
			
			addressRepo.save(address);
			
			addressRepo.deleteById(old_address.getAddressId());

			return "Address Updated Successfully !";

		} else {
			throw new CustomerException("No Customer Found, Please Login In !");
		}

	}

	@Override
	public String removeAddress(String key, Integer addressId)
			throws CustomerException, LoginException, AddressException {

		Customer customer = loginLogoutCustomerServiceImplementation.validateCustomer(key);

		if (customer != null) {

			Optional<Address> optional_address = addressRepo.findById(addressId);

			if (optional_address.isPresent()) {

				addressRepo.delete(optional_address.get());

				return "Address Deleted Successfully !";

			} else {
				throw new AddressException("No Address Found With The Address ID : " + addressId);
			}

		} else {
			throw new CustomerException("No Customer Found, Please Login In !");
		}
	}

	@Override
	public List<Address> viewAllAddress(String key) throws AdminException, LoginException, AddressException {

		Admin admin = loginLogoutAdminServiceImplementation.validateAdmin(key);

		if (admin != null) {

			List<Address> listofaddresses = addressRepo.findAll();

			if (!listofaddresses.isEmpty()) {

				return listofaddresses;
			} else {
				throw new AddressException("No Addresses Found in The Database !");
			}

		} else {
			throw new AdminException("Invalid Key, Please Login In as Admin !");
		}

	}

	@Override
	public Address viewAddress(String key) throws CustomerException, LoginException, AddressException {

		Customer customer = loginLogoutCustomerServiceImplementation.validateCustomer(key);

		if (customer != null) {

			Address address = customer.getAddress();

			if (address != null) {

				return address;

			} else {
				throw new AddressException("No Addresses Has Been Added By The Customer !");
			}

		} else {
			throw new CustomerException("No Customer Found, Please Login In !");
		}
	}

}
