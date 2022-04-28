package app;

import classes.Animal;
import classes.Person;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class Program {

    private static final String CLASSES = "classes.";
    private static final String ERROR = "Error";
    public static final String STRING = "string";
    public static final String INT = "int";
    public static final String INTEGER = "integer";
    public static final String BOOLEAN = "boolean";
    public static final String LONG = "long";
    public static final String DOUBLE = "double";
    public static final String ERROR1 = "Error";
    public static final String TYPE_ERROR = "Type error";
    public static final String INCORRECT_PARAM = "Incorrect param";
    public static final String METHOD_RETURNED = "Method returned:\n";
    public static final String VOID = "void";
    public static final String ENTER_NAME_OF_THE_METHOD_FOR_CALL = "-----------------------\nEnter name of the method for call: ";
    public static final String INCORRECT_PARAM1 = "Incorrect param";
    public static final String ENTER_NAME_OF_THE_FIELD_FOR_CHANGING = "Enter name of the field for changing: ";
    public static final String X = "-----------------------";
    public static final String OBJECT_CREATED = "Object created: ";
    public static final String LET_S_CREATE_AN_OBJECT = "Let’s create an object.";
    public static final String TO_STRING = "toString";
    public static final String METHODS = "methods:";
    public static final String FIELDS = "fields:";
    public static final String CLASS_NOT_FOUND = "Class not found";
    public static final String ENTER_CLASS_NAME = "Enter class name: ";
    public static final String CLASSES1 = "Classes:";
    public static final String METHOD_NOT_FOUND = "Method not found";

    public static void main(String[] args) {
        Reflections reflections;
        reflections = new Reflections("classes", new SubTypesScanner(false));
        Set<Class<?>> set = reflections.getSubTypesOf(Object.class);

        List<String> classes = set.stream()
                .map(Class::getSimpleName)
                .collect(Collectors.toList());
        System.out.println(CLASSES1);

        for (String aClass : classes) {
            System.out.println("-  " + aClass);
        }

        Checker checker = new Checker();
        System.out.println(X);
        Scanner scanner = new Scanner(System.in);
        System.out.println(ENTER_CLASS_NAME);
        String inputLine = scanner.nextLine();

        if (!classes.contains(inputLine)){
            System.err.println(CLASS_NOT_FOUND);
            System.exit(-1);
        }

        System.out.println(X);
        System.out.println(FIELDS);
        Field[] fields = null;
        Method[] methods = null;
        Class getClass = null;

        try {
            getClass = Class.forName(CLASSES + inputLine);
            fields = checker.getFields(getClass.newInstance());
            methods = checker.getMethods(getClass.newInstance());

            for (Field field : fields) {
                System.out.println("\t" + field.getType().getSimpleName() + " " + field.getName());
            }

            System.out.println(METHODS);

            for (Method method : methods) {
                if (!method.getName().equals(TO_STRING)) {
                    Class<?>[] clazz = method.getParameterTypes();
                    StringBuilder parametrs = new StringBuilder();

                    if (clazz.length > 0) {
                        parametrs.append(clazz[0].getSimpleName());

                        for (int i = 1; i < clazz.length; i++) {
                            parametrs.append(", ");
                            parametrs.append(clazz[i].getSimpleName());
                        }
                    }
                    System.out.println("\t" + method.getReturnType().getSimpleName() + " " + method.getName() +
                            "(" + parametrs + ")");
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.err.println(ERROR);
            System.exit(-1);
        }

        System.out.println(X);
        System.out.println(LET_S_CREATE_AN_OBJECT);
        Constructor constructorClass = null;
        Constructor[] constructors = getClass.getDeclaredConstructors();
        Parameter[] parameters = null;

        for (Constructor constructor : constructors) {
            if (constructor.getParameters().length > 0){
                constructorClass = constructor;
                parameters = constructor.getParameters();
                break;
            }
        }

        List<Object> constructorParam = new ArrayList<>();

        for (int i = 0; i < parameters.length; i++) {
            System.out.println(fields[i].getName() + ":");
            constructorParam.add(getParamObject(parameters[i], scanner));
        }

        if (!(constructorParam.get(constructorParam.size() -1 ) instanceof String)){
            scanner.nextLine();
        }

        System.out.print(OBJECT_CREATED);
        Object object = null;

        try {
            object = constructorClass.newInstance(constructorParam.toArray());
            System.out.println(object);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException  e) {
            System.err.println(ERROR);
            System.exit(-1);
        }

        System.out.println(X);
        System.out.println(ENTER_NAME_OF_THE_FIELD_FOR_CHANGING);
        String fieldName = scanner.nextLine();
        int i = 0;

        for (i = 0; i < fields.length; i++){
            if (fieldName.equals(fields[i].getName())){
                break;
            }
        }
        System.out.println("Enter " + fields[i].getType().getSimpleName() + " value");
        Object object1 = getParamObject(parameters[i], scanner);
        fields[i].setAccessible(true);

        try {
            fields[i].set(object, object1);
        } catch (IllegalAccessException e) {
            System.err.println(INCORRECT_PARAM1);
            System.exit(-1);
        }

        System.out.println("Object updated: " + object);
        System.out.println(ENTER_NAME_OF_THE_METHOD_FOR_CALL);
        runMethod(methods, object, scanner);
    }

    private static void runMethod(Method[] methods, Object object, Scanner scanner) {
        String inputLine = scanner.nextLine();

        for (Method method : methods) {
            Class<?>[] clazz = method.getParameterTypes();
            StringBuilder parametrs = new StringBuilder();

            if (clazz.length > 0) {
                parametrs.append(clazz[0].getSimpleName());

                for (int i = 1; i < clazz.length; i++) {
                    parametrs.append(", ");
                    parametrs.append(clazz[i].getSimpleName());
                }
            }
            if (inputLine.equals(method.getName() +"(" + parametrs +")")){
                List<Object> constructorParam = new ArrayList<>();
                Parameter[] parameters = method.getParameters();

                for (Parameter parameter : parameters) {
                    System.out.println("Enter " + parameter.getType().getSimpleName() +   " value:");
                    constructorParam.add(getParamObject(parameter, scanner));
                }
                method.setAccessible(true);

                try {
                    if (method.getReturnType().getSimpleName().equals(VOID)) {
                        method.invoke(object, constructorParam.toArray());
                    } else {
                        System.out.println(METHOD_RETURNED + method.invoke(object, constructorParam.toArray()));
                    }
                    return;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.err.println(INCORRECT_PARAM);
                    System.exit(-1);
                }
                System.out.println(method.getName() + "(" + parametrs + ")");
            }
        }
        System.err.println(METHOD_NOT_FOUND);
        System.exit(-1);
    }

    private static Object getParamObject(Parameter parameter, Scanner scanner) {
        String paramName = parameter.getType().getSimpleName().toLowerCase();
        try {
            switch (paramName) {
                case STRING:
                    return scanner.nextLine();
                case INT:
                case INTEGER:
                    return scanner.nextInt();
                case BOOLEAN:
                    return scanner.nextBoolean();
                case LONG:
                    return scanner.nextLong();
                case DOUBLE:
                    return scanner.nextDouble();
                default:
                    System.err.println(ERROR1);
                    System.exit(-1);
            }
        } catch (InputMismatchException error) {
            System.err.println(TYPE_ERROR);
            System.exit(-1);
        }
        return null;
    }
}
