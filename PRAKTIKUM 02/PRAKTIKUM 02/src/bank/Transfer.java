package bank;

public class Transfer extends Transaction {
    private String sender;
    private String recipient;

    public Transfer(String date, String description, double amount, String sender, String recipient) {
        super(date, description, amount);
        setSender(sender);
        setRecipient(recipient);
    }

    public String getRecipient() {
        return recipient;
    }

    @Override
    public void setAmount(double amount)
    {
        if(amount > 0){
            this.amount = amount;
        }
        throw new IllegalArgumentException("Number must be positive");
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
