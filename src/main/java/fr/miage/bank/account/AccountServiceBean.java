package fr.miage.bank.account;

import fr.miage.bank.account.entity.Account;
import fr.miage.bank.account.exception.AccountNotFoundException;
import fr.miage.bank.account.resource.AccountResource;
import org.springframework.stereotype.Service;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

@Singleton
@Startup
@Service
public class AccountServiceBean implements AccountService {

    @Inject
    private AccountResource ar;

    private static final String CARD_TYPE = "Visa";
    private static final String DEFAULT_EXPIRATION_DATE = "01/2025";

    private static final Random random = new SecureRandom();

    @Override
    public Account get(String email, String firstname, String lastname) throws AccountNotFoundException {
        Account a = ar.findByEmail(email);
        if (a == null) {
            throw new AccountNotFoundException("The account with the email " + email + " does not exist!");
        }
        return a;
    }

    @Override
    public Account create(Account a) {
        a.setId(UUID.randomUUID().toString());
        a.setAccountNumber(generateAccountNumber());
        a.setCardNumber(generateCardNumber());
        a.setCardType(generateCardType());
        a.setCardExpirationDate(generateCardExpirationDate());
        a.setCardCvv(generateCardCvv());
        a.setBalance(0);
        ar.save(a);
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
        return "FR" + (random.nextInt() * 1000000000);
    }

    private String generateCardNumber() {
        String cardNumber = (random.nextInt() * 9000 + 1000) + "";
        cardNumber += (random.nextInt() * 9000 + 1000) + "";
        cardNumber += (random.nextInt() * 9000 + 1000) + "";
        cardNumber += (random.nextInt() * 9000 + 1000) + "";
        return cardNumber;
    }

    private String generateCardType() {
        return CARD_TYPE;
    }

    private String generateCardExpirationDate() {
        return DEFAULT_EXPIRATION_DATE;
    }

    private String generateCardCvv() {
        return (random.nextInt() * 1000) + "";
    }
}
