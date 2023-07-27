package com.sg.banking.accoutManager;

import com.sg.banking.enums.OperationType;
import com.sg.banking.exception.AmountBiggerThanBalanceException;
import com.sg.banking.exception.NegativeAmountException;
import com.sg.banking.exception.NotValidAccountException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {

    private BigDecimal balance;

    private Integer accountNumber;

    private List<Operation> operations;

    private final Map<Integer, Account> accounts;

    public Account() {
        this.accounts = new HashMap<>();
        this.balance = BigDecimal.ZERO;
        this.operations = new ArrayList<>();
    }

    public BigDecimal deposit(Integer idAccount, BigDecimal amount){

        Account account = this.accounts.get(idAccount);

        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new NegativeAmountException("The amount demanded " + amount.toString() + " is negative");
        }

        if(account == null){
            throw new NotValidAccountException(idAccount.toString());
        }

        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        account.getOperations().add(new Operation(OperationType.DEPOSIT, amount, newBalance, Instant.now()));
        return newBalance;
    }

    public BigDecimal withdraw(Integer idAccount, BigDecimal amount){

        Account account = this.accounts.get(idAccount);

        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new NegativeAmountException("The amount demanded " + amount.toString() + " is negative");
        }

        if(account == null){
            throw new NotValidAccountException(idAccount.toString());
        }

        if(amount.compareTo(this.balance) > 0){
            throw new AmountBiggerThanBalanceException("the amount is bigger than the balance");
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);
        account.setBalance(newBalance);
        account.getOperations().add(new Operation(OperationType.WITHDRAWAL, amount, newBalance, Instant.now()));
        return newBalance;
    }

    public List<Operation> checkHistory(Integer idAccount){

        Account account = this.accounts.get(idAccount);

        if(account == null){
            throw new NotValidAccountException(idAccount.toString());
        }

        return account.getOperations();
    }

    public String printAccountStatements(Integer idAccount) {
        StringBuilder printer = new StringBuilder();
        //get account with account number
        Account account = this.accounts.get(idAccount);

        if(account == null){
            throw new NotValidAccountException(idAccount.toString());
        }

        //here we construct a string with all operations
        account.getOperations().forEach(t->printer.append(t.toString()).append("\n"));

        //print transactions history
        return printer.toString();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Map<Integer, Account> getAccounts() {
        return accounts;
    }
}
