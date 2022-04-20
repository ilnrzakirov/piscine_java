package day01.ex01;

public class UserIdsGenerator {

    private static UserIdsGenerator object;
    private static Integer identifier = 0;

    public static UserIdsGenerator getInstance(){
        if (object == null){
            object = new UserIdsGenerator();
        }
        return object;
    }

    public int generateId(){
        return ++identifier;
    }

    public static Integer getIdentifier() {
        return identifier;
    }
}
