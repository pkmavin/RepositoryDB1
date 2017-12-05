package com.db.awmd.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferCashData;
import com.db.awmd.challenge.exception.AccountIllegalAmountException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.service.AccountsService;
import java.math.BigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
//pkm
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountsServiceTest {

	@Autowired
	private AccountsService accountsService;

	@Test
	public void addAccount() throws Exception {
		Account account = new Account("Id-123");
		account.setBalance(new BigDecimal(1000));
		this.accountsService.createAccount(account);
		Account act = this.accountsService.getAccount("Id-123");
		assertThat(this.accountsService.getAccount("Id-123")).isEqualTo(account);
	}

	/*
	@Test
	public void getAccount() throws Exception {
		String accountId = "Id-123";
		Account account = this.accountsService.getAccount(accountId);
		assertThat(this.accountsService.getAccount(accountId)).isEqualTo(account);
	}

	@Test
	public void updateAccount(String accountId, BigDecimal balance) throws Exception {
		Account account = this.accountsService.getAccount(accountId);
		assertThat(this.accountsService.getAccount(accountId).getBalance() == balance);
	}
*/
	@Test
	public void transferAmount() throws Exception {
		String fromAccountId = "Id-100";
		String toAccountId = "Id-101";
		
		// Create the To Account
		Account fromAccount = new Account(fromAccountId);
		fromAccount.setBalance(new BigDecimal(1000));
		this.accountsService.createAccount(fromAccount);
		
		Account toAccount = new Account(toAccountId);
		toAccount.setBalance(new BigDecimal(1500));
		this.accountsService.createAccount(toAccount);
				
		BigDecimal transferAmount = BigDecimal.valueOf(500);
		BigDecimal newBalance = transferAmount.add(new BigDecimal(1000)); 
		TransferCashData transferAmountData = new TransferCashData(fromAccountId, toAccountId, transferAmount);
		// this.accountsService.transferAccountBalance(fromAccountId, toAccountId, transferAmount);
		this.accountsService.transferAccountBalance(transferAmountData);
		assertThat(this.accountsService.getAccount(toAccountId).getBalance() == newBalance);
	}

	@Test
	public void transferExcessiveAmount() throws Exception {
		String fromAccountId = "Id-555";
		String toAccountId = "Id-666";
		
		// Create Accounts
		
		Account fromAccount = new Account(fromAccountId);
		fromAccount.setBalance(new BigDecimal(1000));
		this.accountsService.createAccount(fromAccount);
		
		Account toAccount = new Account(toAccountId);
		toAccount.setBalance(new BigDecimal(1500));
		this.accountsService.createAccount(toAccount);
		

		
		BigDecimal transferAmount = BigDecimal.valueOf(2500);
		try {
			TransferCashData transferAmountData = new TransferCashData(fromAccountId, toAccountId, transferAmount);
			
			this.accountsService.transferAccountBalance(transferAmountData);
			fail("Should have failed when adding excessive amount");
		} catch (Exception e) {
			assertThat(e instanceof AccountIllegalAmountException); 
		}
	}
	
	@Test
	public void addAccount_failsOnDuplicateId() throws Exception {
		String uniqueId = "Id-" + System.currentTimeMillis();
		Account account = new Account(uniqueId);
		this.accountsService.createAccount(account);

		try {
			this.accountsService.createAccount(account);
			fail("Should have failed when adding duplicate account");
		} catch (DuplicateAccountIdException ex) {
			assertThat(ex.getMessage()).isEqualTo("Account id " + uniqueId + " already exists!");
		}

	}
}
