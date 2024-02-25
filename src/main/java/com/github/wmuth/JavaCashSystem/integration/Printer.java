package com.github.wmuth.JavaCashSystem.integration;

import com.github.wmuth.JavaCashSystem.model.Reciept;

/**
 * Printer integration for our program
 */
public class Printer {
    /**
     * Creates the printer object
     */
    public Printer() {
    }

    /**
     * Prints the reciept
     * 
     * @param rec the reciept to print
     */
    public void print(Reciept rec) {
        System.out.println(rec.toString());
    }
}
