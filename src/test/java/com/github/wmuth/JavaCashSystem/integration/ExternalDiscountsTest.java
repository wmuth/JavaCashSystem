package com.github.wmuth.JavaCashSystem.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.wmuth.JavaCashSystem.dto.DiscountDTO;

public class ExternalDiscountsTest {
    private ExternalDiscounts extDisc;

    @BeforeEach
    void setUpEach() {
        this.extDisc = new ExternalDiscounts();
    }

    @Test
    void testgetdiscountsCorrectID() {
        List<DiscountDTO> resp = this.extDisc.getDiscounts(123);
        assertEquals(resp.size(), 3);
    }

    @Test
    void testgetdiscountsWrongID() {
        List<DiscountDTO> resp = this.extDisc.getDiscounts(456);
        assertEquals(resp.size(), 0);
    }
}
