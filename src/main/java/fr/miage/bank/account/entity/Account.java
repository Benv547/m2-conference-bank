package fr.miage.bank.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account", uniqueConstraints =
        @UniqueConstraint(columnNames = {
                "email",
                "accountNumber",
                "cardNumber"}
        ))
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
    @Min(value = 0, message = "Balance must be positive")
    private float balance;
}
