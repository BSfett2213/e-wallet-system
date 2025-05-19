package com.ewallet.services;

import com.ewallet.entities.Transaction;
import com.ewallet.entities.User;
import com.ewallet.exceptions.BankAccountException;
import com.ewallet.exceptions.CustomerException;
import com.ewallet.exceptions.LoginException;
import com.ewallet.exceptions.UserException;
import com.ewallet.exceptions.WalletException;

public interface WalletService {

	public Double showWalletBalance(String key) throws LoginException, CustomerException;

	public Transaction transferFunds(User user, String key, String targetMobileNumber, Double amount,
			String description) throws LoginException, CustomerException, WalletException, UserException;

	public Transaction addMoneyToWallet(User user, String key, Double amount)
			throws UserException, LoginException, CustomerException, WalletException, BankAccountException;

	public Transaction addMoneyToBank(User user, String key, Double amount) throws UserException, LoginException, CustomerException, WalletException, BankAccountException;
	
}
