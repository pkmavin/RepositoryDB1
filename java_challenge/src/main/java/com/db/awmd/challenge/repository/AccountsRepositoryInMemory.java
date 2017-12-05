package com.db.awmd.challenge.repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.AccountIllegalAmountException;
import com.db.awmd.challenge.exception.AccountNotFoundException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

	private final Map<String, Account> accounts = new ConcurrentHashMap<>();

	@Override
	public void createAccount(Account account) throws DuplicateAccountIdException {
		Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
		if (previousAccount != null) {
			throw new DuplicateAccountIdException("Account id " + account.getAccountId() + " already exists!");
		}
	}

	@Override
	public Account getAccount(String accountId) {
		return accounts.get(accountId);
	}

	@Override
	public void clearAccounts() {
		accounts.clear();
	}
	
	// pkm

	@Override
	public Account updateAccount(String accountId, BigDecimal balance) throws AccountNotFoundException {
		if (balance.doubleValue() < 0) {
			throw new AccountIllegalAmountException("Balance value not allowed:" + balance);
		}
		Account account = this.getAccount(accountId);
		if (account == null) {
			throw new AccountNotFoundException("Account id " + accountId + " Not Found!");
		}

		// Make the update thread safe
		synchronized (account) {
			account.setBalance(balance);
		}

		return account;
	}

	@Override
	public void transferAccountBalance(String fromAccountId, String toAccountId, BigDecimal amount) throws AccountNotFoundException{
		Account fromAccount = this.getAccount(fromAccountId);
		
		if (fromAccount == null)
		{
			throw new AccountNotFoundException("Account id to transfer from:" + fromAccountId + " Not Found!");
		}
		Account toAccount = this.getAccount(toAccountId);
		
		if (toAccount == null)
		{
			throw new AccountNotFoundException("Account id to transfer to:" + toAccountId + " Not Found!");
		}
		toAccount.setBalance(fromAccount.getBalance());
	}

}
