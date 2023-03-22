package fr.miage.service;


import fr.miage.bank.account.AccountService;
import fr.miage.bank.account.entity.Account;
import fr.miage.bank.account.exception.AccountNotFoundException;
import fr.miage.bank.account.resource.AccountResource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest()
public class AccountServiceTests {

    @MockBean
    AccountResource ar;

    @Autowired
    AccountService as;

    @Test
    void getTest_twoTry_ExpectTrue() throws AccountNotFoundException {

        // ARRANGE
        String email = "test@test.fr";
        String firstname = "FirstName";
        String lastname = "LastName";

        Account account = new Account();
        account.setId("1");
        account.setEmail(email);
        account.setFirstname(firstname);
        account.setLastname(lastname);
        account.setBalance(0);
        account.setCardNumber("123");
        account.setAccountNumber("123");

        // ACT
        when(ar.findByCardNumber(Mockito.anyString()))
                .thenReturn(account);
        Account a2 = as.get(account.getCardNumber());

        // ASSERT
        assertTrue(account.getAccountNumber().equals(a2.getAccountNumber()));
    }

    @Test
    void withdraw_accountNotFound_ExpectException() {

        // ARRANGE
        String cardNumber = "123";
        float amount = 100;

        // ACT
        when(ar.findByCardNumber(Mockito.anyString()))
                .thenReturn(null);

        // ASSERT
        assertThrows(AccountNotFoundException.class, () -> as.withdraw(cardNumber, amount));
    }

    @Test
    void deposit_accountNotFound_ExpectException() {

        // ARRANGE
        String cardNumber = "123";
        float amount = 100;

        // ACT
        when(ar.findByCardNumber(Mockito.anyString()))
                .thenReturn(null);

        // ASSERT
        assertThrows(AccountNotFoundException.class, () -> as.deposit(cardNumber, amount));
    }
}
