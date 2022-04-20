package day01.ex04;

public class Program {

    public static void main(String[] args) {
        User user1 = new User(1000, "Tom");
        User user2 = new User(1000, "Bob");
        TransactionsService transactionsService = new TransactionsService();
        transactionsService.addUser(user1);
        transactionsService.addUser(user2);
        transactionsService.executeTransaction(user1.getId(), user2.getId(), 100);
        transactionsService.executeTransaction(user1.getId(), user2.getId(), 500);
        transactionsService.executeTransaction(user2.getId(), user1.getId(), 300);
        user1.printInfo();
        user2.printInfo();
        user1.printTransactionList();
        System.out.println();
        user2.printTransactionList();
        Transaction[] array = transactionsService.getInvalidTransaction();
        System.out.println();
        user1.printTransactionList();
        transactionsService.executeTransaction(user2.getId(), user1.getId(), 300);
    }
}
