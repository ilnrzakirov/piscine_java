package day01.ex02;

interface UserList{

    void addUser(User newUser);
    User getUserById(Integer id);
    User getUserByIndex(Integer index);
    Integer getUserCount();
}