package day01.ex04;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String exceptionMassage){
        super(exceptionMassage);
    }
}
