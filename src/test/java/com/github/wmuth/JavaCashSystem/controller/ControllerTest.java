package com.github.wmuth.JavaCashSystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.wmuth.JavaCashSystem.dto.ViewDTO;
import com.github.wmuth.JavaCashSystem.integration.ItemNotFoundException;

public class ControllerTest {
    private Controller contr;

    @BeforeEach
    void setUpController() {
        this.contr = new Controller();
        this.contr.startSale();
    }

    @Test
    void testEndSaleTotal() {
        long ident = 123456L;
        try {
            this.contr.startScan(2, ident);
            ViewDTO resp = this.contr.endSale();
            assertEquals(resp.getData().get(), "134.40");

        } catch (ItemNotFoundException ife) {
            fail(String.format("Item %d should exist", ident));
        }
    }

    @Test
    void testGetDiscountsWrongID() {
        ViewDTO resp = this.contr.getDiscounts(987);
        assertEquals(resp.getData().isEmpty(), true, "No discounts should exist");
    }

    @Test
    void testGetDiscountsRightID() {
        ViewDTO resp = this.contr.getDiscounts(123);
        assertEquals(resp.getData().get(), "True", "Discounts should exist");
    }

    @Test
    void testInputPaymentPartial() {
        long ident = 123456L;
        try {
            this.contr.startScan(2, 123456L);
        } catch (ItemNotFoundException ife) {
            fail(String.format("Item %d should exist", ident));
        }
        this.contr.endSale();

        // Partial payment
        ViewDTO resp = this.contr.inputPayment(34.40);
        assertEquals(resp.getData().get(), "100.00");
        assertEquals(resp.getSuccess(), false);
    }

    @Test
    void testInputPaymentNoChange() {
        long ident = 123456L;
        try {
            this.contr.startScan(2, 123456L);
        } catch (ItemNotFoundException ife) {
            fail(String.format("Item %d should exist", ident));
        }
        this.contr.endSale();

        // No change
        ViewDTO resp = this.contr.inputPayment(134.40);
        assertEquals(resp.getData().isEmpty(), true);
        assertEquals(resp.getSuccess(), true);
    }

    @Test
    void testInputPaymentChange() {
        long ident = 123456L;
        try {
            this.contr.startScan(2, 123456L);
        } catch (ItemNotFoundException ife) {
            fail(String.format("Item %d should exist", ident));
        }
        this.contr.endSale();

        // Change
        ViewDTO resp = this.contr.inputPayment(150);
        assertEquals(resp.getData().get(), "15.60");
        assertEquals(resp.getSuccess(), true);
    }

    @Test
    void testStartScanAddItem() {
        long ident = 123456L;
        try {
            // Add an item
            ViewDTO resp = this.contr.startScan(2, ident);
            assertEquals(resp.getData().get(), "ExampleItem1");

            // Buy an already existing item
            resp = this.contr.startScan(1, ident);
            assertEquals(resp.getData().isEmpty(), true);
            assertEquals(resp.getSuccess(), true);

            // Make sure item count was incremented
            resp = this.contr.endSale();
            assertEquals(resp.getData().get(), "201.60");

        } catch (ItemNotFoundException ife) {
            fail(String.format("Item %d should exist", ident));
        }
    }

    @Test
    void testStartScanNonexistantItem() {
        assertThrows(ItemNotFoundException.class,
                () -> {
                    this.contr.startScan(1, 987654L);
                },
                "Finding nonexistant item should have thrown!");
    }
}
