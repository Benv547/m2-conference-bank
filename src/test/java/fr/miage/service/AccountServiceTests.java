package fr.miage.service;


import fr.miage.bank.account.AccountService;
import fr.miage.bank.account.entity.Account;
import fr.miage.bank.account.resource.AccountResource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest()
public class AccountServiceTests {

    @MockBean
    AccountResource ar;

    @Autowired
    AccountService as;

    @Test
    void getTest_twoTry_ExpectTrue() {

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
        account.setAccountNumber("123");

        // mock AccountResource
        when(ar.findByEmail(Mockito.anyString()))
                .thenReturn(account);

        // ACT
        Account a = as.get(email, firstname, lastname);
        Account a2 = as.get(email, firstname, lastname);

        // ASSERT
        assertTrue(a.getAccountNumber().equals(a2.getAccountNumber()));
    }

}
