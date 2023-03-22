package fr.miage.bank.api.resource;

import fr.miage.bank.account.AccountService;
import fr.miage.bank.account.entity.Account;
import fr.miage.bank.account.entity.AccountInput;
import fr.miage.bank.account.exception.AccountAlreadyExistException;
import fr.miage.bank.account.exception.AccountNotFoundException;
import fr.miage.bank.api.assembler.AccountAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ejb.EJB;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    @EJB
    private AccountService service;

    @EJB
    private AccountAssembler assembler;

    @GetMapping("/{cardNumber}")
    public ResponseEntity<EntityModel<Account>> get(@PathVariable("cardNumber") String cardNumber) {
        try {
            return ResponseEntity.ok(assembler.toModel(service.get(cardNumber)));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<EntityModel<Account>> create(@RequestBody @Valid AccountInput account) throws AccountAlreadyExistException {
        Account entity = new Account();
        entity.setEmail(account.getEmail());
        entity.setFirstname(account.getFirstname());
        entity.setLastname(account.getLastname());
        URI location = URI.create(String.format("/account/%s", entity.getCardNumber()));
        Account saved = service.create(entity);
        return ResponseEntity.created(location).body(assembler.toModel(saved));
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
