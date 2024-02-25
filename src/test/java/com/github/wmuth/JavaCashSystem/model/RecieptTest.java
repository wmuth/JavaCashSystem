package com.github.wmuth.JavaCashSystem.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.wmuth.JavaCashSystem.dto.ItemDTO;

public class RecieptTest {
    private Reciept rec;
    private Sale s;

    @BeforeEach
    void setUpEach() {
        this.s = new Sale();
        ItemDTO i = new ItemDTO(60.0, 0.12, 0, 1, 123456L, "ExampleItem1");
        this.s.addItem(i);
        ItemDTO i2 = new ItemDTO(40.0, 0.06, 0, 2, 234567L, "ExampleItem2");
        this.s.addItem(i2);
        this.s.endSale();
        this.s.addPayment(152);
        this.s.completeSale();

        this.rec = this.s.completeSale();
    }

    @Test
    void testToString() {
        // Stringbuilder like reciept but with hardcoded strings (besides time)
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------------------------\n");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = this.s.getTime().format(formatter);

        sb.append(String.format("Reciept for sale at time: " + time + "\n"));
        sb.append("---------------------ITEMS----------------------\n");
        sb.append("count x name\t\t\t\ttotal cost\n");

        sb.append("1xExampleItem1\t\t\t\t67.20\n");
        sb.append("2xExampleItem2\t\t\t\t84.80\n");

        sb.append("\n");
        sb.append("Total is:\t\t\t\t152.00\n");
        sb.append("\n");

        sb.append("Paid: 152.00 Change 0.00\n");
        sb.append("-----------------------------------------------\n");
        sb.append("Thank you shopping at salepoint!");

        String recS = this.rec.toString();
        sb.toString();

        assertEquals(recS, sb.toString());
    }
}
