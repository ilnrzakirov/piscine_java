package app;

import classes.Animal;
import classes.Person;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {

    private static final String CLASSES = "classes.";

    public static void main(String[] args) {
        Animal animal = new Animal();
        Person person = new Person();
        Checker checker = new Checker();
        System.out.println("Classes:");
        String animalClassName = checker.getClassName(animal);
        String personClassName = checker.getClassName(person);
        System.out.println("-  " + animalClassName);
        System.out.println("-  " + personClassName);
        System.out.println("-----------------------");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter class name: ");
        String inputLine = scanner.nextLine();
        if (!inputLine.equals(checker.getClassName(animal)) && !inputLine.equals(checker.getClassName(person))){
            System.err.println("Class not found");
            System.exit(-1);
        }

        System.out.println("-----------------------");
        System.out.println("fields:");
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
            System.out.println("methods:");

            for (Method method : methods) {
                if (!method.getName().equals("toString")) {
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
                            " (" + parametrs + ")");
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println("-----------------------");
        System.out.println("Let’s create an object.");
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

        System.out.print("Object created: ");
        Object object = null;

        try {
            object = constructorClass.newInstance(constructorParam.toArray());
            System.out.println(object);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException  e) {
            e.printStackTrace();
        }

        System.out.println("Enter name of the field for changing: ");
        String fieldName = scanner.nextLine();

        int i = 0;
        for (i = 0; i < fields.length; i++){
            if (fieldName.equals(fields[i].getName())){
                break;
            }
        }
        System.out.println(i);
        System.out.println("Enter " + fields[i].getType().getSimpleName() + " value");
        Object object1 = getParamObject(parameters[i], scanner);
        fields[i].setAccessible(true);

        try {
            fields[i].set(object, object1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println("Object updated: " + object);
    }

    private static Object getParamObject(Parameter parameter, Scanner scanner) {
        String paramName = parameter.getType().getSimpleName().toLowerCase();
        switch (paramName) {
            case "string":
                return scanner.nextLine();
            case "int":
            case "integer":
                return scanner.nextInt();
            case "boolean":
                return scanner.nextBoolean();
            case "long":
                return scanner.nextLong();
            case "double":
                return scanner.nextDouble();
            default:
                System.err.println("Error");
                System.exit(-1);
        }
        return null;
    }
}
