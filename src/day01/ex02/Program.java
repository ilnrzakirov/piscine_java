package day01.ex02;

public class Program {
    public static void main(String[] args) {
        User user1 = new User(1500, "Bob");
        User user2 = new User(100, "Tom");
        User user3 = new User(10000, "Tim");
        User user4 = new User(1400, "Jon");
        User user5 = new User(100, "Tom");
        User user6 = new User(10000, "Tim");
        User user7 = new User(1400, "Jon");
        User user8 = new User(100, "Tom");
        User user9 = new User(10000, "Tim");
        User user10 = new User(1400, "Jon");
        User user11 = new User(100, "Tom");
        User user12 = new User(10000, "Tim");
        User user13 = new User(1400, "Jon");

        UsersArrayList userList = new UsersArrayList();
        userList.addUser(user1);
        userList.addUser(user2);
        userList.addUser(user3);
        userList.addUser(user4);
        userList.addUser(user5);
        userList.addUser(user6);
        userList.addUser(user7);
        userList.addUser(user8);
        userList.addUser(user9);
        userList.addUser(user10);
        userList.addUser(user11);
        userList.addUser(user12);
        userList.addUser(user13);
        User user25 = userList.getUserById(user2.getId());
        user25.printInfo();
        User user26 = userList.getUserByIndex(3);
        user26.printInfo();
        System.out.println();
        userList.printInfo();
        System.out.println("ArraySize:\t" + userList.getArraySize());
        System.out.println("UserCount:\t"+userList.getUserCount());
        userList.getUserById(444);
    }
}
