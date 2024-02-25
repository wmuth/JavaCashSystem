package com.github.wmuth.JavaCashSystem.model;

import java.time.format.DateTimeFormatter;

import com.github.wmuth.JavaCashSystem.dto.ItemDTO;
import com.github.wmuth.JavaCashSystem.dto.SaleDTO;

/**
 * The reciept class for our cash system program
 */
public class Reciept {
    private final SaleDTO sale;

    /**
     * Create new reciept object based on sale
     * 
     * @param sDTO SaleDTO to base Reciept on
     */
    public Reciept(SaleDTO sDTO) {
        this.sale = sDTO;
    }

    /**
     * Creates string based on reciept information
     * 
     * @return a fully formatted System.out printable string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------------------------\n");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = this.sale.getTime().format(formatter);
        sb.append(String.format("Reciept for sale at time: " + time + "\n"));
        sb.append("---------------------ITEMS----------------------\n");
        sb.append("count x name\t\t\t\ttotal cost\n");

        for (ItemDTO item : this.sale.getItems()) {
            double total = (item.getPrice() * (1.00 + item.getTax()) * (1.00 - item.getDiscount()) * item.getCount());
            sb.append(String.format("%dx%s\t\t\t\t%.2f\n", item.getCount(), item.getName(), total));
        }
        sb.append("\n");
        sb.append(String.format("Total is:\t\t\t\t%.2f\n", this.sale.getTotal()));
        sb.append("\n");
        double change = (this.sale.getPaid() - this.sale.getTotal());
        sb.append(String.format("Paid: %.2f Change %.2f\n", this.sale.getPaid(), change));
        sb.append("-----------------------------------------------\n");
        sb.append("Thank you shopping at salepoint!");

        return sb.toString();
    }
}
