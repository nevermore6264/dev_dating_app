package org.kiennguyenfpt.datingapp.exceptions;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super("Invalid email format (example: name@domain.com)");
    }
}
