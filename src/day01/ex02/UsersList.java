package day01.ex02;

interface UserList{
    void addUser(User newUser);
    User getUserById(Integer id) throws Exception;
    User getUserByIndex(Integer index) throws Exception;
    Integer getUserCount();
}