package com.ewallet.services;

import java.util.List;

import com.ewallet.entities.Bill;
import com.ewallet.entities.Transaction;
import com.ewallet.exceptions.CustomerException;
import com.ewallet.exceptions.LoginException;
import com.ewallet.exceptions.WalletException;

public interface BillService {

	public Transaction BillPayment(String key, Bill bill) throws CustomerException, LoginException, WalletException;

	public List<Bill> viewBillPayments(String key) throws CustomerException, LoginException;

}
