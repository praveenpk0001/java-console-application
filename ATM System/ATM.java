import Note.Notes;
import java.util.ArrayList;
import java.util.Scanner;

public class ATM {
    private static ArrayList<Notes> noteInventory = new ArrayList<>(); //notes array list
    private static double atmFunds; //atm balance variable
    private static ArrayList<Account> accountsList = new ArrayList<>(); //account array list

    public static void initialize() throws CloneNotSupportedException { //start method
        Scanner scanner = new Scanner(System.in); //Scanner object
        AdminActions adminActions = new AdminActions(); // Constructor for adminActions
        UserActions userActions = new UserActions(); // Constructor for userActions

        accountsList.add(new Admin("admin123", "12345")); // Adding default admin

        while (true) { // Infinite loop
            System.out.println("Welcome \n1. Admin Login\n2. User Login\n3. Exit");
            int option = scanner.nextInt();

            if (option == 1) {
                Account loggedInAdmin = AdminActions.adminLogin(accountsList, scanner);
                if (loggedInAdmin != null) {
                    adminMenu(scanner, adminActions, loggedInAdmin);
                } else {
                    System.out.println("Invalid Admin ID....");
                }
            } else if (option == 2) {
                User user = new User();
                Account loggedInUser = UserActions.userLogin(accountsList, user, scanner);
                if (loggedInUser != null) {
                    userMenu(scanner, userActions, loggedInUser, user);
                } else {
                    System.out.println("User Login Failed....");
                }
            } else if (option == 3) {
                System.out.println("Thank you....");
                break;
            } else {
                System.out.println("Invalid choice....please Try again.");
            }
        }
    }

    private static void adminMenu(Scanner scanner, AdminActions adminActions, Account loggedInAdmin) {  // Method to show Admin options
        while (true) {  // Infinite loop
            System.out.println("Admin Actions:\n1. Add User\n2. Delete User\n3. View Users\n4. Deposit cash to ATM\n5. View Transactions\n6. ATM Funds\n7. Exit");
            int option = scanner.nextInt();  // Get Admin option
            if (option == 1) {  // Add user
                adminActions.addUser(accountsList, scanner);  // Call addUser method from AdminActions
            } else if (option == 2) {  // Delete user
                adminActions.deleteUser(accountsList, scanner);  // Call deleteUser method from AdminActions
            } else if (option == 3) {  // View users
                adminActions.viewUsers(accountsList);  // Call viewUsers method from AdminActions
            } else if (option == 4) {  // Add money to ATM
                adminActions.depositCashToATM(noteInventory, loggedInAdmin, scanner);  // Call depositCashToATM method from AdminActions
            } else if (option == 5) {  // View transactions
                adminActions.viewTransactions(accountsList);
            } else if (option == 6) {  // View ATM funds
                atmFunds = AdminActions.getATMFunds(noteInventory);  // Calculate ATM funds
                System.out.println("Current ATM Funds: " + atmFunds);  // Display ATM funds
            } else if (option == 7) {  // Exit Admin session
                System.out.println("Admin logged out");
                break;
            } else {  // Invalid option
                System.out.println("Invalid option... please Try again.");
            }
        }
    }

    private static void userMenu(Scanner scanner, UserActions userActions, Account account, User user) throws CloneNotSupportedException {  // Method to show User options
        while (true) {  // Infinite loop
            System.out.println("User Actions:\n1. Withdraw\n2. Deposit\n3. Check Balance\n4. View Transactions\n5. Change PIN\n6. Exit");
            int option = scanner.nextInt();  // Get User option
            if (option == 1) {  // Withdraw money
                userActions.withdraw(user, scanner, account);  // Call withdraw method from UserActions
            } else if (option == 2) {  // Deposit money
                userActions.deposit(account, user, noteInventory, scanner);  // Call deposit method from UserActions
            } else if (option == 3) {  // Check balance
                System.out.println("Current Balance:" + user.getBalance());  // Display user's current balance
            } else if (option == 4) {  // View transactions
                userActions.viewTransactions(account);  // Call viewTransactions method from UserActions
            } else if (option == 5) {  // Change pin
                userActions.changePin(account, scanner, user);  // Call changePin method from UserActions
            } else if (option == 6) {  // Exit User session
                System.out.println("User logged out...");
                break;  // Break the loop and log out
            } else {  // Invalid option
                System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static ArrayList<Notes> getNoteInventory() {
        return noteInventory;
    }

    public static double getATMFunds() { //getter method for atm funds
        return atmFunds;
    }

    public static void setATMFunds(double atmAmount) { //setter method for atm funds
        atmFunds = atmAmount;
    }
}