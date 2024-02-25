package com.github.wmuth.JavaCashSystem.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.wmuth.JavaCashSystem.dto.DiscountDTO;
import com.github.wmuth.JavaCashSystem.dto.ItemDTO;

public class SaleTest {
    private Sale s;
    private final ItemDTO EXPECTED = new ItemDTO(60.0, 0.12, 0, 1, 123456L, "ExampleItem1");

    @BeforeEach
    void setUpEach() {
        this.s = new Sale();
    }

    @Test
    void testAddDiscounts() {
        final List<DiscountDTO> LIST = new ArrayList<DiscountDTO>();
        LIST.add(new DiscountDTO(0.5, 123456L));

        this.s.addItem(EXPECTED);
        this.s.addDiscounts(LIST);

        ItemDTO[] resArr = this.s.getItems();
        assertEquals(resArr[0].getDiscount(), 0.5);
    }

    @Test
    void testAddItem() {
        assertEquals(this.s.getItems().length, 0);
        this.s.addItem(EXPECTED);
        assertEquals(this.s.getItems().length, 1);
    }

    @Test
    void testAddPayment() {
        this.s.addItem(EXPECTED);
        this.s.endSale();
        double remaining = this.s.addPayment(7.2);
        assertEquals(remaining, 60);
    }

    @Test
    void testCheckItemInSale() {
        this.s.addItem(EXPECTED);
        assertEquals(this.s.checkItemInSale(0, EXPECTED.getBarcode()), true);
    }

    @Test
    void testCheckItemInSaleIncrement() {
        this.s.addItem(EXPECTED);
        this.s.checkItemInSale(4, EXPECTED.getBarcode());

        ItemDTO[] resArr = this.s.getItems();
        assertEquals(resArr[0].getCount(), 5);
    }

    @Test
    void testEndSale() {
        this.s.addItem(EXPECTED);
        double total = this.s.endSale();
        assertEquals(total, 67.2);
    }

    @Test
    void testGetTime() {
        s.completeSale();
        final LocalDateTime T0 = LocalDateTime.now();
        final LocalDateTime T1 = s.getTime();

        assertAll("Verify time is same",
                () -> assertEquals(T0.getDayOfMonth(), T1.getDayOfMonth()),
                () -> assertEquals(T0.getDayOfWeek(), T1.getDayOfWeek()),
                () -> assertEquals(T0.getHour(), T1.getHour()),
                () -> assertEquals(T0.getSecond(), T1.getSecond()));

    }
}
