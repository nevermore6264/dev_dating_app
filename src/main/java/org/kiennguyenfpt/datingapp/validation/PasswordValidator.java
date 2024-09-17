package org.kiennguyenfpt.datingapp.validation;

import java.util.regex.Pattern;

public class PasswordValidator {
    /**
        Explanation of the regex pattern:
        ^                 # start-of-string
        $                 # end-of-string
        (?=.*[0-9])       # a digit must occur at least once
        (?=.*[a-z])       # a lower case letter must occur at least once
        (?=.*[A-Z])       # an upper case letter must occur at least once
        (?=.*[@#$%^&+=])  # a special character must occur at least once
        (?=\S+$)          # no whitespace allowed in the entire string
        .{8,}             # anything, at least eight places though
        For example, the password "Abc@1234" is valid because it contains at least one digit, one lower case letter, one upper case letter, one special character, and has a length of at least 8 characters.
     */
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean validate(String password) {
        if (password == null) {
            return false;
        }
        return pattern.matcher(password).matches();
    }
}
