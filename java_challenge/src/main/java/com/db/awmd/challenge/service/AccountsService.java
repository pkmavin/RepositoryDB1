package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferCashData;
import com.db.awmd.challenge.exception.AccountIllegalAmountException;
import com.db.awmd.challenge.repository.AccountsRepository;
import com.db.awmd.challenge.service.NotificationServiceFactory.NotificationTypeEnum;

import lombok.Getter;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

 
	private final NotificationService notificationService;

	@Getter
	private final AccountsRepository accountsRepository;

	/**
	@Autowired
	public AccountsService(AccountsRepository accountsRepository, NotificationService notificationService) {
		this.accountsRepository = accountsRepository;
		this.notificationService = notificationService;

	}
	**/
	
	@Autowired
	public AccountsService(AccountsRepository accountsRepository) {
		this.accountsRepository = accountsRepository;
		this.notificationService = NotificationServiceFactory.getNotificatioNService(NotificationTypeEnum.EMAIL);
		 

	}

	public void createAccount(Account account) {
		this.accountsRepository.createAccount(account);
	}

	public Account getAccount(String accountId) {
		return this.accountsRepository.getAccount(accountId);
	}

	public Account updateAccount(String accountId, BigDecimal balance) {
		return this.accountsRepository.updateAccount(accountId, balance);
	}

//	public void transferAccountBalance(String fromAccountId, String toAccountId, BigDecimal amount) throws AccountIllegalAmountException {
	public void transferAccountBalance(TransferCashData transferCashData) throws AccountIllegalAmountException {
		
		// The notification will be invoked by this service
		  String  fromAccountId = transferCashData.getFromAccountId();
		  String  toAccountId = transferCashData.getToAccountId();
		  BigDecimal  amount = transferCashData.getAmount(); 
		  
		Account fromAccount = getAccount (fromAccountId);
		Account toAccount = getAccount (toAccountId);
		BigDecimal fromBalance = fromAccount.getBalance();
		if (amount.floatValue() <=0) 
		{
			throw new AccountIllegalAmountException ("Amount To Be Transfered should be more than zero"); 
		}
		if (amount.compareTo(fromBalance) > 0)
		{
			throw new AccountIllegalAmountException ("Amount To Be Transfered:" + amount + "Exceeds current balance"); 
		}
	
		this.accountsRepository.transferAccountBalance(fromAccountId, toAccountId, amount);
			
		String message = getTransferMessage (toAccountId, "to", amount);
		notificationService.notifyAboutTransfer(fromAccount, message); 
		message = getTransferMessage (fromAccountId, "from", amount);
		notificationService.notifyAboutTransfer(toAccount, message);
	}
	
	private String getTransferMessage (String accountId, String direction, BigDecimal amount)
	{
		return "Transferred amount:" + amount + " " + direction + " acccountId:" + accountId;
	}
	
 
}
