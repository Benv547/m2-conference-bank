package fr.miage.bank.api.resource;

import fr.miage.bank.account.AccountService;
import fr.miage.bank.account.entity.Account;
import fr.miage.bank.account.entity.AccountInput;
import fr.miage.bank.account.exception.AccountNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ejb.EJB;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    @EJB
    private AccountService service;

    @GetMapping()
    public ResponseEntity<Account> get(@RequestBody @Valid AccountInput account) {
        try {
            return ResponseEntity.ok(service.get(account.getEmail(), account.getFirstname(), account.getLastname()));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public Account create(@RequestBody @Valid AccountInput account) {
        Account entity = new Account();
        entity.setEmail(account.getEmail());
        entity.setFirstname(account.getFirstname());
        entity.setLastname(account.getLastname());
        return service.create(entity);
    }

    @PostMapping(value = "/withdraw/{cardNumber}/{amount}")
    @Transactional
    public ResponseEntity withdraw(@PathVariable("cardNumber") String cardNumber, @PathVariable("amount") float amount) throws AccountNotFoundException {
        service.withdraw(cardNumber, amount);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/deposit/{cardNumber}/{amount}")
    @Transactional
    public ResponseEntity deposit(@PathVariable("cardNumber") String cardNumber, @PathVariable("amount") float amount) throws AccountNotFoundException {
        service.deposit(cardNumber, amount);
        return ResponseEntity.noContent().build();
    }
}
