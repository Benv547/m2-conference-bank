package fr.miage.bank.api.resource;

import fr.miage.bank.account.AccountService;
import fr.miage.bank.account.entity.Account;
import fr.miage.bank.account.entity.AccountInput;
import fr.miage.bank.account.exception.AccountNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ejb.EJB;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    @EJB
    private AccountService service;

    @GetMapping()
    public Account get(@RequestBody @Valid AccountInput account) {
        return service.get(account.getEmail(), account.getFirstname(), account.getLastname());
    }

    @PostMapping(value = "/withdraw/{cardNumber}/{amount}")
    public ResponseEntity withdraw(@PathVariable("cardNumber") String cardNumber, @PathVariable("amount") float amount) throws AccountNotFoundException {
        service.withdraw(cardNumber, amount);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/deposit/{cardNumber}/{amount}")
    public ResponseEntity deposit(@PathVariable("cardNumber") String cardNumber, @PathVariable("amount") float amount) throws AccountNotFoundException {
        service.deposit(cardNumber, amount);
        return ResponseEntity.noContent().build();
    }
}
