package day01.ex04;

import java.util.UUID;

interface TransactionsList {

    void addTransaction(Transaction newTransaction);
    void removeTransactionById(UUID id);
    Transaction[] toArray();
    Integer getSize();
}
