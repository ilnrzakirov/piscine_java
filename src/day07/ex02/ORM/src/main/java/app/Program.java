package app;

import models.OrmManager;
import models.User;

public class Program {

    public static void main(String[] args) throws ClassNotFoundException {

        OrmManager ormManager = new OrmManager();
        ormManager.init();
        User user = new User(1L, "Tom", "Djery", 2);
        ormManager.save(user);
        User user2;
        user2 = ormManager.findById(user.getId(), User.class);
        System.out.println(user2);
    }
}
