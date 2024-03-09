import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final String name;
    private final List<Account> accounts = new ArrayList<>();
    private int accNumber = 1002;

    public Bank(String name){
        this.name = name;
    }


    public Account registerCustomer(String firstName, String surName, String pin) {
        String fullName = getFullName(firstName, surName);
        Account account = new Account(fullName, accNumber, pin);
        accounts.add(account);
        increaseAccNumber();
        return account;
    }

    private String getFullName(String firstName, String surName) {
        return firstName + " " + surName;
    }

    private void increaseAccNumber() {
        accNumber += accounts.size();
    }

    public Account findAccount(int accNo) {
        for (Account account : accounts) {
            if (validate(accNo, account)) return account;
        }
        return null;
    }

    private boolean validate(int accNo, Account account) {
        return account.getAccountNumber() == accNo;
    }

    public void deposit(int accNo, int amount) {
        Account account = findAccount(accNo);
        validate(account);
        account.deposit(amount);
    }

    private void validate(Account account) {
        if (account == null) throw new NoAccountFound("Account not correct");
    }

    public int checkBalance(int accNo, String pin) {
        for (Account account : accounts) {
            if (validate(accNo, account) && validate(pin, account)) return account.getBalance();
        }
        return 0;
    }

    private boolean validate(String pin, Account account) {
        return account.verifyPin(pin);
    }


    public void withdraw(int accNo, int amount, String pin) {
        for (Account account : accounts) {
            validate(accNo, account);
            validate(pin, account);
            account.withdraw(amount, pin);
        }
    }

    public void transfer(int senderAccNo, int receiverAccNo, int amount, String pin) {
        Account sender = findAccount(senderAccNo);
        Account receiver = findAccount(receiverAccNo);
        if (validateAccount(sender) && validateAccount(receiver) && validate(pin, sender)) {
            sender.withdraw(amount, pin);
            receiver.deposit(amount);
        }
    }

    private static boolean validateAccount(Account sender) {
        return sender != null;
    }

    public int checkNoOfCustomers() {
        return accounts.size();
    }

    public void removeAccount(int accNo, String pin) {
        for (int index = 0; index < accounts.size(); index++){
            Account account = accounts.get(index);
            if (validate(accNo, account) && validate(pin, account)) accounts.remove(account);
        }
    }

    public String getName(){
        return name;
    }

    public String getCustomerName(){
        for (Account account : accounts) {
            return account.getName();
        }
        return null;
    }

    public int getCustomerAccNo() {
        for (Account account : accounts) {
            return account.getAccountNumber();
        }
        return 0;
    }
}
