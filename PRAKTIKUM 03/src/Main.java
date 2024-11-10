import bank.*;

public class Main {
    public static void main(String[] args) {
        printHeader("BANKING SYSTEM TEST RESULTS");

        // Test Payment class
        printSectionHeader("PAYMENT TESTS");

        Payment depositPayment = new Payment("2024-10-27", "Deposit", 1000, 0.05, 0.1);
        Payment withdrawalPayment = new Payment("2024-10-27", "Withdrawal", -1000, 0.05, 0.1);

        // Testing calculate() method for Payment
        printSubsectionHeader("Calculate Method Tests");
        printTestResult("Deposit Payment Calculation", depositPayment.calculate());
        printTestResult("Withdrawal Payment Calculation", withdrawalPayment.calculate());

        // Testing toString() method for Payment
        printSubsectionHeader("ToString Method Tests");
        printTestResult("Deposit Payment", depositPayment.toString());
        printTestResult("Withdrawal Payment", withdrawalPayment.toString());

        // Testing equals() method for Payment
        printSubsectionHeader("Equals Method Tests");
        Payment anotherDepositPayment = new Payment("2024-10-27", "Deposit", 1000, 0.05, 0.1);
        printTestResult("Payments Equality Test", depositPayment.equals(anotherDepositPayment));

        // Test Transfer class
        printSectionHeader("TRANSFER TESTS");

        Transfer transfer1 = new Transfer("2024-10-27", "Transfer", 500, "Alice", "Bob");
        Transfer transfer2 = new Transfer("2024-10-27", "Transfer", 500, "Alice", "Bob");

        // Testing calculate() method for Transfer
        printSubsectionHeader("Calculate Method Tests");
        printTestResult("Transfer Calculation", transfer1.calculate());

        // Testing toString() method for Transfer
        printSubsectionHeader("ToString Method Tests");
        printTestResult("Transfer Details", transfer1.toString());

        // Testing equals() method for Transfer
        printSubsectionHeader("Equals Method Tests");
        printTestResult("Transfers Equality Test", transfer1.equals(transfer2));

        printFooter();
    }

    // Utility methods for formatted output
    private static void printHeader(String text) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" ".repeat((60 - text.length())/2) + text);
        System.out.println("=".repeat(60));
    }

    private static void printSectionHeader(String text) {
        System.out.println("\n" + "-".repeat(50));
        System.out.println(" ".repeat(5) + text);
        System.out.println("-".repeat(50));
    }

    private static void printSubsectionHeader(String text) {
        System.out.println("\n• " + text + ":");
        System.out.println("  " + "-".repeat(30));
    }

    private static void printTestResult(String testName, Object result) {
        System.out.println("  → " + testName + ":");
        System.out.println("    " + result);
    }

    private static void printFooter() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" ".repeat(21) + "END OF TESTS");
        System.out.println("=".repeat(60));
    }
}