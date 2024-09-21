package org.kiennguyenfpt.datingapp.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Password must contain at least one uppercase letter, one lowercase letter, one digit, one special character, and length at least 8 (ex: Abc@1234");
    }
}
