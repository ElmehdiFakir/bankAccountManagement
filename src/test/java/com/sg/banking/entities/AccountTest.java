package com.sg.banking.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.sg.banking.accoutManager.Account;
import com.sg.banking.exception.AmountBiggerThanBalanceException;
import com.sg.banking.exception.NegativeAmountException;
import com.sg.banking.exception.NotValidAccountException;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountTest {
    /**constants**/
    private static final Integer ACCOUNT_NUMBER = 876543;

    /**services**/
    private Account account;


    /**
     * we test the deposit method by adding multiple deposits and checking the balance
     */
    @Test
    public void variousDepositsTest() {
        Account account = buildAccountForTest(ACCOUNT_NUMBER, BigDecimal.ZERO);

        account.getAccounts().put(ACCOUNT_NUMBER, account);

        //performing various deposits of 1000 200 300 to check the balance
        account.deposit(account.getAccountNumber(), new BigDecimal(1000));
        BigDecimal actualBalance = account.getBalance();
        assertEquals(BigDecimal.valueOf(1000), actualBalance);

        account.deposit(account.getAccountNumber(), BigDecimal.valueOf(200));
        account.deposit(account.getAccountNumber(), BigDecimal.valueOf(300));
        BigDecimal actualBalance2 = account.getBalance();
        assertEquals(BigDecimal.valueOf(1500), actualBalance2);

        //and also we must check number of transactions
        assertEquals(3, account.getOperations().size());
    }


    /**
     * we test the deposit method for a negative amount
     */
    @Test(expected = NegativeAmountException.class)
    public void negativeDepositsTest() {
        Account account = buildAccountForTest(ACCOUNT_NUMBER, BigDecimal.ZERO);

        account.getAccounts().put(ACCOUNT_NUMBER, account);

        //performing a negative deposit of -200
        account.deposit(account.getAccountNumber(), new BigDecimal(-200));
    }

    /**
     * test deposit in a non existing account
     */
    @Test(expected = NotValidAccountException.class)
    public void depositInNonExtingAccountTest() {

        Account account = buildAccountForTest(ACCOUNT_NUMBER, BigDecimal.ZERO);

        account.getAccounts().put(ACCOUNT_NUMBER, account);

        //test the 111222 account that doesn't exist
        account.deposit(111222, BigDecimal.valueOf(1000));
    }


    /**
     * we test the Withdrawal method by adding multiple Withdrawals and checking the balance
     */
    @Test
    public void variousWithdrawalsTest() {
        Account account = buildAccountForTest(ACCOUNT_NUMBER, BigDecimal.valueOf(1000));

        account.getAccounts().put(ACCOUNT_NUMBER, account);

        //performing various Withdrawals of 1000 200 300 to check the balance
        account.withdraw(account.getAccountNumber(), new BigDecimal(100));
        BigDecimal actualBalance = account.getBalance();
        assertEquals(BigDecimal.valueOf(900), actualBalance);

        account.withdraw(account.getAccountNumber(), BigDecimal.valueOf(200));
        account.withdraw(account.getAccountNumber(), BigDecimal.valueOf(300));
        BigDecimal actualBalance2 = account.getBalance();
        assertEquals(BigDecimal.valueOf(400), actualBalance2);

        //checking transactions
        assertEquals(3, account.getOperations().size());
    }


    /**
     * we test the Withdrawal method for a negative amount
     */
    @Test(expected = NegativeAmountException.class)
    public void negativeWithdrawalsTest() {
        Account account = buildAccountForTest(ACCOUNT_NUMBER, BigDecimal.ZERO);

        account.getAccounts().put(ACCOUNT_NUMBER, account);

        //performing a negative Withdrawal of -200
        account.withdraw(account.getAccountNumber(), new BigDecimal(-200));
    }

    /**
     * test Withdrawal in a non existing account
     */
    @Test(expected = NotValidAccountException.class)
    public void WithdrawalInNonExtingAccountTest() {

        Account account = buildAccountForTest(ACCOUNT_NUMBER, BigDecimal.ZERO);

        account.getAccounts().put(ACCOUNT_NUMBER, account);

        //test the 111222 account that doesn't exist
        account.withdraw(111222, BigDecimal.valueOf(1000));
    }

    /**
     * test withDrawal amount more than account balance
     */
    @Test(expected = AmountBiggerThanBalanceException.class)
    public void withDrawalMoreThanBalanceTest() {

        Account account = buildAccountForTest(ACCOUNT_NUMBER, BigDecimal.valueOf(100));

        account.getAccounts().put(ACCOUNT_NUMBER, account);

        //the amount 550 is more than the actual balance 100
        account.withdraw(account.getAccountNumber(), BigDecimal.valueOf(550));
    }

    /**
     * check printed balance : here due to time insuffisance we can improve this test to check all printed string in one assert
     */
    @Test
    public void printAccountStatementsTest() {

        Account account = buildAccountForTest(ACCOUNT_NUMBER, BigDecimal.valueOf(100));

        account.getAccounts().put(ACCOUNT_NUMBER, account);

        account.deposit(account.getAccountNumber(), BigDecimal.valueOf(100));
        account.deposit(account.getAccountNumber(), BigDecimal.valueOf(150));

        account.withdraw(account.getAccountNumber(), BigDecimal.valueOf(50));

        Account actualAccount = account.getAccounts().get(account.getAccountNumber());

        assertEquals(3, actualAccount.getOperations().size());

        String statement = account.printAccountStatements(actualAccount.getAccountNumber());
        //check in printed string our transactions
        assertTrue(
                statement.contains("[operationType=DEPOSIT, amount=100, balance=200, operationDate=" + LocalDate.now())
        );
        //check in printed string our transactions
        assertTrue(
                statement.contains("[operationType=DEPOSIT, amount=150, balance=350, operationDate="+LocalDate.now())
        );
        //check in printed string our transactions
        assertTrue(
                statement.contains("[operationType=WITHDRAWAL, amount=50, balance=300, operationDate="+LocalDate.now())
        );

    }

    /**
     * building of an account
     */
    public Account buildAccountForTest(Integer accountNumber, BigDecimal balance){
        Account account = new Account();
        account.setBalance(balance);
        account.setAccountNumber(accountNumber);

        return account;
    }


}
