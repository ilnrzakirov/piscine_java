package app;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Checker {

    public Checker() {
    }

    public String getClassName(Object object){
        Class className = object.getClass();
        String classNameString = className.getName();
        String[] getName = classNameString.split("\\.");
        return getName[1];
    }

    public Field[] getFields(Object object){
        Class className = object.getClass();
        Field[] typeField = className.getDeclaredFields();
        return typeField;
    }

    public Method[] getMethods(Object object) {
        Class className = object.getClass();
        Method[] methods = className.getDeclaredMethods();
        return methods;
    }
}
