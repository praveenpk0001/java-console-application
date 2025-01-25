import java.util.ArrayList;

class User extends Account {
    private static final ArrayList<Account> customersList = new ArrayList<>(); // Class name modified
    private double balance; // Store user balance amount

    public User(String userId, String userPin) { // Constructor
        super(userId, userPin);
    }

    public User() {} // Default Constructor

    // Getters and setters

    public double getBalance() { // User balance getter
        return balance;
    }

    public void setBalance(double balance) { // User balance setter
        this.balance = balance;
    }

    static ArrayList<Account> getAdminList() {
        return customersList;
    }

    static ArrayList<Account> getUserList() {
        return customersList;
    }
}
