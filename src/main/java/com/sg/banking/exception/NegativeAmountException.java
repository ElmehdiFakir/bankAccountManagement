package com.sg.banking.exception;

public class NegativeAmountException extends RuntimeException{
    
    public NegativeAmountException(final String error){
        super(error);
    }
}
