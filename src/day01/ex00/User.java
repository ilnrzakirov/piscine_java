package day01.ex00;

public class User {

    private Integer identifier;
    private Integer balance;
    private String name;

    public User(Integer identifier, Integer balance, String name) {
        this.identifier = identifier;

        if (balance > 0) {
            this.balance = balance;
        }
        else {
            this.balance = 0;
        }
        this.name = name;
    }

    public User() {
    }

    public void setBalance(Integer balance) {
        if (balance > 0) {
            this.balance = balance;
        } else {
            this.balance = 0;
        }
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBalance() {
        return balance;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public void printInfo(){
        System.out.println("Name: " + this.name + ", Balance: " + this.balance + " id: " + this.identifier);
    }
}
