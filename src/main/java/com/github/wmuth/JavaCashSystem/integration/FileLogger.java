package com.github.wmuth.JavaCashSystem.integration;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger which can log things to a file.
 * Based on the file logger example from the course book.
 * Intentionally not using polymorphism here and I only have one logger.
 */
public class FileLogger {
    private PrintWriter pw;
    private DateTimeFormatter dtf;

    /**
     * Get the file logger which logs to the specified path
     *
     * @param path the string path relative to program
     */
    public FileLogger(String path) {
        try {
            this.pw = new PrintWriter(new FileWriter(path, true), true);
            this.dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        } catch (IOException ex) {
            System.out.println("Log failure");
            System.out.println(ex);
        }
    }

    /**
     * Logs the string to the file this logger is attached to
     * LOGS WITH TIMESTAMP!
     *
     * @param s the string to log
     */
    public void log(String s) {
        this.pw.println(String.format("%s : %s", this.dtf.format(LocalDateTime.now()), s));
    }

    /**
     * Returns the printwriter used by this FileLogger
     * Used where other printfunctions such as Exception.printStackTrace()
     * should use the same log location - kind of hacky, maybe TODO:
     *
     * @return the printwriter used by this filelogger
     */
    public PrintWriter getPw() {
        return pw;
    }
}
