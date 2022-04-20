package day01.ex04;

public class UsersArrayList implements UserList {

    private Integer arraySize = 10;
    private User[] users = new User[arraySize];
    private Integer count = 0;

    @Override
    public void addUser(User newUser) {
        if (this.count == this.arraySize){
            User[] users = new User[arraySize * 2];
            for (int i = 0; i < this.arraySize; i++){
                users[i] = this.users[i];
            }
            this.arraySize = this.arraySize * 2;
            this.users = users;
            this.users[count++] = newUser;
        } else {
            this.users[count++] = newUser;
        }
    }

    @Override
    public User getUserById(Integer id) {
        for (int i = 0; i < this.count; i++){
            if (users[i].getId() == id){
                return users[i];
            }
        }
        throw new UserNotFoundException("User not found");
    }

    @Override
    public User getUserByIndex(Integer index) {
        if (index <= this.count || index > 0){
            return users[index];
        }
        throw new UserNotFoundException("User not found");
    }

    @Override
    public Integer getUserCount() {
        return this.count;
    }

    public Integer getArraySize() {
        return arraySize;
    }

    public void printInfo(){
        for (int i = 0; i < this.count; i++){
            System.out.print(i + "\tName: " + users[i].getName() + "\tbalance: " + users[i].getBalance());
            System.out.println("\tid: " + users[i].getId());
        }
    }
}
