package fr.miage.bank.account.resource;

import fr.miage.bank.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountResource extends JpaRepository<Account, String> {
    Account findByEmail(String email);
    Account findByCardNumber(String cardNumber);
}
