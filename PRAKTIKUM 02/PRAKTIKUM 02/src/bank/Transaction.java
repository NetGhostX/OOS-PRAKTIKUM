package bank;

public abstract class Transaction {
    protected String date;
    protected double amount;
    protected String description;

    public Transaction(String date, String description, double amount) {
        setDate(date);
        setDescription(description);
        setAmount(amount);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
