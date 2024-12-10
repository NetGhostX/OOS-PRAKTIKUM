package bank;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.InvalidInterestArgument;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionAttributeException;
import bank.exceptions.TransactionDoesNotExistException;

/**
 * The PrivateBank class implements the Bank interface and provides concrete methods
 * to manage accounts and transactions.
 */
public class PrivateBank implements Bank {
    private String name;
    private double incomingInterest;
    private double outgoingInterest;
    private HashMap<String, List<Transaction>> accountsToTransactions = new HashMap<>();
    private String directoryName;

    /**
     * Constructs a new PrivateBank with the specified name and interest rates.
     *
     * @param name             the name of the bank
     * @param incomingInterest the interest rate for incoming transactions (deposits)
     * @param outgoingInterest the interest rate for outgoing transactions (withdrawals)
     * @throws IllegalArgumentException if the interest rates are not between 0 and 1
     */

    /**
     * Constructs a new PrivateBank with the specified name and interest rates.
     *
     * @param name             the name of the bank
     * @param incomingInterest the interest rate for incoming transactions (deposits)
     * @param outgoingInterest the interest rate for outgoing transactions (withdrawals)
     * @throws IllegalArgumentException if the interest rates are not between 0 and 1
     */
    public PrivateBank(String name, double incomingInterest, double outgoingInterest) {
        setName(name);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }


    public PrivateBank(String name, double incomingInterest, double outgoingInterest, String directoryName) throws IOException {
        setName(name);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
        this.directoryName = directoryName;

        // Create directory if it doesn't exist
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Read existing accounts
        readAccounts();
    }


    public PrivateBank(PrivateBank otherBank) {
        this.name = otherBank.name;
        this.incomingInterest = otherBank.incomingInterest;
        this.outgoingInterest = otherBank.outgoingInterest;
        // Note: We do not copy the accountsToTransactions map
    }


    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for incomingInterest
    public double getIncomingInterest() {
        return incomingInterest;
    }

    public void setIncomingInterest(double incomingInterest) {
        if (incomingInterest >= 0 && incomingInterest <= 1) {
            this.incomingInterest = incomingInterest;
        } else {
            throw new InvalidInterestArgument("Incoming interest must be between 0 and 1");
        }
    }

    // Getter and Setter for outgoingInterest
    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    public void setOutgoingInterest(double outgoingInterest) {
        if (outgoingInterest >= 0 && outgoingInterest <= 1) {
            this.outgoingInterest = outgoingInterest;
        } else {
            throw new InvalidInterestArgument("Outgoing interest must be between 0 and 1");
        }
    }


    public HashMap<String, List<Transaction>> getAccountsToTransactions() {
        return this.accountsToTransactions;
    }

    public void setAccountsToTransactions(HashMap<String, List<Transaction>> accountsToTransactions) {
        if (this.accountsToTransactions == null) {
            throw new IllegalArgumentException("accountToTransactions map cannot be null");
        }
        this.accountsToTransactions = accountsToTransactions;
    }


    public void setDirectoryName(String directoryName) {
        this.directoryName = this.directoryName;
    }


