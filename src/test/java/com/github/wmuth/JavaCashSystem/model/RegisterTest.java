package com.github.wmuth.JavaCashSystem.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegisterTest {
    private Register reg;

    @BeforeEach
    void setUpEach() {
        this.reg = new Register();
    }

    @Test
    void testDefaultCashEmpty() {
        assertEquals(this.reg.getCurrentCash(), 0.0);
    }

    @Test
    void testIncreaseAmount() {
        this.reg.changeAmount(10.0);
        assertEquals(this.reg.getCurrentCash(), 10.0);

        this.reg.changeAmount(3.33);
        assertEquals(this.reg.getCurrentCash(), 13.33);
    }

    @Test
    void testDecreaseAmount() {
        this.reg.changeAmount(10.0);
        this.reg.changeAmount(-5.0);

        assertEquals(this.reg.getCurrentCash(), 5.0);
    }
}
