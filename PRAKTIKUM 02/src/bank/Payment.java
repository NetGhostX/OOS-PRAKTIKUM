package bank;

public class Payment extends Transaction implements CalculateBill{
    private double  incomingInterest;
    private double outgoingInterest;

    public Payment(String date, String description, double amount, double incomingInterest, double outgoingInterest) {
        super(date, description, amount);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    public Payment(Payment payment) {
        super(payment.getDate(), payment.getDescription(), payment.getAmount());
        this.incomingInterest = payment.getIncomingInterest();
        this.outgoingInterest = payment.getOutgoingInterest();
    }

    public double getIncomingInterest() {
        return this.incomingInterest;
    }
    public double getOutgoingInterest(){
        return this.outgoingInterest;
    }

    public void setIncomingInterest(double incomingInterest){
            if(incomingInterest > 0 && incomingInterest <1)
            {
                this.incomingInterest = incomingInterest;
            }
            else{
                throw new IllegalArgumentException("Incoming interest must be between 0 and 1");
            }
    }
    public void setOutgoingInterest(double outgoingInterest){
        if(outgoingInterest > 0 && outgoingInterest <1)
        {
            this.outgoingInterest = outgoingInterest;
        }
        else{
            throw new IllegalArgumentException("Outgoing interest must be between 0 and 1");
        }
    }

    @Override
    public double calculate() {
        if (getAmount() > 0) {
            return getAmount() - (getAmount() * getIncomingInterest());
        }
        else{
            return getAmount() + (getAmount() *getOutgoingInterest());
        }
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
                " incoming interest='" + getIncomingInterest() + '\'' +
                ", outgoing interest='" + getOutgoingInterest() + "}" + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Payment payment = (Payment) o;
        return Double.compare(getIncomingInterest(), payment.getIncomingInterest()) == 0 && Double.compare(getOutgoingInterest(), payment.getOutgoingInterest()) == 0;
    }
}