    /**
     * Adds an account to the bank.
     *
     * @param account the account to be added
     * @throws AccountAlreadyExistsException if the account already exists
     */

    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException, IOException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Account '" + account + "' already exists.");
        }
        accountsToTransactions.put(account, new ArrayList<>());
        writeAccount(account);
    }

    /**
     * Adds an account (with specified transactions) to the bank.
     * Important: duplicate transactions must not be added to the account!
     *
     * @param account      the account to be added
     * @param transactions a list of already existing transactions which should be added to the newly created account
     * @throws AccountAlreadyExistsException    if the account already exists
     * @throws TransactionAlreadyExistException if the transaction already exists
     * @throws TransactionAttributeException    if the validation check for certain attributes fail
     */
    @Override
    public void createAccount(String account, List<Transaction> transactions)
            throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException, IOException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Account '" + account + "' already exists.");
        }

        List<Transaction> newTransactions = new ArrayList<>();
        if (transactions != null) {
            for (Transaction transaction : transactions) {
                if (transaction == null) {
                    throw new TransactionAttributeException("Transaction cannot be null.");
                }
                if (newTransactions.contains(transaction)) {
                    throw new TransactionAlreadyExistException("Duplicate transaction found.");
                }
                newTransactions.add(transaction);
            }
        }
        accountsToTransactions.put(account, newTransactions);
        writeAccount(account);
    }

    /**
     * Adds a transaction to an already existing account.
     *
     * @param account     the account to which the transaction is added
     * @param transaction the transaction which should be added to the specified account
     * @throws TransactionAlreadyExistException if the transaction already exists
     * @throws AccountDoesNotExistException     if the specified account does not exist
     * @throws TransactionAttributeException    if the validation check for certain attributes fail
     */

    @Override
    public void addTransaction(String account, Transaction transaction)
            throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IOException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account '" + account + "' does not exist.");
        }
        if (transaction == null) {
            throw new TransactionAttributeException("Transaction cannot be null.");
        }
        List<Transaction> transactions = accountsToTransactions.get(account);
        if (transactions.contains(transaction)) {
            throw new TransactionAlreadyExistException("Transaction already exists in the account.");
        }

        // Overwrite interests for Payment transactions
        if (transaction instanceof Payment) {
            ((Payment) transaction).setIncomingInterest(this.incomingInterest);
            ((Payment) transaction).setOutgoingInterest(this.outgoingInterest);
        }

        transactions.add(transaction);
        writeAccount(account);
    }

    @Override
    public void removeTransaction(String account, Transaction transaction)
            throws AccountDoesNotExistException, TransactionDoesNotExistException, IOException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account '" + account + "' does not exist.");
        }
        List<Transaction> transactions = accountsToTransactions.get(account);
        if (!transactions.contains(transaction)) {
            throw new TransactionDoesNotExistException("Transaction does not exist in the account.");
        }
        transactions.remove(transaction);
        writeAccount(account);
    }



    @Override
    public boolean containsTransaction(String account, Transaction transaction) {
        if (!accountsToTransactions.containsKey(account)) {
            return false;
        }
        return accountsToTransactions.get(account).contains(transaction);
    }

    @Override
    public double getAccountBalance(String account) throws AccountDoesNotExistException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account '" + account + "' does not exist.");
        }

        double balance = 0.0;
        for (Transaction transaction : accountsToTransactions.get(account)) {
            balance += transaction.calculate();
        }
        return balance;
    }

    @Override
    public List<Transaction> getTransactions(String account) throws AccountDoesNotExistException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account '" + account + "' does not exist.");
        }
        return new ArrayList<>(accountsToTransactions.get(account));
    }

    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc) throws AccountDoesNotExistException {
        List<Transaction> transactions = getTransactions(account);
        transactions.sort((t1, t2) -> {
            double amount1 = (t1 instanceof CalculateBill)
                    ? ((CalculateBill) t1).calculate()
                    : t1.getAmount();
            double amount2 = (t2 instanceof CalculateBill)
                    ? ((CalculateBill) t2).calculate()
                    : t2.getAmount();
            return asc ? Double.compare(amount1, amount2) : Double.compare(amount2, amount1);
        });
        return transactions;
    }


    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive) throws AccountDoesNotExistException {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : getTransactions(account)) {
            double amount = (transaction instanceof CalculateBill)
                    ? ((CalculateBill) transaction).calculate()
                    : transaction.getAmount();
            if ((positive && amount >= 0) || (!positive && amount < 0)) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }
    /**
    * Read all JSON files from directoryName
    * Deserialize and populate accountsToTransactions
    */
    private void readAccounts() throws IOException {
        File directory = new File(directoryName);
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(Transaction.class, new TransactionSerializer())
            .setPrettyPrinting()
            .create();

        accountsToTransactions.clear();
        File[] files = directory.listFiles((dir, name) -> name.startsWith("Konto") && name.endsWith(".json"));

        if (files != null) {
            for (File file : files) {
                try (Reader reader = new FileReader(file)) {
                    // Extract account name from filename (remove "Konto" prefix and ".json" suffix)
                    String accountName = file.getName().substring(5, file.getName().length() - 5);

                    // Read transactions from file
                    Type listType = new TypeToken<List<Transaction>>(){}.getType();
                    List<Transaction> transactions = gson.fromJson(reader, listType);

                    // Add to map
                    accountsToTransactions.put(accountName, transactions != null ? transactions : new ArrayList<>());
                }
            }
        }
    }


    /**
     * Serialize account data to JSON
     * Save to file named Konto "Konto" + account + ".json"
     */
    private void writeAccount(String account) throws IOException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account does not exist");
        }

        Gson gson = new GsonBuilder()
            .registerTypeAdapter(Transaction.class, new TransactionSerializer())
            .setPrettyPrinting()
            .create();

        File file = new File(directoryName + "/Konto" + account + ".json");
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(accountsToTransactions.get(account), writer);
        }
    }

    //TODO: equals und toString
    @Override
    public String toString() {
        return "PrivateBank{" +
                "name='" + name + '\'' +
                ", incomingInterest=" + incomingInterest +
                ", outgoingInterest=" + outgoingInterest +
                ", accountsToTransactions=" + accountsToTransactions +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrivateBank)) return false;
        PrivateBank that = (PrivateBank) o;
        return Double.compare(that.incomingInterest, incomingInterest) == 0 &&
                Double.compare(that.outgoingInterest, outgoingInterest) == 0 &&
                name.equals(that.name) &&
                accountsToTransactions.equals(that.accountsToTransactions);
    }
}
