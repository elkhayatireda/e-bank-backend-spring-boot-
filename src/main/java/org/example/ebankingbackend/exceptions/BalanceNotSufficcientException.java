package org.example.ebankingbackend.exceptions;

public class BalanceNotSufficcientException extends Exception {
    public BalanceNotSufficcientException(String msg) {
        super(msg);
    }
}
