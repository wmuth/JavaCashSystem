package com.github.wmuth.JavaCashSystem.integration;

import com.github.wmuth.JavaCashSystem.dto.ItemDTO;
import com.github.wmuth.JavaCashSystem.model.Reciept;

/**
 * External accounting integration for our program
 */
public class ExternalInventory {
    /**
     * Creates the external inventory object
     */
    public ExternalInventory() {
    }

    /**
     * FOR TESTING - WILL CRASH
     *
     * @param b - boolean, to distinguish this constructor from other constructors
     * @throws DatabaseException ALWAYS
     */
    public ExternalInventory(Boolean b) {
        String ip = "192.168.50.2";
        DatabaseUnreachableException e = new DatabaseUnreachableException(
                String.format("Database at IP: %s wasn't able to be reached!", ip));
        throw new DatabaseException(e);
    }

    /**
     * Logs a new sale, currently does nothing
     *
     * @param rec The reciept to log
     */
    public void logNewSale(Reciept rec) {
        // This logs the sale somewhere not defined right now
        // For S4 purposes the printer prints to system out
    }

    /**
     * Gets item info from input barcode
     * 
     * @param barcode long formatted barcode, e.g. 123456L
     * @throws ItemNotFoundException if item was not found
     * @throws DatabaseException     if the live database connection dies
     * @return ItemDTO of the found item
     */
    public ItemDTO getItemInfo(long barcode) throws ItemNotFoundException {
        if (barcode == 123456L) {
            return new ItemDTO(60.0, 0.12, 0, 1, barcode, "ExampleItem1");
        } else if (barcode == 234567L) {
            return new ItemDTO(40.0, 0.06, 0, 1, barcode, "ExampleItem2");
        } else if (barcode == 1234567890128L) {
            return new ItemDTO(10.0, 0.25, 0, 1, barcode, "ExampleItem3");
        } else if (barcode == 987L) {
            String ip = "192.168.50.2";
            DatabaseConnectionCrashedException e = new DatabaseConnectionCrashedException(
                    String.format("Live database connection to IP:%s crashed!", ip));
            throw new DatabaseException(e);
        } else {
            throw new ItemNotFoundException(barcode);
        }
    }
}
