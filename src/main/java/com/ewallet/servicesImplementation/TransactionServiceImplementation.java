package com.ewallet.servicesImplementation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewallet.entities.Admin;
import com.ewallet.entities.Customer;
import com.ewallet.entities.Transaction;
import com.ewallet.entities.Wallet;
import com.ewallet.exceptions.AdminException;
import com.ewallet.exceptions.CustomerException;
import com.ewallet.exceptions.LoginException;
import com.ewallet.repository.CustomerRepo;
import com.ewallet.repository.TransactionRepo;
import com.ewallet.repository.WalletRepo;
import com.ewallet.services.AdminLoginService;
import com.ewallet.services.CustomerLoginService;
import com.ewallet.services.TransactionService;

@Service
public class TransactionServiceImplementation implements TransactionService {

	@Autowired
	private CustomerLoginService loginLogoutCustomerServiceImplementation;

	@Autowired
	private AdminLoginService loginLogoutAdminServiceImplementation;

	@Autowired
	private WalletRepo walletRepo;

	@Autowired
	private TransactionRepo transactionRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Override
	public Transaction addTransaction(String key, String receiver, String description, String transactionType,
			Double amount) throws CustomerException, LoginException {

		Customer customer = loginLogoutCustomerServiceImplementation.validateCustomer(key);

		if (customer != null) {

			Wallet wallet = customer.getWallet();

			Transaction transaction = new Transaction();

			transaction.setAmount(amount);
			transaction.setTime(LocalTime.now());
			transaction.setDate(LocalDate.now());
			transaction.setDescription(description);
			transaction.setReceiver(receiver);
			transaction.setType(transactionType);

			transaction.setWalletId(wallet.getWalletId());

			List<Transaction> listoftransactions = wallet.getListofTransactions();

			listoftransactions.add(transaction);

			wallet.setListofTransactions(listoftransactions);

			walletRepo.save(wallet);

			transactionRepo.save(transaction);

			return transaction;

		} else {
			throw new CustomerException("Invalid Customer Key ! Please Login In ! ");
		}

	}

	@Override
	public Transaction viewTransaction(String key, Integer transactionId) throws CustomerException, LoginException {

		Customer customer = loginLogoutCustomerServiceImplementation.validateCustomer(key);

		if (customer != null) {

			Optional<Transaction> optionalTransaction = transactionRepo.findById(transactionId);

			if (optionalTransaction.isPresent()) {

				return optionalTransaction.get();

			} else {
				throw new TransactionException("No Transaction Found With This Transaction Id : " + transactionId);
			}

		} else {
			throw new CustomerException("Invalid Customer Key, Please Login In !");
		}

	}

	@Override
	public List<Transaction> viewAllTransactions(String key) throws CustomerException, LoginException {

		Customer customer = loginLogoutCustomerServiceImplementation.validateCustomer(key);

		if (customer != null) {

			Wallet wallet = customer.getWallet();

			List<Transaction> listoftransactions = wallet.getListofTransactions();

			if (listoftransactions.isEmpty()) {
				throw new TransactionException("No Transactions Found !");
			} else {

				return listoftransactions;

			}

		} else {
			throw new CustomerException("Invalid Customer Key, Please Login In !");
		}

	}

	@Override
	public List<Transaction> viewTransactionByDate(String key, String date) throws LoginException, CustomerException {

		// Date format should be "YYYY-MM-DD"

		LocalDate localDate = LocalDate.parse(date);

		Customer customer = loginLogoutCustomerServiceImplementation.validateCustomer(key);

		if (customer != null) {

			Wallet wallet = customer.getWallet();

			List<Transaction> listoftransactions = wallet.getListofTransactions();

			if (!listoftransactions.isEmpty()) {

				List<Transaction> listoftransactionsbydate = listoftransactions.stream()
						.filter(t -> t.getDate() == localDate).collect(Collectors.toList());

				if (listoftransactionsbydate.isEmpty()) {
					throw new TransactionException("No Transactions by This Date : " + date);
				} else {

					return listoftransactionsbydate;
				}

			} else {

				throw new TransactionException("No Transactions Found By This Customer !");
			}

		} else {
			throw new CustomerException("Invalid Customer Key, Please Login In !");
		}

	}

	@Override
	public List<Transaction> viewAllTransactionsByCustomer(String key, String mobileNumber)
			throws AdminException, LoginException, CustomerException {

		Admin admin = loginLogoutAdminServiceImplementation.validateAdmin(key);

		if (admin != null) {

			Optional<Customer> optionalCustomer = customerRepo.findById(mobileNumber);

			if (optionalCustomer.isPresent()) {

				Customer customer = optionalCustomer.get();

				Wallet wallet = customer.getWallet();

				List<Transaction> listoftransactions = wallet.getListofTransactions();

				if (listoftransactions.isEmpty()) {

					throw new TransactionException("No Transactions Found By This Customer !");

				} else {
					return listoftransactions;
				}

			} else {
				throw new CustomerException("No Registered Customer found with this MobileNumber : " + mobileNumber);
			}

		} else {

			throw new AdminException("Invalid Admin Key, Please Login In !");
		}
	}

	@Override
	public List<Transaction> viewAllTransactionsByCustomerByDate(String key, String date, String mobileNumber)
			throws AdminException, LoginException, CustomerException {

		Admin admin = loginLogoutAdminServiceImplementation.validateAdmin(key);

		if (admin != null) {

			// Date format should be "YYYY-MM-DD"

			LocalDate localDate = LocalDate.parse(date);

			Optional<Customer> optionalCustomer = customerRepo.findById(mobileNumber);

			if (optionalCustomer.isPresent()) {

				Customer customer = optionalCustomer.get();

				Wallet wallet = customer.getWallet();

				List<Transaction> listoftransactions = wallet.getListofTransactions();

				if (!listoftransactions.isEmpty()) {

					List<Transaction> listoftransactionsbydate = listoftransactions.stream()
							.filter(t -> t.getDate() == localDate).collect(Collectors.toList());

					if (listoftransactionsbydate.isEmpty()) {
						throw new TransactionException("No Transactions by This Date : " + date);
					} else {

						return listoftransactionsbydate;
					}

				} else {

					throw new TransactionException("No Transactions Found By This Customer !");
				}

			} else {
				throw new CustomerException("No Registered Customer found with this MobileNumber : " + mobileNumber);
			}

		} else {
			throw new AdminException("Invalid Admin Key, Please Login In !");
		}

	}
}
