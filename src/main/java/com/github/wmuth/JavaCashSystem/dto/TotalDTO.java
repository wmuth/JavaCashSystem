package com.github.wmuth.JavaCashSystem.dto;

/**
 * Our total data transfer object
 * to be sent from model to observers
 */
public class TotalDTO {
    private final double amount;

    /**
     * Create the TotalDTO with the amount param a
     *
     * @param a the amount
     */
    public TotalDTO(final double a) {
        this.amount = a;
    }

    /**
     * Gets the DTOs amount
     *
     * @return the amount
     */
    public double getAmount() {
        return this.amount;
    }
}
