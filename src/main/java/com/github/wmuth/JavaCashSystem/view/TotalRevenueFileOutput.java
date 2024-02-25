package com.github.wmuth.JavaCashSystem.view;

import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

import com.github.wmuth.JavaCashSystem.dto.TotalDTO;
import com.github.wmuth.JavaCashSystem.integration.FileLogger;

/**
 * A class which keeps track of the total of all sales and prints
 * this total to a file specified in the constructor
 */
public class TotalRevenueFileOutput implements Observer {
    private double total;
    private final FileLogger fl;

    /**
     * Creates a TotalRevenueFileOutput with a initial total of 0
     */
    public TotalRevenueFileOutput(String path) {
        this.total = 0;
        this.fl = new FileLogger(path);
    }

    @Override
    public void update(Observable sender, Object data) {
        TotalDTO dto = (TotalDTO) data;
        changeTotal(dto.getAmount());
        logTotal();
    }

    private void logTotal() {
        this.fl.log(String.format("The running total is: %.2f", this.total));
    }

    private void changeTotal(final double amount) {
        this.total += amount;
    }
}
