package com.github.wmuth.JavaCashSystem.integration;

/**
 * Exception thrown when a live connection to the database crashes
 */
public class DatabaseConnectionCrashedException extends RuntimeException {
    private final String message;

    /**
     * Creates the exception, using DatabaseException constructor
     *
     * @param m The message string to attach to the exception
     */
    public DatabaseConnectionCrashedException(String m) {
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
