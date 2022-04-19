package day01.ex04;

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
}

