package day01.ex04;

import java.util.UUID;

public class TransactionsService {

    TransactionsList transactionsList = new TransactionsLinkedList();
    UserList userList = new UsersArrayList();
    private TransactionsLinkedList invalidTransactionList = new TransactionsLinkedList();

    public void addUser(User user){
        this.userList.addUser(user);
    }

    public int getUserBalance(Integer id){
        return userList.getUserById(id).getBalance();
    }

    public int getUserBalance(User user){
        return user.getBalance();
    }

    public void executeTransaction(Integer senderId, Integer recipientId, Integer amount){
        User sender = userList.getUserById(senderId);
        User recipient = userList.getUserById(recipientId);

        if (senderId == recipientId || amount < 0 || sender.getBalance() < amount){
            throw new IllegalTransactionException("Illegal Transaction");
        }

        Transaction credit = new Transaction(sender, recipient, -amount, Transaction.Category.CREDIT);
        Transaction debit = new Transaction(recipient, sender, amount, Transaction.Category.DEBIT);
        debit.setIdentifier(credit.getIdentifier());
        recipient.getTransactionsList().addTransaction(debit);
        sender.getTransactionsList().addTransaction(credit);
        recipient.setBalance(recipient.getBalance() + amount);
        sender.setBalance(sender.getBalance() - amount);
    }

    public Transaction[] getTransactionList(Integer userId){
        return userList.getUserById(userId).getTransactionsList().toArray();
    }

    public void removeTransactionById(UUID transactionId, Integer userId){
        userList.getUserById(userId).getTransactionsList().removeTransactionById(transactionId);
    }

    public Transaction[] getInvalidTransaction(){
        TransactionsLinkedList list = new TransactionsLinkedList();

        for (int i = 0; i < userList.getUserCount(); i++) {
            User user = userList.getUserByIndex(i);
            if (user != null) {
                int size = user.getTransactionsList().getSize();
                for (int j = 0; j < size; j++) {
                    list.addTransaction(user.getTransactionsList().toArray()[j]);
                }
            }
        }

        TransactionsLinkedList result = new TransactionsLinkedList();
        for (int i = 0; i < list.getSize(); i++) {
            int count = 0;
            for (int j = 0; j < list.getSize(); j++) {
                if (list.toArray()[i].getIdentifier() == list.toArray()[j].getIdentifier()) {
                    count++;
                }
            }
            if (count != 2) {
                result.addTransaction(list.toArray()[i]);
            }
        }
        return result.toArray();
    }
}

