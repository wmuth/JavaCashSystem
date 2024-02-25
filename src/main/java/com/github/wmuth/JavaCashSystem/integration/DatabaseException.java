package com.github.wmuth.JavaCashSystem.integration;

/**
 * Exception type which is thrown if there is an error with the databse
 */
public class DatabaseException extends RuntimeException {
    /**
     * Creates the exception, using java.lang.RuntimeException constructor
     * 
     * @param cause the exception which caused this exception
     */
    public DatabaseException(Throwable cause) {
        super("Database had an exception! Check cause!", cause);
    }
}
