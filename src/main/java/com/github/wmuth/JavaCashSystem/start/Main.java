package com.github.wmuth.JavaCashSystem.start;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

import com.github.wmuth.JavaCashSystem.integration.FileLogger;
import com.github.wmuth.JavaCashSystem.view.View;

/**
 * The main function for the entire program
 * Simply starts the controller which is then in charge
 */
public class Main {
    /**
     * Main function, simply creates controller
     * 
     * @param args, arguments to program, ignored for now
     */
    public static void main(String[] args) {
        try {
            View v = new View();

        } catch (Exception e) {
            FileLogger fl = new FileLogger("log.txt");
            fl.log(String.format("%s", e.getMessage()));
            e.printStackTrace(fl.getPw());
            JOptionPane.showMessageDialog(null,
                    "The program has encountered a fatal condition and will exit. Contact system administrator.");
            System.exit(1);
        }
    }
}
