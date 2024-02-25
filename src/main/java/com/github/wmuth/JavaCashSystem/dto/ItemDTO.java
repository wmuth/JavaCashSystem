package com.github.wmuth.JavaCashSystem.dto;

/**
 * Our item data transfer object
 */
public class ItemDTO {
    private final Double price;
    private final Double tax;
    private final Double discount;
    private final Integer count;
    private final Long barcode;
    private final String name;

    /**
     * Create immutable ItemDTO object
     * 
     * @param p Price as double
     * @param t Tax as double, 0.25 = 25% tax
     * @param d Discount as double, 0.25 = 25% discount
     * @param c Count of item, 5 = 5 items sold
     * @param b Barcode formatted as long
     * @param n Name of item
     */
    public ItemDTO(double p, double t, double d, int c, long b, String n) {
        this.price = p;
        this.tax = t;
        this.discount = d;
        this.count = c;
        this.barcode = b;
        this.name = n;
    }

    /**
     * Get the items price
     * 
     * @return price double
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Get the items tax
     * 
     * @return tax double 0.5 = 50%
     */
    public double getTax() {
        return this.tax;
    }

    /**
     * Get the items applied discount
     * 
     * @return discount double 0.5 = 50%
     */
    public double getDiscount() {
        return this.discount;
    }

    /**
     * Get the count sold of the item
     * 
     * @return count int, 5 = 5 items sold
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Get the barcode of the item
     * 
     * @return barcode formatted as long
     */
    public long getBarcode() {
        return this.barcode;
    }

    /**
     * Get the name of the item
     * 
     * @return name of item
     */
    public String getName() {
        return this.name;
    }
}
