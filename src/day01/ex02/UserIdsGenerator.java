package day01.ex02;

public class UserIdsGenerator {
    private static day01.ex01.UserIdsGenerator object;
    private static Integer identifier = 0;

    public static day01.ex01.UserIdsGenerator getInstance(){
        if (object == null){
            object = new day01.ex01.UserIdsGenerator();
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
