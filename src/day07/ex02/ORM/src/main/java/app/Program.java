package app;

import models.OrmManager;

public class Program {

    public static void main(String[] args) throws ClassNotFoundException {

        OrmManager ormManager = new OrmManager();
        ormManager.init();
    }
}
