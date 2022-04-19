package day01.ex04;


interface UserList {
    void addUser(User newUser);
    User getUserById(int id);
    User getUserByIndex(int index);
    Integer getUserCount();
}