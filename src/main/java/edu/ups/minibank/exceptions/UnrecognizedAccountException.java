package edu.ups.minibank.exceptions;

public class UnrecognizedAccountException extends Exception{
    public UnrecognizedAccountException(){
        super("The supplied account is not recognized");
    }
}
