package edu.app;

public class NotSavedSubEntityException extends  RuntimeException{

    public NotSavedSubEntityException(String massage){
        super(massage);
    }
}
