package fr.miage.bank.account;

import fr.miage.bank.account.entity.Account;
import fr.miage.bank.account.exception.AccountNotFoundException;

public interface AccountService {
    Account get(String email, String firstname, String lastname) throws AccountNotFoundException;
    Account create(Account account);
    float withdraw(String email, float amount) throws AccountNotFoundException;
    float deposit(String email, float amount) throws AccountNotFoundException;
}
