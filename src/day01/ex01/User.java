package day01.ex01;

public class User {
    private Integer id;
    private Integer balance;
    private String name;

    public void setBalance(Integer balance) {
        if (balance > 0) {
            this.balance = balance;
        }
        else {
            this.balance = 0;
        }
    }

    public User() {
        this.id = UserIdsGenerator.getInstance().generateId();
    }

    public User(Integer balance, String name) {
        if (balance > 0) {
            this.balance = balance;
        }
        else {
            this.balance = 0;
        }
        this.name = name;
        this.id = UserIdsGenerator.getInstance().generateId();
    }

    public void setName(String name) {
        this.name = name;
    }
}
