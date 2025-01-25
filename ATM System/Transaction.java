public class Transaction {
    private final String userId;  // Renamed 'user' to 'userId' for clarity
    private String transactionType;  // Renamed 'type' to 'transactionType'
    private double transactionAmount;  // Renamed 'amount' to 'transactionAmount'

    public Transaction(String transactionType, double transactionAmount, String userId) { // Constructor
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.userId = userId;
    }

    @Override
    public String toString() { // Override method to provide custom string representation
        return "Transaction of user=" + userId + " type='" + transactionType + "' Amount=" + transactionAmount;
    }
}