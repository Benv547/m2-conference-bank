package fr.miage.bank.account;

import fr.miage.bank.account.entity.Account;

public interface AccountService {
    public Account get(String email, String firstname, String lastname);
}
