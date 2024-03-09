import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankTest {
    private Bank bank;
    private Account account;

    @BeforeEach
    public void setUp() {
        bank = new Bank("UBA");
        account = bank.registerCustomer("Dayo", "Timi", "1234");
    }


    @Test
    public void BankCanRegisterCustomer() {
        assertEquals(account, bank.findAccount(1002));
        assertEquals(1, bank.checkNoOfCustomers());
    }

    @Test
    public void BankCanDepositMoneyIntoAccount() {
        int accNo = account.getAccountNumber();
        bank.deposit(accNo, 5_000);
        assertEquals(5_000, bank.checkBalance(accNo, "1234"));
    }

    @Test
    public void DepositThrowsExceptionWhenDepositNegativeNumber(){
        int accNo = account.getAccountNumber();
        assertThrows(InvalidAmountException.class, ()->bank.deposit(accNo, -5_000));
    }

    @Test
    public void BankCanWithdrawMoneyFromAccount(){
        int accNo = account.getAccountNumber();
        bank.deposit(accNo, 5_000);
        bank.withdraw(accNo, 2_000, "1234");
        assertEquals(3_000, bank.checkBalance(accNo, "1234"));
    }

    @Test
    public void BankCantWithdrawIfAmountIsGreaterThanBalance(){
        int accNo = account.getAccountNumber();
        bank.deposit(accNo, 5_000);
        assertThrows(InsufficientFundsException.class, ()->bank.withdraw(accNo, 10_000, "1234"));
    }

    @Test
    public void BankCanTransferFromOneAccountToAnother(){
        int senderAccNo = account.getAccountNumber();
        bank.deposit(senderAccNo, 5_000);
        assertEquals(5_000, bank.checkBalance(senderAccNo, "1234"));

        Account account2 = bank.registerCustomer("Moh", "Baba", "2222");
        int receiverAccNo = account2.getAccountNumber();
        assertEquals(0, bank.checkBalance(receiverAccNo, "2222"));

        bank.transfer(senderAccNo, receiverAccNo, 3_000, "1234");

        assertEquals(2_000, bank.checkBalance(senderAccNo, "1234"));
        assertEquals(3_000, bank.checkBalance(receiverAccNo, "2222"));
    }

    @Test
    public void BankCanRemoveAccount() {
        Account account2 = bank.registerCustomer("Moh", "Baba", "2222");
        assertEquals(2, bank.checkNoOfCustomers());
        int accNo = account2.getAccountNumber();

        bank.removeAccount(accNo, "2222");
        assertEquals(1, bank.checkNoOfCustomers());

        int accNo2 = account.getAccountNumber();
        bank.removeAccount(accNo2, "1234");
        assertEquals(0, bank.checkNoOfCustomers());
    }
}
