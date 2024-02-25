package com.github.wmuth.JavaCashSystem.view;

import java.util.Observable;
import java.util.Observer;

import com.github.wmuth.JavaCashSystem.dto.TotalDTO;

/**
 * A class which keeps track of the total of all sales and prints
 * this total to the System.out
 */
public class TotalRevenueView implements Observer {
    private double total;

    /**
     * Creates a TotalRevenueView with a initial total of 0
     */
    public TotalRevenueView() {
        this.total = 0;
    }

    @Override
    public void update(Observable sender, Object data) {
        TotalDTO dto = (TotalDTO) data;
        changeTotal(dto.getAmount());
        printTotal();
    }

    private void printTotal() {
        System.out.println(String.format("The running total is: %.2f", this.total));
    }

    private void changeTotal(final double amount) {
        this.total += amount;
    }
}
