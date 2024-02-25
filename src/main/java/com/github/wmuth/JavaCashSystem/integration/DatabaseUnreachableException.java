package com.github.wmuth.JavaCashSystem.integration;

/**
 * Exception type which is thrown if the program couldn't reach the database
 */
public class DatabaseUnreachableException extends RuntimeException {
    private final String message;

    /**
     * Creates the exception, using DatabaseException constructor
     *
     * @param m The messsage string to attach to the exception
     */
    public DatabaseUnreachableException(String m) {
        this.message = m;
    }

    /**
     * Gets the message from the exception
     *
     * @return the message provided when exception was created
     */
    public String getMessage() {
        return message;
    }
}
