package bank;

//With this class, client is only able to send money.
public class Transfer {

    private String date;
    private double amount;
    private String description;
    private String sender;
    private String recipient;

    //Constructor
    Transfer(String date, double amount, String description)
    {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    //General Constructor
    Transfer(String date, double amount, String description, String sender, String recipient)
    {
        this(date,amount,description);
        this.sender = sender;
        this.recipient = recipient;
    }

    //Getter and Setter
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if(amount >0)
        {
            this.amount = amount;
        }else {
            throw new IllegalArgumentException("Amount must me positive");
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

/**
 * @param date is the date of the transaction.
 * @param amount is the amount of money being transferred.
 * @param description is the description of the transaction.
 * @param sender is the sender of the money.
 * @param recipient is the recipient of the money.
 */
}
