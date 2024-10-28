package bank;

public class Transfer extends Transaction {
    private String sender;
    private String recipient;

    public Transfer(String date, String description, double amount, String sender, String recipient) {
        super(date, description, amount);
        setSender(sender);
        setRecipient(recipient);
    }

    public Transfer(Transfer Object){
        super(Object.getDate(),Object.getDescription(),Object.getAmount());
        setSender(Object.getSender());
        setRecipient(Object.getRecipient());
    }
    public String getRecipient() {
        return recipient;
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
