package com.github.wmuth.JavaCashSystem.integration;

import com.github.wmuth.JavaCashSystem.model.Reciept;

/**
 * External accounting integration for our program
 */
public class ExternalAccounting {
    /**
     * Create the external accounting object
     */
    public ExternalAccounting() {
    }

    /**
     * Logs a sale, does nothing for now since there is not external system
     *
     * @param rec The reciept to log
     */
    public void logNewSale(Reciept rec) {
        // This should log the sale in accounting
    }
}
