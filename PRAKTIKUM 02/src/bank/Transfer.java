package bank;

public class Transfer extends Transaction implements CalculateBill {
    private String sender;
    private String recipient;

    //General constructor
    public Transfer(String date, String description, double amount, String sender, String recipient) {
        super(date, description, amount);
        setSender(sender);
        setRecipient(recipient);
    }

    //copy constructor
    public Transfer(Transfer Object){
        super(Object.getDate(),Object.getDescription(),Object.getAmount());
        setSender(Object.getSender());
        setRecipient(Object.getRecipient());
    }

    //Getter and Setter
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

    @Override
    public double calculate() {
        return getAmount();
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
                " sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                '}';
    }
}
