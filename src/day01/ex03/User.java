package day01.ex03;

public class User {
    private final Integer id;
    private Integer balance;
    private String name;
    private TransactionsList transactionsList;

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
        this.balance = 0;
        this.transactionsList = new TransactionsLinkedList();
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
        this.transactionsList = new TransactionsLinkedList();
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBalance() {
        return balance;
    }

    public void printInfo(){
        System.out.println("Name: " + this.name + ", balance: " + this.balance + ", id: " + this.id);
    }
}

