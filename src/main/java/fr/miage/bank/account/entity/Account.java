package fr.miage.bank.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String accountNumber;
    private String cardNumber;
    private String cardType;
    private String cardExpirationDate;
    private String cardCvv;
    private float balance;
}
