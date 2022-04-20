package day01.ex04;

public class TransactionNotFoundException extends RuntimeException{

    public TransactionNotFoundException(String exceptionMassage){
        super(exceptionMassage);
    }
}
