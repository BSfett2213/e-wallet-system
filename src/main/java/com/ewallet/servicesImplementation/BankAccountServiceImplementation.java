package com.ewallet.servicesImplementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewallet.entities.BankAccount;
import com.ewallet.entities.CurrentAdminSession;
import com.ewallet.entities.CurrentCustomerSession;
import com.ewallet.entities.Customer;
import com.ewallet.entities.User;
import com.ewallet.exceptions.AdminException;
import com.ewallet.exceptions.BankAccountException;
import com.ewallet.exceptions.AdminSessionException;
import com.ewallet.exceptions.CustomerSessionException;
import com.ewallet.exceptions.CustomerException;
import com.ewallet.exceptions.LoginException;
import com.ewallet.exceptions.UserException;
import com.ewallet.repository.BankAccountRepo;
import com.ewallet.repository.AdminSessionRepo;
import com.ewallet.repository.CustomerSessionRepo;
import com.ewallet.repository.CustomerRepo;
import com.ewallet.services.BankAccountService;

import java.util.List;
import java.util.Objects;

@Service
public class BankAccountServiceImplementation implements BankAccountService {

	@Autowired
	private CustomerSessionRepo customerSessionRepo;

	@Autowired
	private AdminSessionRepo adminSessionRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private BankAccountRepo bankAccountRepo;

	@Autowired
	private CustomerLoginServiceImplementation loginLogoutCustomerServiceImplementation;

	@Autowired
	private AdminLoginServiceImplementation loginLogoutAdminServiceImplementation;

	@Override
	public String addAccount(User user, String key, String mobileNumber, String bankName, String ifscCode,
			Double balance, String accountNo)
			throws CustomerSessionException, UserException, LoginException, CustomerException {

		User validated_user = loginLogoutCustomerServiceImplementation.authenticateCustomer(user, key);

		if (validated_user != null) {

			Optional<CurrentCustomerSession> optionalcurrentCustomerSession = customerSessionRepo.findByKey(key);

			if (optionalcurrentCustomerSession.isPresent()) {

				CurrentCustomerSession currentCustomerSession = optionalcurrentCustomerSession.get();

				Optional<Customer> optionalcustomer = customerRepo
						.findById(currentCustomerSession.getCustomerMobileNumber());

				if (optionalcustomer.isPresent()) {

					Customer customer = optionalcustomer.get();

					if (Objects.equals(customer.getMobileNumber(), mobileNumber)) {

						BankAccount bankAccount = new BankAccount();

						bankAccount.setWalletId(customer.getMobileNumber());
						bankAccount.setAccountNo(accountNo);
						bankAccount.setBalance(balance);
						bankAccount.setBankName(bankName);
						bankAccount.setIfscCode(ifscCode);

						bankAccountRepo.save(bankAccount);

						return "Bank Account Successfully Added !";

					} else {
						throw new CustomerException("Invalid Wallet ID, Please Enter Your Registered Mobile Number !");
					}

				} else {
					throw new CustomerException("No Registered Customer Found with this Mobile Number : "
							+ currentCustomerSession.getCustomerMobileNumber());
				}
			} else {
				throw new CustomerSessionException("Invalid Customer Key, Please Login In !");
			}

		} else {

			throw new UserException("Invalid User Id or Password !");
		}

	}

	@Override
	public String removeAccount(User user, String key) throws UserException, LoginException, CustomerException,
			BankAccountException, CustomerSessionException {

		User validated_user = loginLogoutCustomerServiceImplementation.authenticateCustomer(user, key);

		if (validated_user != null) {

			Optional<CurrentCustomerSession> optionalcurrentCustomerSession = customerSessionRepo.findByKey(key);

			if (optionalcurrentCustomerSession.isPresent()) {

				CurrentCustomerSession currentCustomerSession = optionalcurrentCustomerSession.get();

				Optional<BankAccount> optionalBankAccount = bankAccountRepo
						.findById(currentCustomerSession.getCustomerMobileNumber());

				if (optionalBankAccount.isPresent()) {

					bankAccountRepo.delete(optionalBankAccount.get());

					return "Bank Account Deleted Successfully !";

				} else {
					throw new BankAccountException("No Bank Account Found with this Mobile Number : "
							+ currentCustomerSession.getCustomerMobileNumber());
				}

			} else {
				throw new CustomerSessionException("Invalid Customer Key, Please Login In !");
			}

		} else {

			throw new UserException("Invalid User Id or Password !");
		}
	}

	@Override
	public BankAccount viewAccount(User user, String key) throws UserException, LoginException, CustomerException,
			BankAccountException, CustomerSessionException {

		User validated_user = loginLogoutCustomerServiceImplementation.authenticateCustomer(user, key);

		if (validated_user != null) {

			Optional<CurrentCustomerSession> optionalcurrentCustomerSession = customerSessionRepo.findByKey(key);

			if (optionalcurrentCustomerSession.isPresent()) {

				CurrentCustomerSession currentCustomerSession = optionalcurrentCustomerSession.get();

				Optional<BankAccount> optionalBankAccount = bankAccountRepo
						.findById(currentCustomerSession.getCustomerMobileNumber());

				if (optionalBankAccount.isPresent()) {

					BankAccount bankAccount = optionalBankAccount.get();

					return bankAccount;

				} else {
					throw new BankAccountException("No Bank Account Found with this Mobile Number : "
							+ currentCustomerSession.getCustomerMobileNumber());
				}

			} else {
				throw new CustomerSessionException("Invalid Customer Key, Please Login In !");
			}
		} else {
			throw new UserException("Invalid User Id or Password !");
		}
	}

	@Override
	public List<BankAccount> viewAllAccounts(User user, String key)
			throws UserException, AdminException, LoginException, BankAccountException, AdminSessionException {

		User validated_user = loginLogoutAdminServiceImplementation.authenticateAdmin(user, key);

		if (validated_user != null) {

			Optional<CurrentAdminSession> optionalcurrentAdminSession = adminSessionRepo.findByKey(key);

			if (optionalcurrentAdminSession.isPresent()) {

				List<BankAccount> listofbankaccounts = bankAccountRepo.findAll();

				if (!listofbankaccounts.isEmpty()) {

					return listofbankaccounts;
				} else {
					throw new BankAccountException("No Bank Accounts Found in the DataBase !");
				}

			} else {
				throw new AdminSessionException("Invalid Admin Key, Please Login In !");
			}

		} else {
			throw new UserException("Invalid User Id or Password !");
		}

	}

	@Override
	public Double viewBankBalance(User user, String key) throws UserException, LoginException, CustomerException,
			BankAccountException, CustomerSessionException {

		User validated_user = loginLogoutCustomerServiceImplementation.authenticateCustomer(user, key);

		if (validated_user != null) {

			Optional<CurrentCustomerSession> optionalcurrentCustomerSession = customerSessionRepo.findByKey(key);

			if (optionalcurrentCustomerSession.isPresent()) {

				CurrentCustomerSession currentCustomerSession = optionalcurrentCustomerSession.get();

				Optional<BankAccount> optionalBankAccount = bankAccountRepo
						.findById(currentCustomerSession.getCustomerMobileNumber());

				if (optionalBankAccount.isPresent()) {

					BankAccount bankAccount = optionalBankAccount.get();

					return bankAccount.getBalance();

				} else {
					throw new BankAccountException("No Bank Account Found with this Mobile Number : "
							+ currentCustomerSession.getCustomerMobileNumber());
				}

			} else {
				throw new CustomerSessionException("Invalid Customer Key, Please Login In !");
			}
		} else {
			throw new UserException("Invalid User Id or Password !");
		}

	}

}
