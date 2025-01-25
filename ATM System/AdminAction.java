import Note.Notes;
import Notes.Note100;
import Notes.Note200;
import Notes.Note2000;
import Notes.Note500;

import java.util.ArrayList;
import java.util.Scanner;

class AdminActions extends ATM {

    public static Account adminLogin(ArrayList<Account> accountsList, Scanner scanner) { // Admin login method
        System.out.print("Enter Admin ID: ");
        String adminId = scanner.next();
        int attempts = 1;
        for (Account account : accountsList) {
            if (account instanceof Admin) { // Check if the account is an Admin
                if (account.getAccountId().equals(adminId)) { // Validate Admin I
                    while (attempts <= 3) { // Allow for 3 attempts
                        System.out.print("Enter Admin PIN: ");
                        String adminPin = scanner.next();
                        if (account.getAccountPin().equals(adminPin)) { // Validate Admin PIN
                            System.out.println("Admin login successful.");
                            return account; // Successful login
                        } else {
                            attempts++; // Increment attempts
                            int remainingAttempts = 3 - attempts; // Calculate remaining attempts
                            if (remainingAttempts == 0) {
                                System.out.println("Account locked due to too many failed attempts.");
                                return null;
                            } else {
                                System.out.println("Incorrect PIN. You have " + remainingAttempts + " attempts left...");
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Admin ID not found.");
        return null;
    }

    public void addUser(ArrayList<Account> allAccountsList, Scanner scanner) { // Add a new user
        System.out.print("Enter new User ID: ");
        String userId = scanner.next();
        for (Account account : allAccountsList) { // Check if User ID already exists
            if (account.getAccountId().equals(userId)) {
                System.out.println("User ID already exists. Try a different ID.");
                return;
            }
        }
        System.out.print("Enter new User PIN: ");
        String userPin = scanner.next();
        User newUser = new User(userId, userPin); // Create new User
        allAccountsList.add(newUser); // Add to accounts list
        System.out.println("User added successfully.");
    }

    public void deleteUser(ArrayList<Account> accountsList, Scanner scanner) { // Delete a user
        System.out.print("Enter User ID to delete: ");
        String userId = scanner.next();

        boolean userFound = false;
        for (Account account : accountsList) { // Check if User ID exists
            if (account.getAccountId().equals(userId)) {
                accountsList.remove(account); // Remove from accounts list
                System.out.println("User " + userId + " deleted successfully.");
                userFound = true;
                break;
            }
        }

        if (!userFound) { // User not found
            System.out.println("User with ID " + userId + " not found.");
        }
    }

    public void viewUsers(ArrayList<Account> accountsList) { // View list of users
        System.out.println("List of Users:");
        for (Account account : accountsList) { // Iterate through accounts
            if (account instanceof User) { // Check if it's a User account
                System.out.println("User ID: " + account.getAccountId() + "\t\tBalance: " + ((User) account).getBalance());
            }
        }
    }

    public void depositCashToATM(ArrayList<Notes> noteInventory, Account loggedInAdmin, Scanner scanner) {
        System.out.println("Current Cash Inventory in ATM:");
        for (Notes note : noteInventory) { // View current note inventory
            System.out.println(note.getNoteValue() + " - " + note.getNoteCount() + " notes");
        }

        System.out.println("Enter the amount of money to add to the ATM:");
        double totalAmount = scanner.nextDouble();

        System.out.print("Enter number of 2000 denomination notes: ");
        int num2000 = scanner.nextInt();
        System.out.print("Enter number of 500 denomination notes: ");
        int num500 = scanner.nextInt();
        System.out.print("Enter number of 200 denomination notes: ");
        int num200 = scanner.nextInt();
        System.out.print("Enter number of 100 denomination notes: ");
        int num100 = scanner.nextInt();

        double totalDeposit = (num100 * 100) + (num200 * 200) + (num500 * 500) + (num2000 * 2000);

        if (totalDeposit == totalAmount) { // Validate total amount
            System.out.println("Total amount to be added to ATM: " + totalDeposit);

            addToCashInventory(noteInventory, new Note100(num100)); // Add new notes to ATM inventory
            addToCashInventory(noteInventory, new Note200(num200));
            addToCashInventory(noteInventory, new Note500(num500));
            addToCashInventory(noteInventory, new Note2000(num2000));

            ATM.setATMFunds(ATM.getATMFunds() + totalDeposit); // Update ATM funds
            System.out.println("Money added successfully to ATM.");
            loggedInAdmin.addTransaction(new Transaction("Deposit", totalDeposit, loggedInAdmin.getAccountId())); // Log admin transaction
        } else {
            System.out.println("Deposit amount does not match the denomination counts.");
        }
    }

    private void addToCashInventory(ArrayList<Notes> noteInventory, Notes newNote) { // Add notes to cash inventory
        boolean found = false;
        for (Notes note : noteInventory) { // Check if note exists in inventory
            if (note.getNoteValue() == newNote.getNoteValue()) {
                note.setNoteCount(note.getNoteCount() + newNote.getNoteCount()); // Add new note count
                found = true;
                break;
            }
        }
        if (!found) { // If note doesn't exist, add new note
            noteInventory.add(newNote);
        }
    }

    public static double getATMFunds(ArrayList<Notes> noteInventory) { // Method to get ATM funds
        double totalFunds = 0;
        for (Notes note : noteInventory) {
            totalFunds += note.getNoteCount() * note.getNoteValue(); // Calculate total funds in ATM
        }
        return totalFunds;
    }

    public void viewTransactions(ArrayList<Account> accountsList) {
        System.out.println("Enter option: 1. User\n2. Admin");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice == 1) { // View User transactions
            for (Account account : accountsList) {
                if (account instanceof User) {
                    System.out.println("Transaction for user " + account.getAccountId());
                    if (account.getTransactionList().isEmpty()) {
                        System.out.println("No transactions found.");
                    } else {
                        for (Transaction transaction : account.getTransactionList()) {
                            System.out.println(transaction);
                        }
                    }
                    return;
                }
            }
        }

        if (choice == 2) { // View Admin transactions
            for (Account account : accountsList) {
                if (account instanceof Admin) {
                    System.out.println("Transaction for admin " + account.getAccountId());
                    if (account.getTransactionList().isEmpty()) {
                        System.out.println("No transactions found.");
                    } else {
                        for (Transaction transaction : account.getTransactionList()) {
                            System.out.println(transaction);
                        }
                    }
                    return;
                }
            }
        }
    }
}
