package com.github.wmuth.JavaCashSystem.model;

/**
 * The register class for our cash system program
 */
public class Register {
    private double cash;

    /**
     * Creates the register object with default values
     */
    public Register() {
        this.cash = 0.0;
    }

    /**
     * Increases the amount present in the register by the amount paid by customer
     * 
     * @param amountPaid amount paid by customer
     */
    public void changeAmount(double amountPaid) {
        this.cash += amountPaid;
    }

    /**
     * Gets the cash currently in the register
     * 
     * @return cash in register as double
     */
    public double getCurrentCash() {
        return this.cash;
    }
}
