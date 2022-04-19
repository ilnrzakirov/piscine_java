package day01.ex00;

public class Program {
    public static void main(String[] args) {
        User user1 = new User(1, 0, "Bob");
        User user2 = new User(2, 100, "Tim");
        user1.printInfo();
        user2.printInfo();
        User user3 = new User();
        user3.setBalance(500);
        user3.setName("Jone");
        user3.setIdentifier(3);
        user3.printInfo();

        Transaction tr1 = new Transaction(user1, user2, -200, Transaction.Category.CREDIT);
        Transaction tr2 = new Transaction(user3, user2, 200, Transaction.Category.DEBIT);
        tr1.printTransferInfo();
        tr2.printTransferInfo();
    }
}
