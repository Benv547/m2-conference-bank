package fr.miage.bank.api.assembler;

import fr.miage.bank.account.entity.Account;
import fr.miage.bank.account.exception.AccountNotFoundException;
import fr.miage.bank.api.resource.AccountController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Service;

import javax.ejb.Singleton;
import javax.ejb.Startup;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Singleton
@Startup
@Service
public class AccountAssembler implements RepresentationModelAssembler<Account, EntityModel<Account>> {

    @Override
    public EntityModel<Account> toModel(Account entity) {
        try {
            return EntityModel.of(entity,
                    linkTo(methodOn(AccountController.class).get(entity.getCardNumber())).withSelfRel(),
                    linkTo(methodOn(AccountController.class).withdraw(entity.getCardNumber(), 0.0f)).withRel("withdraw").withTitle("Withdraw money"),
                    linkTo(methodOn(AccountController.class).deposit(entity.getCardNumber(), 0.0f)).withRel("deposit").withTitle("Deposit money")
            );
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CollectionModel<EntityModel<Account>> toCollectionModel(Iterable<? extends Account> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }

}
