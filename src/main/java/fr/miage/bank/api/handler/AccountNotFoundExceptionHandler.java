package fr.miage.bank.api.handler;

import fr.miage.bank.account.exception.AccountNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccountNotFoundExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> handleConstraintViolationException(AccountNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
