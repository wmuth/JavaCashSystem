package com.github.wmuth.JavaCashSystem.integration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.wmuth.JavaCashSystem.dto.ItemDTO;
import com.github.wmuth.JavaCashSystem.integration.ItemNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExternalInventoryTest {
    private ExternalInventory extInv;

    @BeforeEach
    void setUpEach() {
        this.extInv = new ExternalInventory();
    }

    @Test
    void testGetItemInfo() {
        // This identity HAS to be valid!
        long ident = 123456L;

        try {
            final ItemDTO FETCHED = this.extInv.getItemInfo(ident);
            final ItemDTO HARDCODE = new ItemDTO(60.0, 0.12, 0, 1, 123456L, "ExampleItem1");

            assertAll("Verify item has expected arributes",
                    () -> assertEquals(FETCHED.getPrice(), HARDCODE.getPrice(), "price"),
                    () -> assertEquals(FETCHED.getTax(), HARDCODE.getTax(), "tax"),
                    () -> assertEquals(FETCHED.getDiscount(), HARDCODE.getDiscount(), "discount"),
                    () -> assertEquals(FETCHED.getCount(), HARDCODE.getCount(), "count"),
                    () -> assertEquals(FETCHED.getBarcode(), HARDCODE.getBarcode(), "barcode"),
                    () -> assertEquals(FETCHED.getName(), HARDCODE.getName(), "name"));

        } catch (ItemNotFoundException ex) {
            fail(String.format("Item %d is supposed to exist", ident));
        }
    }

    @Test
    void testGetItemNameNotEqual() {
        // These HAVE to be valid!
        long ident1 = 234567L;
        long ident2 = 123456L;

        try {
            ItemDTO item1 = this.extInv.getItemInfo(ident1);
            ItemDTO item2 = this.extInv.getItemInfo(ident2);
            assertNotEquals(item1.getName(), item2.getName());

        } catch (ItemNotFoundException ex) {
            fail(String.format("Item %d and %d are supposed to exist", ident1, ident2));
        }
    }

    @Test
    void testGetItemInfoWrong() {
        assertThrows(ItemNotFoundException.class,
                () -> {
                    this.extInv.getItemInfo(987654L);
                },
                "Finding nonexistant item should have thrown!");
    }
}
