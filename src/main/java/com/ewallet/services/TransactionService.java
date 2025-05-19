package com.ewallet.services;

import java.util.List;

import com.ewallet.entities.Transaction;
import com.ewallet.entities.User;
import com.ewallet.exceptions.AdminException;
import com.ewallet.exceptions.CustomerException;
import com.ewallet.exceptions.LoginException;
import com.ewallet.exceptions.UserException;
import com.ewallet.exceptions.WalletException;

public interface TransactionService {

	public Transaction addTransaction(String key, String receiver, String description, String transactionType,
			Double amount) throws CustomerException, LoginException;

	public Transaction viewTransaction(String key, Integer transactionId) throws CustomerException, LoginException;

	public List<Transaction> viewAllTransactions(String key) throws CustomerException, LoginException;

	public List<Transaction> viewAllTransactionsByCustomer(String key, String mobileNumber)
			throws AdminException, LoginException, CustomerException;

	public List<Transaction> viewAllTransactionsByCustomerByDate(String key, String date, String mobileNumber)
			throws AdminException, LoginException, CustomerException;

	public List<Transaction> viewTransactionByDate(String key, String date) throws LoginException, CustomerException;

}
