package org.kiennguyenfpt.datingapp.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character");
    }
}
