import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

class Account {
    private int id;
    private String holderName;
    private long phoneNumber;
    private char accountType;
    private double balance;

    public Account(int id, String holderName, long phoneNumber, char accountType, double balance) {
        this.id = id;
        this.holderName = holderName;
        this.phoneNumber = phoneNumber;
        this.accountType = accountType;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public char getAccountType() {
        return accountType;
    }

    public void setAccountType(char accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String toString() {
        return "ID: " + id + ", Holder Name: " + holderName + ", Phone number: " + phoneNumber + ", Account Type: "
                + accountType + ", Balance: " + balance;
    }
}

class Bank {
    //List Of Accounts In The Bank
    private final List<Account> accounts;
    Scanner scanner;

    public Bank() {
        accounts = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    //Display The Menu Of Operations
    public void displayMainMenu(){
        while (true) {
            System.out.println("\nSelect an operation:");
            System.out.println("1. Add New Account");
            System.out.println("2. View All Accounts");
            System.out.println("3. Add Amount");
            System.out.println("4. Withdrew Amount");
            System.out.println("5. View Account Details");
            System.out.println("6. Modify Account");
            System.out.println("7. Close The Account");
            System.out.println("8. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addNewAccount();
                    break;

                case 2:
                    System.out.println("All Accounts In The Bank:");
                    if (!accounts.isEmpty()) {
                        displayAccounts();
                    } else {
                        System.out.println("No Accounts In The Bank!");
                    }
                    break;

                case 3:
                    System.out.println("Enter The ID Of The Account Which You Want Add To Its Balance:");
                    int accountId = scanner.nextInt();
                    System.out.println("Enter The Amount:");
                    double AmountValue = scanner.nextDouble();
                    Account account = getAccountById(accountId);
                    if(account != null) {
                        increaseBalance(account, AmountValue);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;

                case 4:
                    System.out.println("Enter The ID Of The Account Which You Want Withdrew:");
                    accountId = scanner.nextInt();
                    System.out.println("Enter The Amount:");
                    AmountValue = scanner.nextDouble();
                    account = getAccountById(accountId);
                    if(account != null) {
                        withdraw(account, AmountValue);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;

                case 5:
                    System.out.println("Enter Account ID:");
                    accountId = scanner.nextInt();
                    account = getAccountById(accountId);
                    if (account != null) {
                        accountDetails(account);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;

                case 6:
                    System.out.println("Enter Account ID:");
                    accountId = scanner.nextInt();
                    account = getAccountById(accountId);
                    if (account != null) {
                        modifyAccountDetails(account);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;

                case 7:
                    System.out.println("Enter The ID Of The Account:");
                    int id = scanner.nextInt();
                    closeAccount(id);
                    break;

                case 8:
                    updateDataFile();
                    System.out.println("Thank you for using the Bank System. Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    //Add New Account Methods
    public void addAccount(Account account) {
        while (!isIdUnique(account.getId())){
            System.out.println("Failed to add account. Account ID already exists. Enter Another ID From 4 Digits:");
            int id = scanner.nextInt();
            scanner.nextLine();
            account.setId(id);
        }
        accounts.add(account);
        System.out.println("Account added successfully.");
    }
    private void addNewAccount() {
        System.out.println("Enter Account ID From 4 Digits:");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Holder Name:");
        String holderName = scanner.nextLine();

        System.out.println("Enter Phone Number:");
        long phoneNumber = scanner.nextLong();

        System.out.println("Enter Account Type (s:saving or o:other):");
        char accountType = scanner.next().charAt(0);

        System.out.println("Enter Balance:");
        double balance = scanner.nextDouble();

        Account newAccount = new Account(id, holderName, phoneNumber, accountType, balance);
        addAccount(newAccount);
    }


    //List All Accounts In The Bank
    public void displayAccounts() {
        for (Account account : accounts) {
            System.out.println(account.toString());
        }
    }


    //Increase The Balance Method
    public void increaseBalance(Account account, double amount) {
        double currentBalance = account.getBalance();
        double newBalance = currentBalance + amount;
        account.setBalance(newBalance);
        System.out.println("Balance increased by " + amount + ". New balance: " + newBalance);
    }


    //Withdrew Method
    public void withdraw(Account account, double amount) {
        char accountType = account.getAccountType();
        double currentBalance = account.getBalance();

        if (accountType == 's' && amount > 500) {
            System.out.println("Withdrawal failed. Savings account holders cannot withdraw more than 500.");
        } else if (amount > currentBalance) {
            System.out.println("Withdrawal failed. Insufficient balance.");
        } else {
            double newBalance = currentBalance - amount;
            account.setBalance(newBalance);
            System.out.println("Withdrawal successful. Amount withdrawn: " + amount + ". New balance: " + newBalance);
        }
    }


    //View Account Details Method
    public void accountDetails(Account account){
        System.out.println("\nSelect an operation:");
        System.out.println("1. ID.");
        System.out.println("2. Account Holder Name.");
        System.out.println("3. Part Of The Name.");

        String mystring = account.getHolderName();
        String[] arr = mystring.split(" ", 2);

        int num = scanner.nextInt();
        switch (num){
            case 1:
                System.out.println("The ID Is : "+account.getId());
                break;
            case 2:
                System.out.println("The Holder Name Is: "+account.getHolderName());
                break;
            case 3:
                System.out.println("The Part Of The Name Is: "+arr[0]);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }


    //Modify Account Details Method
    public void modifyAccountDetails(Account account) {
        System.out.println("\nSelect an operation:");
        System.out.println("1. Modify ID");
        System.out.println("2. Modify Account Holder Name");
        System.out.println("3. Modify Part Of The Name");

        String holderName = account.getHolderName();
        String[] nameParts = holderName.split(" ", 2);

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Enter New Holder ID:");
                int id = scanner.nextInt();
                scanner.nextLine();

                while (!isIdUnique(id)) {
                    System.out.println("Account ID already exists. Enter another ID with 4 digits:");
                    id = scanner.nextInt();
                    scanner.nextLine();
                }

                account.setId(id);
                System.out.println("Account ID modified successfully.");
                break;
            case 2:
                System.out.println("Enter New Holder Name:");
                String newHolderName = scanner.nextLine();
                account.setHolderName(newHolderName);
                System.out.println("Account holder name modified successfully.");
                break;
            case 3:
                System.out.println("Enter New Part Name:");
                String newPartName = scanner.nextLine();
                account.setHolderName(newPartName + " " + nameParts[1]);
                System.out.println("Account name part modified successfully.");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }


    //Close The Account Method
    public void closeAccount(int accountId) {
        Iterator<Account> iterator = accounts.iterator();
        boolean accountFound = false;

        while (iterator.hasNext()) {
            Account account = iterator.next();
            if (account.getId() == accountId) {
                iterator.remove();
                accountFound = true;
                break;
            }
        }

        if (accountFound) {
            System.out.println("Account closed successfully.");
        } else {
            System.out.println("Failed to close account. Account not found.");
        }
    }


    //Search An Account By It's ID
    public Account getAccountById(int id) {
        for (Account account : accounts) {
            if (account.getId() == id) {
                return account;
            }
        }
        return null;
    }



    //Upload All Accounts Information From The File In ArrayList Method
    public void uploadDataFile(String filename) {
        accounts.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] accountDetails = line.split(",");
                if (accountDetails.length != 5) {
                    System.out.println("Invalid account details: " + line);
                    continue;
                }
                int id = Integer.parseInt(accountDetails[0].trim());
                String holderName = accountDetails[1].trim();
                long phoneNumber = Long.parseLong(accountDetails[2].trim());
                char accountType = accountDetails[3].trim().charAt(0);
                double balance = Double.parseDouble(accountDetails[4].trim());

                Account account = new Account(id, holderName, phoneNumber, accountType, balance);
                accounts.add(account);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }


    //Update The Data In The File After The Operations Method
    public void updateDataFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Accounts.txt"))) {
            for (Account account : accounts) {
                writer.write(account.getId() + "," + account.getHolderName() + "," + account.getPhoneNumber() + "," +
                        account.getAccountType() + "," + account.getBalance());
                writer.newLine();
            }
            System.out.println("Accounts saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving accounts to file: " + e.getMessage());
        }
    }



    private boolean isIdUnique(int id) {
        for (Account account : accounts) {
            if (account.getId() == id) {
                return false;
            }
        }
        return true;
    }

}

class Driver {
  public static void main(String[] args) {
    Bank bank = new Bank();
    bank.uploadDataFile("Accounts.txt");
    System.out.println("Welcome to the Bank System!");
    bank.displayMainMenu();
  }
}
