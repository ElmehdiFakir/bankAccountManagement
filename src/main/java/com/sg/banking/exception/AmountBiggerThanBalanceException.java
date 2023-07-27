package com.sg.banking.exception;

public class AmountBiggerThanBalanceException extends RuntimeException{

    public AmountBiggerThanBalanceException(final String error){
        super(error);
    }
}