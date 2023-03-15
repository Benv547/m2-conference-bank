package fr.miage.bank.account;

import fr.miage.bank.account.entity.Account;
import fr.miage.bank.account.exception.AccountNotFoundException;
import fr.miage.bank.account.resource.AccountResource;
import org.springframework.stereotype.Service;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.UUID;

@Singleton
@Startup
@Service
public class AccountServiceBean implements AccountService {

    @Inject
    private AccountResource ar;

    @Override
    public Account get(String email, String firstname, String lastname) {
        Account a = ar.findByEmail(email);
        if (a == null) {
            a = new Account();
            a.setId(UUID.randomUUID().toString());
            a.setEmail(email);
            a.setFirstname(firstname);
            a.setLastname(lastname);
            a.setAccountNumber(generateAccountNumber());
            a.setCardNumber(generateCardNumber());
            a.setCardType(generateCardType());
            a.setCardExpirationDate(generateCardExpirationDate());
            a.setCardCvv(generateCardCvv());
            a.setBalance(0);
            ar.save(a);
        }
        return a;
    }

    @Override
    public float withdraw(String cardNumber, float amount) throws AccountNotFoundException {
        Account a = ar.findByCardNumber(cardNumber);
        if (a == null) {
            throw new AccountNotFoundException("The account with the card number " + cardNumber + " does not exist!");
        }

        a.setBalance(a.getBalance() - amount);
        ar.save(a);
        return a.getBalance();
    }

    @Override
    public float deposit(String cardNumber, float amount) throws AccountNotFoundException {
        Account a = ar.findByCardNumber(cardNumber);
        if (a == null) {
            throw new AccountNotFoundException("The account with the card number " + cardNumber + " does not exist!");
        }

        a.setBalance(a.getBalance() + amount);
        ar.save(a);
        return a.getBalance();
    }

    private String generateAccountNumber() {
        return "FR" + (int) (Math.random() * 1000000000);
    }

    private String generateCardNumber() {
        String cardNumber = (int) (Math.random() * 9000 + 1000) + "";
        cardNumber += (int) (Math.random() * 9000 + 1000) + "";
        cardNumber += (int) (Math.random() * 9000 + 1000) + "";
        cardNumber += (int) (Math.random() * 9000 + 1000) + "";
        return cardNumber;
    }

    private String generateCardType() {
        return "Visa";
    }

    private String generateCardExpirationDate() {
        return "12/2023";
    }

    private String generateCardCvv() {
        return (int) (Math.random() * 1000) + "";
    }
}
