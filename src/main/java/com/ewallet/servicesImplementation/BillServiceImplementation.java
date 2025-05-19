/**
 * 
 */
package com.ewallet.servicesImplementation;

import java.util.List;

import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewallet.entities.Bill;
import com.ewallet.entities.Customer;
import com.ewallet.entities.Transaction;
import com.ewallet.entities.Wallet;
import com.ewallet.exceptions.CustomerException;
import com.ewallet.exceptions.LoginException;
import com.ewallet.exceptions.WalletException;
import com.ewallet.repository.TransactionRepo;
import com.ewallet.repository.WalletRepo;
import com.ewallet.services.BillService;
import com.ewallet.services.TransactionService;

/**
 * @author tejas
 *
 */
@Service
public class BillServiceImplementation implements BillService {

	@Autowired
	private CustomerLoginServiceImplementation loginLogoutCustomerServiceImplementation;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private WalletRepo walletRepo;

	@Override
	public Transaction BillPayment(String key, Bill bill) throws CustomerException, LoginException, WalletException {

		Customer customer = loginLogoutCustomerServiceImplementation.validateCustomer(key);

		if (customer != null) {

			Wallet wallet = customer.getWallet();

			Double availablebalance = wallet.getBalance();

			List<Bill> listofbills = wallet.getListofBills();

			// make a Transaction and add transaction to transaction list

			if (availablebalance >= bill.getAmount()) {

				Transaction transaction = transactionService.addTransaction(key, bill.getReceiver(), bill.getBillType(),
						"Bill Payment", bill.getAmount());

				if (transaction != null) {

					listofbills.add(bill);

					wallet.setBalance(availablebalance - bill.getAmount());

					wallet.setListofBills(listofbills);

					walletRepo.save(wallet);

					return transaction;
				}

				else {
					throw new TransactionException("Opps ! Transaction Failed !");

				}
			} else {
				throw new WalletException("Insufficient Funds ! Available Wallet Balance : " + availablebalance);
			}

		} else {
			throw new CustomerException("Invalid Customer Key, Please Login In ! ");
		}

	}

	@Override
	public List<Bill> viewBillPayments(String key) throws CustomerException, LoginException {

		Customer customer = loginLogoutCustomerServiceImplementation.validateCustomer(key);

		if (customer != null) {

			Wallet wallet = customer.getWallet();

			return wallet.getListofBills();

		} else {
			throw new CustomerException("Invalid Customer Key, Please Login In ! ");
		}

	}

}
