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
        if (senderId == recipientId || amount < 0){
            throw new IllegalTransactionException("Illegal Transaction");
        }
        Transaction credit = new Transaction(sender, recipient, -amount, Transaction.Category.CREDIT);
        Transaction debit = new Transaction(recipient, sender, amount, Transaction.Category.DEBIT);
        debit.setIdentifier(credit.getIdentifier());
        recipient.getTransactionsList().addTransaction(debit);
        if (sender.getBalance() < amount){
            invalidTransactionList.addTransaction(credit);
            throw new IllegalTransactionException("Illegal Transaction");
        }
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
        TransactionsList notValid = new TransactionsLinkedList();

        for (int i = 1; i <= userList.getUserCount(); i++) {
            Transaction[] transactions = this.userList.getUserById(i).getTransactionsList().toArray();
            for (Transaction t : transactions) {
                boolean valid = false;
                for (Transaction t2 : t.getRecipient().getTransactionsList().toArray()) {
                    if (t.getIdentifier() == t2.getIdentifier()) {
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    notValid.addTransaction(t);
                }
            }
        }
        return notValid.toArray();
    }
}

