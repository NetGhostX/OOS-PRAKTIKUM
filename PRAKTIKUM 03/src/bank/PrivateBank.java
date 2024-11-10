package bank;

import bank.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PrivateBank implements Bank {
    private String name;
    private double incomingInterest;
    private double outgoingInterest;
    private HashMap<String, List<Transaction>> accountsToTransactions = new HashMap<>();

    PrivateBank(String name, double incomingInterest, double outgoingInterest) {
        setName(name);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    //TODO: creating getter and setter for class attributes

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getIncomingInterest() {
        return incomingInterest;
    }

    public void setIncomingInterest(double incomingInterest) {
        if (incomingInterest >= 0 && incomingInterest <= 1) {
            this.incomingInterest = incomingInterest;
        }
        throw new IllegalArgumentException("Incoming interest must between 0 and 1 ");
    }

    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    public void setOutgoingInterest(double outgoingInterest) {

        if (outgoingInterest >= 0 && outgoingInterest <= 1) {
            this.outgoingInterest = outgoingInterest;
        }
        throw new IllegalArgumentException("Outgoing interest must be between 0 and 1");
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

    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Account: " + account + " already exists");
        } else {
            accountsToTransactions.put(account, new ArrayList<>());
        }
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
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("Account: " + account + " already exists");
        }

        if (transactions == null) {
            accountsToTransactions.put(account, null);
            return;
        }

        List<Transaction> newTransaction = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction == null) {
                throw new TransactionAttributeException("transaction cannot be null");
            }
            if (newTransaction.contains(transaction)) {
                throw new TransactionAttributeException("Transaction already exists in account");
            }
            newTransaction.add(transaction);
        }
        accountsToTransactions.put(account, newTransaction);
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
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException {
        if(!accountsToTransactions.containsKey(account))
        {
            throw new AccountDoesNotExistException("Account does not exist");
        }
        if(transaction == null){
            throw new TransactionAttributeException("Transaction cannot be null");
        }
        List<Transaction> transactions = accountsToTransactions.get(account);
        if(transactions.contains(transaction)){
            throw new TransactionAlreadyExistException("Transaction already exists in account");
        }
        else{
            transactions.add(transaction);
        }
    }

    @Override
    public void removeTransaction(String account, Transaction transaction) throws AccountDoesNotExistException, TransactionDoesNotExistException {
        if(!accountsToTransactions.containsKey(account))
        {
            throw new AccountDoesNotExistException("Account does not exist");
        }
        List<Transaction>  transactions = accountsToTransactions.get(account);
        if(!transactions.contains(transaction)){
            throw new TransactionDoesNotExistException("Transactions does not exist");
        }
        else{
            transactions.remove(transaction);
        }
    }

    @Override
    public boolean containsTransaction(String account, Transaction transaction) {
        if(!accountsToTransactions.containsKey(account))
        {
            throw new AccountDoesNotExistException("Account does not exist");
        }
        List<Transaction>  transactions = accountsToTransactions.get(account);
        return transactions.contains(transaction);
    }

    @Override
    public double getAccountBalance(String account) {
        return 0;
    }

    @Override
    public List<Transaction> getTransactions(String account) {
        return List.of();
    }

    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc) {
        return List.of();
    }

    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive) {
        return List.of();
    }
}
