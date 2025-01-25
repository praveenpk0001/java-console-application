import java.util.ArrayList;

public class Account {
    private String accountId;  // Account identifier
    private String accountPin;  // Renamed 'pin' to 'accountPin' for clarity
    private ArrayList<Transaction> transactionList = new ArrayList<>();  // Renamed 'transactions' to 'transactionList'

    public Account(String accountId, String accountPin) { // Constructor to store accountId and accountPin
        this.accountId = accountId;
        this.accountPin = accountPin;
    }

    public Account() { // Default constructor to initialize transaction list
        this.transactionList = new ArrayList<>();
    }

    // Getters and Setters
    public String getAccountId() {  // Renamed 'getId' to 'getAccountId' for clarity
        return accountId;
    }

    public String getAccountPin() {  // Renamed 'getPin' to 'getAccountPin'
        return accountPin;
    }

    public void setAccountPin(String accountPin) {  // Renamed 'setPin' to 'setAccountPin'
        this.accountPin = accountPin;
    }

    public ArrayList<Transaction> getTransactionList() {  // Renamed 'getTransactions' to 'getTransactionList'
        return transactionList;
    }

    public void addTransaction(Transaction transaction) {  // Method to add a new transaction to the list
        this.transactionList.add(transaction);
    }

    public void addTransactionToAccount(Transaction transaction) {  // Renamed 'setTransactions' to 'addTransactionToAccount'
        this.transactionList.add(transaction);
    }
}