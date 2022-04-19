package day01.ex00;

import java.util.UUID;

public class Transaction {
    private final UUID identifier;
    private User recipient;
    private User sender;
    private int transferAmount;
    private Category transferCategory;

    enum Category {
        DEBIT, CREDIT
    }

    public Transaction(User recipient, User sender, int transferAmount, Category transferCategory) {
        this.identifier = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.transferCategory = transferCategory;
        setTransferAmount(transferAmount);

    }

    public int getTransferAmount() {
        return transferAmount;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public Category getTransferCategory() {
        return transferCategory;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setTransferAmount(int transferAmount) {
        if (this.transferCategory == Category.CREDIT &&  transferAmount > 0){
            this.transferAmount = 0;
        } else if (this.transferCategory == Category.DEBIT && transferAmount < 0){
            this .transferAmount = 0;
        } else {
            this.transferAmount = transferAmount;
        }
    }

    public void setTransferCategory(Category transferCategory) {
        this.transferCategory = transferCategory;
    }

    public void printTransferInfo(){
        System.out.print("Sender: " + this.sender.getName() + ". Recipient: " + this.recipient.getName() );
        System.out.print(". Category: " + this.transferCategory + ". Amount: " + this.transferAmount + "\n");
    }
}
