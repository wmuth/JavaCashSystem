package com.github.wmuth.JavaCashSystem.dto;

/**
 * Our discount data transfer object
 */
public class DiscountDTO {
    private final Double discount;
    private final Long barcode;

    /**
     * Create new immutable DiscountDTO
     * 
     * @param d discount, 50% = 0.5
     * @param b barcode in long format
     */
    public DiscountDTO(double d, long b) {
        this.discount = d;
        this.barcode = b;
    }

    /**
     * Get the discount value
     * 
     * @return discount of type double, 50% = 0.5
     */
    public double getDiscount() {
        return this.discount;
    }

    /**
     * Get the barcode
     * 
     * @return barcode of type long
     */
    public long getBarcode() {
        return this.barcode;
    }
}
