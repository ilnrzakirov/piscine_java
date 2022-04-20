package day01.ex04;

public class IllegalTransactionException extends RuntimeException {

    public IllegalTransactionException(String exceptionMassage){
        super(exceptionMassage);
    }
}
