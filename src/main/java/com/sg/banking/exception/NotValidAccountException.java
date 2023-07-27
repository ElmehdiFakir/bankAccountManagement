package com.sg.banking.exception;

public class NotValidAccountException extends IllegalArgumentException {

    public NotValidAccountException(String accountId){
        super("The account " + accountId + " is not found");
    }
}
