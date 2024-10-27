package bank;

//With this class, client is able to top up their account and receive money.
public class Payment {

    //attributes
    String date;
    private double amount;
    String description;
    private double incomingInterest;
    private double outgoingInterest;

    //Constructor for date, amount and description

    Payment(String date, double amount, String description)
    {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }
    
    //all attributes
    Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest)
    {
        this(date,amount,description);
        this.incomingInterest = incomingInterest;
        this.outgoingInterest = outgoingInterest;
    }
    
    //Copy constructor
    Payment(Payment otherPayment)
    {
        this.date = otherPayment.date;
        this.amount = otherPayment.amount;
        this.description = otherPayment.description;
        this.incomingInterest = otherPayment.incomingInterest;
        this.outgoingInterest = otherPayment.outgoingInterest;
    }
    //Getter and Setter for Private attributes
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getIncomingInterest() {
        return incomingInterest;
    }
    public void setIncomingInterest(double incomingInterest) {
        if (incomingInterest >= 0 && incomingInterest <= 1) {
            this.incomingInterest = incomingInterest;
        } else {
            throw new IllegalArgumentException("Incoming interest must be between 0 and 1");
        }
    }

    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    public void setOutgoingInterest(double outgoingInterest) {
        if(outgoingInterest >= 0 && outgoingInterest <= 1)
        {
            this.outgoingInterest = outgoingInterest;
        } else{
            throw new IllegalArgumentException("Outgoing interest must be between 0 and 1");
        }
    }

    public void printAll()
    {
        System.out.println("Description: " + description);
        System.out.println("Date: " + date);
        System.out.println("Amount: " + amount);
        System.out.println("Incoming interest: " + getIncomingInterest());
        System.out.println("Outgoin interest: " + getOutgoingInterest());
    }


    /**
     * @param date is the date of the transaction.
     * @param amount is the amount of money being transferred.
     * @param description is the description of the transaction.
     * @param incomingInterest represents interest charged on incoming transactions
     * @param outgoingInterest represents interest charged on outgoing transactions
     * */
}



