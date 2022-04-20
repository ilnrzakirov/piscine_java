package day01.ex03;

public class UserIdsGenerator {
    private static UserIdsGenerator object;
    private static Integer identifier = 0;

    public static UserIdsGenerator getInstance(){
        if (object == null){
            object = new UserIdsGenerator();
        }
        return object;
    }

    public Integer generateId(){
        return ++identifier;
    }

    public static Integer getIdentifier() {
        return identifier;
    }
}
