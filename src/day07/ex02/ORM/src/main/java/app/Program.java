package app;

import models.OrmManager;
import models.User;

public class Program {

    public static void main(String[] args) throws ClassNotFoundException {

        OrmManager ormManager = new OrmManager();
        ormManager.init();
        User user = new User(1L, "Tom", "Djery", 2);
        ormManager.save(user);
    }
}
