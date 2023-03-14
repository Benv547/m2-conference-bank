package fr.miage.bank.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInput {

    @NotNull
    @NotBlank
    @Size(min = 2)
    private String firstname;

    @NotNull
    @NotBlank
    @Size(min = 2)
    private String lastname;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(.+)@(.+)$")
    private String email;

}
