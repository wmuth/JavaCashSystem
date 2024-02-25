package com.github.wmuth.JavaCashSystem.dto;

import java.time.LocalDateTime;

import com.github.wmuth.JavaCashSystem.model.Sale;

/**
 * Our sale data transfer object
 */
public class SaleDTO {
    private final ItemDTO[] items;
    private final LocalDateTime time;
    private final Double total;
    private final Double paid;

    /**
     * Create immutable SaleDTO object
     * 
     * @param s Sale to create DTO of
     */
    public SaleDTO(Sale s) {
        this.items = s.getItems();
        this.time = s.getTime();
        this.total = s.getOriginalTotal();
        this.paid = s.getPaidTotal();
    }

    /**
     * Get the ItemDTOs included in the sale
     * 
     * @return array of ItemDTO from sale
     */
    public ItemDTO[] getItems() {
        return this.items;
    }

    /**
     * Get the time of the sale
     * 
     * @return LocalDateTime of sale
     */
    public LocalDateTime getTime() {
        return this.time;
    }

    /**
     * Get the total cost of the sale
     * 
     * @return total cost of sale
     */
    public double getTotal() {
        return this.total;
    }

    /**
     * Get the amount customer paid
     * 
     * @return amount cutsomer paid
     */
    public double getPaid() {
        return this.paid;
    }
}
