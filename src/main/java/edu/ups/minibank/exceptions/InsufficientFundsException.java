package edu.ups.minibank.exceptions;

public class InsufficientFundsException extends Exception {
    public  InsufficientFundsException(){
        super("There is not enough funds to continue with the current transaction");
    }
}
