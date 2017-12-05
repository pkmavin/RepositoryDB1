package com.db.awmd.challenge.repository;

import java.math.BigDecimal;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.AccountNotFoundException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;

public interface AccountsRepository {

  void createAccount(Account account) throws DuplicateAccountIdException;

  Account getAccount(String accountId);
  
  void clearAccounts();
  
  // pkm
  Account updateAccount(String accountId, BigDecimal balance) throws AccountNotFoundException ; 
  
  void transferAccountBalance (String fromAccountId, String toAccountId, BigDecimal amount) throws AccountNotFoundException ; ;
}
