package day01.ex04;

import javafx.beans.binding.When;

import java.util.UUID;

public class TransactionsService {
    TransactionsList transactionsList = new TransactionsLinkedList();
    UserList userList = new UsersArrayList();

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
        if (amount < 0 || sender.getBalance() < amount || senderId == recipientId){
            throw new IllegalTransactionException(" Illegal Transaction");
        }
        Transaction credit = new Transaction(recipient, sender, -amount, Transaction.Category.CREDIT);
        Transaction debit = new Transaction(recipient, sender, amount, Transaction.Category.DEBIT);
        debit.setIdentifier(credit.getIdentifier());
        sender.getTransactionsList().addTransaction(credit);
        recipient.getTransactionsList().addTransaction(debit);
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
        TransactionsList allTransactionList = new TransactionsLinkedList();
        for (int i = 0; i < userList.getUserCount(); i++){
            int count = userList.getUserByIndex(i).getTransactionsList().getSize();
            for (int k = 0; k < count; k++){
                allTransactionList.addTransaction(userList.getUserByIndex(i).getTransactionsList().toArray()[k]);
            }
        }
        TransactionsList resultList = new TransactionsLinkedList();
        while (allTransactionList.getSize() < 1){
            int flag = 0;
            int j;
            for (j = 1; j < allTransactionList.getSize(); j++){
                if (allTransactionList.getTransactionByIndex(0).getIdentifier() !=
                        allTransactionList.getTransactionByIndex(j).getIdentifier()){
                    flag = 1;
                    break;
                }
            }
            if (flag == 1){
                resultList.addTransaction(allTransactionList.getTransactionByIndex(j));
            }
            allTransactionList.removeTransactionById(allTransactionList.getTransactionByIndex(0).getIdentifier());
            allTransactionList.removeTransactionById(allTransactionList.getTransactionByIndex(j).getIdentifier());
        }
        return resultList.toArray();
    }
}

