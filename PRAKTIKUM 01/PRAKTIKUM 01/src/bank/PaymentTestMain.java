package bank;

public class PaymentTestMain {
    public static void main(String[] args) {
        try {
            constructorInitializesAttributesCorrectly();
            constructorWithAllAttributesInitializesCorrectly();
            copyConstructorCreatesIdenticalObject();
            setIncomingInterestWithinValidRange();
            setIncomingInterestThrowsExceptionForInvalidValue();
            setOutgoingInterestWithinValidRange();
            setOutgoingInterestThrowsExceptionForInvalidValue();
            printAllOutputsCorrectly();
            System.out.println("All tests passed.");
        } catch (AssertionError e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    static void constructorInitializesAttributesCorrectly() {
        Payment payment = new Payment("2023-10-01", 100.0, "Salary");
        assert "2023-10-01".equals(payment.date) : "Date mismatch";
        assert 100.0 == payment.getAmount() : "Amount mismatch";
        assert "Salary".equals(payment.description) : "Description mismatch";
    }

    static void constructorWithAllAttributesInitializesCorrectly() {
        Payment payment = new Payment("2023-10-01", 100.0, "Salary", 0.05, 0.02);
        assert "2023-10-01".equals(payment.date) : "Date mismatch";
        assert 100.0 == payment.getAmount() : "Amount mismatch";
        assert "Salary".equals(payment.description) : "Description mismatch";
        assert 0.03 == payment.getIncomingInterest() : "Incoming interest mismatch";
        assert 0.02 == payment.getOutgoingInterest() : "Outgoing interest mismatch";
    }

    static void copyConstructorCreatesIdenticalObject() {
        Payment original = new Payment("2023-10-01", 100.0, "Salary", 0.05, 0.02);
        Payment copy = new Payment(original);
        assert original.date.equals(copy.date) : "Date mismatch";
        assert original.getAmount() == copy.getAmount() : "Amount mismatch";
        assert original.description.equals(copy.description) : "Description mismatch";
        assert original.getIncomingInterest() == copy.getIncomingInterest() : "Incoming interest mismatch";
        assert original.getOutgoingInterest() == copy.getOutgoingInterest() : "Outgoing interest mismatch";
    }

    static void setIncomingInterestWithinValidRange() {
        Payment payment = new Payment("2023-10-01", 100.0, "Salary");
        payment.setIncomingInterest(0.5);
        assert 0.5 == payment.getIncomingInterest() : "Incoming interest mismatch";
    }

    static void setIncomingInterestThrowsExceptionForInvalidValue() {
        Payment payment = new Payment("2023-10-01", 100.0, "Salary");
        try {
            payment.setIncomingInterest(1.5);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            assert true;
        }
    }

    static void setOutgoingInterestWithinValidRange() {
        Payment payment = new Payment("2023-10-01", 100.0, "Salary");
        payment.setOutgoingInterest(0.5);
        assert 0.5 == payment.getOutgoingInterest() : "Outgoing interest mismatch";
    }

    static void setOutgoingInterestThrowsExceptionForInvalidValue() {
        Payment payment = new Payment("2023-10-01", 100.0, "Salary");
        try {
            payment.setOutgoingInterest(1.5);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            assert true;
        }
    }

    static void printAllOutputsCorrectly() {
        Payment payment = new Payment("2023-10-01", 100.0, "Salary", 0.05, 0.02);
        payment.printAll();
        // This test would need to capture the output stream to verify the printed content
    }
}