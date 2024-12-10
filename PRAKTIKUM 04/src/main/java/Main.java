import java.util.List;

import bank.IncomingTransfer;
import bank.OutgoingTransfer;
import bank.Payment;
import bank.PrivateBank;
import bank.PrivateBankAlt;
import bank.Transaction;
import bank.Transfer;
import bank.exceptions.InvalidTransactionException;

public class Main {
    // ANSI escape codes for color output
    public static final String RESET = "\u001B[0m";
    public static final String HEADER = "\u001B[95m";    // Bright Magenta
    public static final String SUBHEADER = "\u001B[96m"; // Bright Cyan
    public static final String SUCCESS = "\u001B[92m";   // Bright Green
    public static final String ERROR = "\u001B[91m";     // Bright Red
    public static final String YELLOW = "\u001B[93m";    // Bright Yellow
    public static final String RED = "\u001B[91m";       // Bright Red

    public static void main(String[] args) {
        printHeader("PRIVATEBANK FUNCTIONALITY TESTS");

        try {
            // Create instances of PrivateBank and PrivateBankAlt
            PrivateBank bank = new PrivateBank("MyPrivateBank", 0.05, 0.1);
            PrivateBankAlt bankAlt = new PrivateBankAlt("MyPrivateBankAlt", 0.05, 0.1);

            // Test the equals method of PrivateBank with two instances (expected: true)
            printSubheader("Testing PrivateBank equals method (Expected: true)");
            PrivateBank bank2 = new PrivateBank("MyPrivateBank", 0.05, 0.1);
            boolean isEqual = bank.equals(bank2);
            System.out.println("Result: " + (isEqual ? SUCCESS + "true" + RESET : ERROR + "false" + RESET));

            // Modify bank2 and test equals again (expected: false)
            bank2.setOutgoingInterest(0.2);
            printSubheader("Testing PrivateBank equals method after modification (Expected: false)");
            isEqual = bank.equals(bank2);
            System.out.println("Result: " + (isEqual ? SUCCESS + "true" + RESET : ERROR + "false" + RESET));

            // Create accounts
            bank.createAccount("Alice");
            bankAlt.createAccount("Bob");

            // Create transactions
            Payment deposit = new Payment("2024-11-10", "Salary", 2000.0, 0.05, 0.1);
            Payment withdrawal = new Payment("2024-11-11", "Groceries", -150.0, 0.05, 0.1);
            Payment bonus = new Payment("2024-11-12", "Bonus", 500.0, 0.05, 0.1);
            Transfer outgoingTransfer = new OutgoingTransfer("2024-11-13", "Rent", 800.0, "Alice", "Landlord");
            Transfer incomingTransfer = new IncomingTransfer("2024-11-14", "Gift", 300.0, "Charlie", "Alice");

            // Add transactions to Alice's account in PrivateBank
            bank.addTransaction("Alice", deposit);
            bank.addTransaction("Alice", withdrawal);
            bank.addTransaction("Alice", bonus);
            bank.addTransaction("Alice", outgoingTransfer);
            bank.addTransaction("Alice", incomingTransfer);

            // Display Alice's transactions with color highlighting
            printSubheader("Alice's Transactions:");
            List<Transaction> aliceTransactions = bank.getTransactions("Alice");
            for (Transaction t : aliceTransactions) {
                if (t.calculate() > 0) {
                    // Positive transactions (Einnzahlung) in yellow
                    System.out.println(YELLOW + t + RESET);
                } else {
                    // Negative transactions (Auszahlung) in red
                    System.out.println(RED + t + RESET);
                }
            }

            // Display Alice's account balance
            double aliceBalance = bank.getAccountBalance("Alice");
            System.out.println("\nAlice's Account Balance: " + SUCCESS + aliceBalance + RESET);

            // Test sorting transactions
            printSubheader("Alice's Transactions Sorted Ascending by Amount:");
            List<Transaction> sortedTransactions = bank.getTransactionsSorted("Alice", true);
            for (Transaction t : sortedTransactions) {
                System.out.println(t);
            }

            // Test filtering transactions
            printSubheader("Alice's Negative Transactions (Withdrawals):");
            List<Transaction> negativeTransactions = bank.getTransactionsByType("Alice", false);
            for (Transaction t : negativeTransactions) {
                System.out.println(t);
            }

            // Test exception handling with invalid transaction (negative amount Payment)
            printSubheader("Testing Exception Handling with Invalid Transaction");
            try {
                Payment invalidPayment = new Payment("2024-11-15", "Invalid Payment", -1000.0, 0.05, 0.1);
                bank.addTransaction("Alice", invalidPayment);
            } catch (InvalidTransactionException e) {
                System.out.println(ERROR + "Caught InvalidTransactionException: " + e.getMessage() + RESET);
            }

            // Test removing a transaction
            printSubheader("Testing Transaction Removal");
            bank.removeTransaction("Alice", bonus);
            System.out.println("Removed bonus transaction. Updated Transactions:");
            aliceTransactions = bank.getTransactions("Alice");
            for (Transaction t : aliceTransactions) {
                System.out.println(t);
            }
            // Updated account balance
            aliceBalance = bank.getAccountBalance("Alice");
            System.out.println("\nAlice's Updated Account Balance: " + SUCCESS + aliceBalance + RESET);

            // Test copying account from bank to bankAlt
            printSubheader("Testing Account Copying");
            bankAlt.createAccount("Alice", bank.getTransactions("Alice"));
            System.out.println("Copied Alice's account to bankAlt.");
            double aliceBalanceAlt = bankAlt.getAccountBalance("Alice");
            System.out.println("Alice's Account Balance in bankAlt: " + SUCCESS + aliceBalanceAlt + RESET);

        } catch (Exception e) {
            System.out.println(ERROR + "An error occurred during tests: " + e.getMessage() + RESET);
            e.printStackTrace();
        }

        printFooter();
    }

    // Utility methods for formatted output
    private static void printHeader(String text) {
        System.out.println("\n" + HEADER + "=".repeat(60) + RESET);
        System.out.println(HEADER + " ".repeat((60 - text.length()) / 2) + text + RESET);
        System.out.println(HEADER + "=".repeat(60) + RESET);
    }

    private static void printSubheader(String text) {
        System.out.println("\n" + SUBHEADER + "-".repeat(50) + RESET);
        System.out.println(SUBHEADER + "     " + text + RESET);
        System.out.println(SUBHEADER + "-".repeat(50) + RESET);
    }

    private static void printFooter() {
        System.out.println("\n" + HEADER + "=".repeat(60) + RESET);
        System.out.println(HEADER + " ".repeat(21) + "END OF TESTS" + RESET);
        System.out.println(HEADER + "=".repeat(60) + RESET);
    }
}