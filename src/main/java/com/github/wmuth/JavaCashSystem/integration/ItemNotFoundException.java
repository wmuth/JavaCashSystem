package com.github.wmuth.JavaCashSystem.integration;

/**
 * Item not found custom exception
 */
public class ItemNotFoundException extends Exception {
    /**
     * Creates the exception, using java.lang.Exception constructor
     * 
     * @param barcode the barcode which the system tried to look up
     */
    public ItemNotFoundException(long barcode) {
        super(String.format("Item with barcode %d was not found!", barcode));
    }
}
