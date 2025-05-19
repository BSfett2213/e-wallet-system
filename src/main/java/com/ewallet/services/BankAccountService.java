package com.ewallet.services;

import java.util.List;

import com.ewallet.entities.BankAccount;
import com.ewallet.entities.User;
import com.ewallet.exceptions.AdminException;
import com.ewallet.exceptions.BankAccountException;
import com.ewallet.exceptions.AdminSessionException;
import com.ewallet.exceptions.CustomerSessionException;
import com.ewallet.exceptions.CustomerException;
import com.ewallet.exceptions.LoginException;
import com.ewallet.exceptions.UserException;
public interface BankAccountService {

	String addAccount(User user, String key, String mobileNumber, String bankName, String ifscCode, Double balance,
			String accountNo) throws CustomerSessionException, UserException, LoginException, CustomerException;

	public String removeAccount(User user, String key) throws UserException, LoginException, CustomerException,
			BankAccountException, CustomerSessionException;

	public BankAccount viewAccount(User user, String key) throws UserException, LoginException, CustomerException,
			BankAccountException, CustomerSessionException;

	public Double viewBankBalance(User user, String key) throws UserException, LoginException, CustomerException,
			BankAccountException, CustomerSessionException;

	public List<BankAccount> viewAllAccounts(User user, String key)
			throws UserException, AdminException, LoginException, BankAccountException, AdminSessionException;

}
