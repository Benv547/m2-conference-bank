package fr.miage.bank.account.enumeration;

public enum ExceptionMessagesEnum {
    ACCOUNT_NOT_FOUND("Account not found"),
    ACCOUNT_ALREADY_EXIST("Account already exist");

    private String message;

    ExceptionMessagesEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
