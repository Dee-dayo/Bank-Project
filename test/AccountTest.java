import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {
    private Account account;

    @BeforeEach
    public void initialiseAccount(){
        account = new Account("Dayo Akinyemi", 2090171769,"1234");
    }

    @Test
    public void accountCanDepositMoney() {
        account.deposit(5_000);
        assertEquals(5_000, account.checkBalance("1234"));
    }

    @Test
    public void accountCantCheckBalanceIfPinIsWrong() {
        account.deposit(5_000);
        assertThrows(InvalidPinException.class, ()->account.checkBalance("123432"));
    }

    @Test
    public void accountCantDepositNegativeAmount() {
        assertThrows(InvalidAmountException.class,()->account.deposit(-5_000));
    }
    @Test
    public void accountCanWithdrawMoney() {
        account.deposit(5_000);
        account.withdraw(2_000, "1234");
        assertEquals(3_000, account.checkBalance("1234"));
    }

    @Test
    public void accountCantWithdrawMoreThanBalance() {
        account.deposit(5_000);
        assertThrows(InsufficientFundsException.class, ()->account.withdraw(10_000, "1234"));
    }

    @Test
    public void accountCantWithdrawIfPinIncorrect() {
        account.deposit(5_000);
        assertThrows(InvalidPinException.class, ()->account.withdraw(2_000, "1234564"));
    }
}
