public class Account {
    private final String name;
    private int balance;
    private final String pin;
    private final int accNumber;

    public Account(String fullName, int accNumber, String pin) {
        this.name = fullName;
        this.accNumber = accNumber;
        this.pin = pin;
    }

    public void deposit(int amount) {
        validate(amount);
        balance += amount;
    }

    private static void validate(int amount) {
        if (amount <= 0) throw new InvalidAmountException("Amount less than zero can't be deposited");
    }

    public int checkBalance(String pinNo) {
        validate(pinNo);
        return balance;
    }

    private void validate(String pinNo) {
        if (!pin.equals(pinNo)) throw new InvalidPinException("Incorrect pin");
    }

    public void withdraw(int amount, String pinNo) {
        validate(amount, pinNo);
        balance -= amount;
    }

    private void validate(int amount, String pinNo) {
        validate(amount);
        validate(pinNo);
        validateAmount(amount);
    }

    private void validateAmount(int amount) {
        if ( amount > balance) throw new InsufficientFundsException("Amount Exceeded Balance");
    }

    public int getAccountNumber() {
        return accNumber;
    }

    public boolean verifyPin(String pin) {
        return this.pin.equals(pin);
    }

    public int getBalance(){
        return balance;
    }

    public String getName(){
        return name;
    }

}
