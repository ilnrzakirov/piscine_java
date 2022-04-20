package day01.ex03;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {

    private Transaction start;
    private Transaction end;
    private Integer size = 0;

    @Override
    public void addTransaction(Transaction newTransaction) {
        if (this.start == null){
            this.start = newTransaction;
        } else {
            this.end.setNext(newTransaction);
        }

        this.end = newTransaction;
        size++;
    }

    @Override
    public void removeTransactionById(UUID id) {
        Transaction temp;
        Transaction prev;

        if (this.start != null) {
            temp = this.start.getNext();
            prev = this.start;

            if (prev.getIdentifier() == id) {
                this.start = temp;
                size--;
                return;
            }

            while (temp != null) {
                if (temp.getIdentifier() == id) {
                    prev.setNext(temp.getNext());
                    size--;
                    return;
                }
                prev = prev.getNext();
                temp = prev.getNext();
            }
        }

        throw new TransactionNotFoundException("Transaction not found");
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] transactionArray = new Transaction[this.size];
        int i = 0;
        Transaction temp = this.start;

        while (temp != null){
            transactionArray[i] = temp;
            i++;
            temp = temp.getNext();
        }

        return transactionArray;
    }

    public Integer getSize() {
        return size;
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
