package org.example.ebankingbackend.exceptions;

public class BankAccountNotFoundException extends Exception {
    public BankAccountNotFoundException(String msg) {
        super(msg);
    }
}
