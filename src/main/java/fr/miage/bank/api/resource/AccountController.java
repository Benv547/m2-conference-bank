package fr.miage.bank.api.resource;

import fr.miage.bank.account.AccountService;
import fr.miage.bank.account.entity.Account;
import fr.miage.bank.account.entity.AccountInput;
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

}
