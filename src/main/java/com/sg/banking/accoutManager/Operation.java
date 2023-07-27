package com.sg.banking.accoutManager;

import com.sg.banking.enums.OperationType;

import java.math.BigDecimal;
import java.time.Instant;

public class Operation {
    OperationType operationType;
    BigDecimal amount;
    BigDecimal balance;
    Instant operationDate;

    public Operation(OperationType operationType, BigDecimal amount, BigDecimal balance, Instant operationDate) {
        this.operationType = operationType;
        this.amount = amount;
        this.balance = balance;
        this.operationDate = operationDate;
    }

    @Override
    public String toString() {
        return "Operation[" +
                "operationType=" + operationType +
                ", amount=" + amount +
                ", balance=" + balance +
                ", operationDate=" + operationDate +
                ']';
    }
}
