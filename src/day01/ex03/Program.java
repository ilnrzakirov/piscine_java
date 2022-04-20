package day01.ex03;

import java.util.UUID;

public class Program {

    public static void main(String[] args) {
        User user1 = new User(2500, "Bob");
        User user2 = new User(2300, "Tom");
        TransactionsLinkedList list = new TransactionsLinkedList();
        Transaction tr = new Transaction(user1, user2, -300, Transaction.Category.CREDIT);
        Transaction tr1 = new Transaction(user2, user1, 310, Transaction.Category.DEBIT);
        Transaction tr2 = new Transaction(user2, user1, -330, Transaction.Category.CREDIT);
        Transaction tr3 = new Transaction(user1, user2, 350, Transaction.Category.DEBIT);
        list.addTransaction(tr);
        list.addTransaction(tr1);
        list.addTransaction(tr2);
        list.addTransaction(tr3);
        list.showTransaction();
        list.removeTransactionById(tr1.getIdentifier());
        System.out.println();
        list.showTransaction();
        Transaction[] arrayTr = list.toArray();
        System.out.println();

        for (int i = 0; i < list.getSize(); i++){
            arrayTr[i].printTransferInfo();
        }

        list.removeTransactionById(UUID.randomUUID());
    }
}
