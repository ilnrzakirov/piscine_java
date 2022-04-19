package day01.ex03;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private Transaction start;
    private Transaction end;

    @Override
    public void addTransaction(Transaction newTransaction) {
        if (this.start == null){
            this.start = newTransaction;
            this.end = newTransaction;
        } else {
            this.end.setNext(newTransaction);
            this.end = newTransaction;
        }
    }

    @Override
    public void removeTransactionById(UUID id) {
        Transaction temp;
        Transaction prev;

        temp = this.start.getNext();
        prev = this.start;
        if (prev.getIdentifier() == id){
            this.start = temp;
        }
        while (temp != null){
            if (temp.getIdentifier() == id){
                prev.setNext(temp.getNext());
            }
            if (prev.getNext() == null || temp.getNext() == null) {
                break;
            }
            prev = prev.getNext();
            temp = prev.getNext();
        }
    }

    @Override
    public Transaction[] toArray() {
        return new Transaction[0];
    }

    public void showTransaction(){
        Transaction temp;

        temp = this.start;
        while (temp != null){
            temp.printTransferInfo();
            temp = temp.getNext();
        }
    }
}
