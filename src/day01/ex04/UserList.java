package day01.ex04;


interface UserList {
    void addUser(User newUser);
    User getUserById(Integer id);
    User getUserByIndex(Integer index);
    Integer getUserCount();
}