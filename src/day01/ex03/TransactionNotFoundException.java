package day01.ex03;

public class TransactionNotFoundException extends RuntimeException{

    public TransactionNotFoundException(String exceptionMassage){
        super(exceptionMassage);
    }
}
