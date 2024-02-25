package com.github.wmuth.JavaCashSystem.dto;

import java.util.Optional;

/**
 * Our view data transfer object
 */
public class ViewDTO {
    /**
     * Enum for types of action that the viewDTO is the output of
     * e.g. if the return is the result of an InputPayment operation
     * the returned ViewDTO will have an enum of type InputPayment
     */
    public static enum ActionType {
        StartSale,
        StartScan,
        GetDiscounts,
        EndSale,
        InputPayment,
    }

    private final Optional<String> data;
    private final ActionType type;
    private final Boolean success;

    /**
     * Create immutable ViewDTO object
     * 
     * @param d Optional.empty if no data, otherwise optional with data as double
     *          array
     * @param t ActionType enum based on what action created this viewDTO
     */
    public ViewDTO(Optional<String> d, ActionType t, Boolean b) {
        this.data = d;
        this.type = t;
        this.success = b;
    }

    /**
     * Get the data contained in the object
     *
     * @return Optional.empty if no data, otherwise array of doubles with the data
     */
    public Optional<String> getData() {
        return this.data;
    }

    /**
     * Get the type of the action that spawned this object
     *
     * @return ActionType that created this viewDTO
     */
    public ActionType getType() {
        return this.type;
    }

    /**
     * Get wether the operation was successful
     *
     * @return True if was successful else false
     */
    public Boolean getSuccess() {
        return this.success;
    }
}
