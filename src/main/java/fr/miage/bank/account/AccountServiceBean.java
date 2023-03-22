package fr.miage.bank.account;

import fr.miage.bank.account.entity.Account;
import fr.miage.bank.account.enumeration.ExceptionMessagesEnum;
import fr.miage.bank.account.exception.AccountAlreadyExistException;
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
    public Account get(String cardNumber) throws AccountNotFoundException {
        Account a = ar.findByCardNumber(cardNumber);
        if (a == null) {
            throw new AccountNotFoundException(ExceptionMessagesEnum.ACCOUNT_NOT_FOUND.getMessage());
        }
        return a;
    }

    @Override
    public Account create(Account a) throws AccountAlreadyExistException {

        if (ar.findByEmail(a.getEmail()) != null) {
            throw new AccountAlreadyExistException(ExceptionMessagesEnum.ACCOUNT_ALREADY_EXIST.getMessage());
        }

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
            throw new AccountNotFoundException(ExceptionMessagesEnum.ACCOUNT_NOT_FOUND.getMessage());
        }

        a.setBalance(a.getBalance() - amount);
        ar.save(a);
        return a.getBalance();
    }

    @Override
    public float deposit(String cardNumber, float amount) throws AccountNotFoundException {
        Account a = ar.findByCardNumber(cardNumber);
        if (a == null) {
            throw new AccountNotFoundException(ExceptionMessagesEnum.ACCOUNT_NOT_FOUND.getMessage());
        }

        a.setBalance(a.getBalance() + amount);
        ar.save(a);
        return a.getBalance();
    }

    private String generateAccountNumber() {
        return "FR" + (random.nextInt() * 1000000000);
    }

    private String generateCardNumber() {
        // Generate Card number like 1234 5678 9012 3456
        return (random.nextInt(9000) + 1000) + "-" + (random.nextInt(9000) + 1000) + "-" + (random.nextInt(9000) + 1000) + "-" + (random.nextInt(9000) + 1000);
    }

    private String generateCardType() {
        return CARD_TYPE;
    }

    private String generateCardExpirationDate() {
        return DEFAULT_EXPIRATION_DATE;
    }

    private String generateCardCvv() {
        // Generate a random 3 digits number between 100 and 999
        return (random.nextInt(900) + 100) + "";
    }
}
