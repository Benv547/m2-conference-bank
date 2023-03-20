package fr.miage.bank.api.handler;

import fr.miage.bank.account.exception.AccountAlreadyExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccountAlreadyExistExceptionHandler {

    @ExceptionHandler(AccountAlreadyExistException.class)
    public ResponseEntity<?> handleConstraintViolationException(AccountAlreadyExistException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
