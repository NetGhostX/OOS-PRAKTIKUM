package bank;

public class Payment extends Transaction{
    private double  incomingInterest;
    private double outgoingInterest;

    public Payment(String date, String description, double amount, double incomingInterest, double outgoingInterest) {
        super(date, description, amount);

        this.incomingInterest =  incomingInterest;
        this.outgoingInterest = outgoingInterest;
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
}


