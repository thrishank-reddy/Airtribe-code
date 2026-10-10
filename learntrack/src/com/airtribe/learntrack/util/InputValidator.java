package com.airtribe.learntrack.util;

import com.airtribe.learntrack.exception.InvalidInputException;

/** Small static helpers for checking and converting raw console input. */
public class InputValidator {

    private InputValidator() {
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /** Returns the trimmed value or throws if it is blank. */
    public static String requireNonEmpty(String value, String fieldName) {
        if (isNullOrEmpty(value)) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
        return value.trim();
    }

    /** Very simple email check: must contain '@' with a '.' after it. */
    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) {
            return false;
        }
        int at = email.indexOf('@');
        int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1;
    }

    public static String requireValidEmail(String email) {
        if (!isValidEmail(email)) {
            throw new InvalidInputException("'" + email + "' is not a valid email address.");
        }
        return email.trim();
    }

    /** Converts text to a positive int (try-catch around parsing). */
    public static int parsePositiveInt(String text, String fieldName) {
        if (isNullOrEmpty(text)) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
        int value;
        try {
            value = Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName + " must be a whole number, but got '" + text + "'.");
        }
        if (value <= 0) {
            throw new InvalidInputException(fieldName + " must be a positive number.");
        }
        return value;
    }
}
