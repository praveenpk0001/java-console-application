import Note.Notes;
import Notes.*;
import java.util.ArrayList;
import java.util.Scanner;

class UserActions {

    public static Account userLogin(ArrayList<Account> accountList, User currentUser, Scanner scanner) { // Method for user login
        System.out.print("Enter User ID: ");
        String userId = scanner.next();
        int loginAttempts = 1;

        for (Account account : accountList) { // Iterate over accounts list
            if (account instanceof User) { // Check if account is an instance of User
                if (account.getAccountId().equals(userId)) { // Check if User ID matches
                    while (loginAttempts <= 3) { // Allow only 3 login attempts
                        System.out.print("Enter your PIN: ");
                        String userPin = scanner.next();

                        if (account.getAccountPin().equals(userPin)) { // Check if PIN matches
                            System.out.println("User login successful.");
                            return account; // Return the logged-in user
                        } else {
                            loginAttempts++; // Increment login attempts
                            int remainingAttempts = 3 - loginAttempts; // Calculate remaining attempts
                            if (remainingAttempts == 0) {
                                System.out.println("User account locked....");
                                return null;
                            } else {
                                System.out.println("Incorrect PIN... You have " + remainingAttempts + " attempts left.");
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Invalid User ID...");
        return null;
    }

    public void changePin(Account account, Scanner scanner, User user) { // Method for changing PIN
        System.out.println("Enter your current PIN:");
        String oldPin = scanner.next();

        if (oldPin.equals(account.getAccountPin())) { // Check if current PIN is correct
            System.out.println("Enter your new PIN:");
            String newPin = scanner.next();
            account.setAccountPin(newPin); // Set new PIN
            System.out.println("PIN was successfully changed.");
        } else {
            System.out.println("Incorrect PIN...");
        }
    }

    public void withdraw(User user, Scanner scanner, Account account) throws CloneNotSupportedException { // Withdraw method
        System.out.println("Enter the withdrawal amount:");
        double withdrawalAmount = scanner.nextDouble(); // Get withdrawal amount
        double remainingAmount = withdrawalAmount; // Store remaining amount to be withdrawn
        ArrayList<String> transactionNotes = new ArrayList<>(); // Array list for transaction notes
        ArrayList<Notes> clonedNotes = new ArrayList<>(); // Array list for cloned notes

        for (Notes note : ATM.getNoteInventory()) { // Clone notes from ATM's available notes
            clonedNotes.add(note.clone());
        }

        // Sort notes in descending order of denomination value
        for (int i = 0; i < clonedNotes.size() - 1; i++) {
            for (int j = i + 1; j < clonedNotes.size(); j++) {
                if (clonedNotes.get(i).getNoteValue() < clonedNotes.get(j).getNoteValue()) {
                    // Swap notes in descending order
                    Notes temp = clonedNotes.get(i);
                    clonedNotes.set(i, clonedNotes.get(j));
                    clonedNotes.set(j, temp);
                }
            }
        }

        while (remainingAmount != 0) {
            boolean denominationAvailable = false;  // Flag to check if denomination is available
            for (Notes note : clonedNotes) {
                int noteValue = note.getNoteValue(); // Get note value
                int availableNotes = note.getNoteCount(); // Get available note count
                int requiredNotes = (int) (remainingAmount / noteValue); // Calculate required notes

                if (requiredNotes > 0 && noteValue <= remainingAmount && availableNotes > 0) {
                    int notesToWithdraw = Math.min(requiredNotes, availableNotes);  // Withdraw the lesser of required or available notes
                    System.out.println("Withdrawing " + notesToWithdraw + " notes of " + noteValue);
                    remainingAmount -= notesToWithdraw * noteValue;  // Deduct from remaining amount
                    note.setNoteCount(availableNotes - notesToWithdraw); // Update the note count in ATM

                    transactionNotes.add("You got " + notesToWithdraw + " notes of " + noteValue); // Add transaction note
                    denominationAvailable = true;  // Set flag to true as we have withdrawn some notes
                }

                if (remainingAmount == 0) { // If withdrawal is completed
                    Transaction transaction = new Transaction("Withdraw", withdrawalAmount, account.getAccountId());
                    account.addTransaction(transaction);

                    user.setBalance(user.getBalance() - withdrawalAmount); // Deduct withdrawal amount from user's balance
                    break;
                }
            }

            // If no suitable denomination found or amount is still not fully withdrawn
            if (!denominationAvailable || remainingAmount != 0) {
                System.out.println("Insufficient denominations or unable to withdraw the exact amount. Please try again.");
                return;  // Exit withdrawal process
            }
        }
    }

    public void deposit(Account account, User user, ArrayList<Notes> cashInventory, Scanner scanner) { // Deposit method
        for (Notes note : cashInventory) {
            System.out.println(note.getNoteValue() + " " + note.getNoteCount()); // Display available notes
        }

        System.out.print("Enter your deposit amount: ");
        double depositAmount = scanner.nextDouble();

        System.out.print("Enter number of 2000 denomination notes: ");
        int num2000 = scanner.nextInt();
        System.out.print("Enter number of 500 denomination notes: ");
        int num500 = scanner.nextInt();
        System.out.print("Enter number of 200 denomination notes: ");
        int num200 = scanner.nextInt();
        System.out.print("Enter number of 100 denomination notes: ");
        int num100 = scanner.nextInt();

        double totalDeposit = (num100 * 100) + (num200 * 200) + (num500 * 500) + (num2000 * 2000);

        if (depositAmount == totalDeposit) {
            addToCashInventory(cashInventory, new Note100(num100));
            addToCashInventory(cashInventory, new Note200(num200));
            addToCashInventory(cashInventory, new Note500(num500));
            addToCashInventory(cashInventory, new Note2000(num2000));

            user.setBalance(user.getBalance() + totalDeposit); // Add deposit to user's balance
            ATM.setATMFunds(ATM.getATMFunds() + totalDeposit); // Update ATM balance

            // Add transaction for user
            Transaction transaction = new Transaction("Deposit", depositAmount, account.getAccountId());
            account.addTransaction(transaction);

            System.out.println("Deposit successful.");

            for (Notes note : cashInventory) {
                System.out.println(note.getNoteValue() + " " + note.getNoteCount()); // Display updated note count
            }
        } else {
            System.out.println("Deposit amount does not match the denomination counts.");
        }
    }

    private void addToCashInventory(ArrayList<Notes> cashInventory, Notes newNote) { // Add notes to cash inventory
        boolean noteFound = false;

        for (Notes note : cashInventory) { // Iterate through cash inventory
            if (note.getNoteValue() == newNote.getNoteValue()) { // Check if note value matches
                note.setNoteCount(note.getNoteCount() + newNote.getNoteCount()); // Add new notes to existing ones
                noteFound = true;
                break;
            }
        }

        if (!noteFound) { // If note is not found, add it to inventory
            cashInventory.add(newNote);
        }
    }

    public void viewTransactions(Account account) { // Method to view user transactions
        if (account.getTransactionList().isEmpty()) {
            System.out.println("No transactions in this account.");
        } else {
            System.out.println("User Transactions:");
            for (Transaction transaction : account.getTransactionList()) {
                System.out.println(transaction);
            }
        }
    }
}