package com.github.wmuth.JavaCashSystem.integration;

import java.util.ArrayList;
import java.util.List;
import com.github.wmuth.JavaCashSystem.dto.DiscountDTO;

/**
 * External discounts integration for our program
 */
public class ExternalDiscounts {
    /**
     * Creates the external discounts object
     */
    public ExternalDiscounts() {
    }

    /**
     * Get discount if customerID is in database
     * 
     * @param customerID the id to look for
     * @return If ID not found, empty list otherwise list with all discounts which
     *         apply to that customer
     */
    public List<DiscountDTO> getDiscounts(int customerID) {
        if (customerID == 123) {
            List<DiscountDTO> ret = new ArrayList<DiscountDTO>();
            ret.add(new DiscountDTO(0.5, 123456L));
            ret.add(new DiscountDTO(0.25, 234567L));
            ret.add(new DiscountDTO(0.5, 1234567890128L));
            return ret;
        } else {
            return new ArrayList<DiscountDTO>();
        }
    }
}
