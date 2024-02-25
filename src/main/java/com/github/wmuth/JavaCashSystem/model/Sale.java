package com.github.wmuth.JavaCashSystem.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import com.github.wmuth.JavaCashSystem.dto.DiscountDTO;
import com.github.wmuth.JavaCashSystem.dto.ItemDTO;
import com.github.wmuth.JavaCashSystem.dto.SaleDTO;
import com.github.wmuth.JavaCashSystem.dto.TotalDTO;

/**
 * The sale class for our cash system program
 */
public class Sale extends Observable {
    private Map<Long, ItemDTO> items;
    private Register reg;
    private LocalDateTime time;
    private Double totalToPay;
    private Double originalTotal;
    private Double totalWasInput;

    /**
     * Creates the sale object with default values
     */
    public Sale() {
        this.items = new HashMap<Long, ItemDTO>();
        this.reg = new Register();
        this.totalToPay = 0.0;
        this.originalTotal = 0.0;
        this.totalWasInput = 0.0;
    }

    /**
     * Gets an array of items currently in the sale
     * 
     * @return ItemDTO[] of the sale items, can be empty
     */
    public ItemDTO[] getItems() {
        return this.items.values().toArray(new ItemDTO[0]);
    }

    /**
     * Gets the time value of the sale
     * 
     * @return LocalDateTime of sale, not defined if sale not finished
     */
    public LocalDateTime getTime() {
        return this.time;
    }

    /**
     * Gets the original total to pay for the customer
     * should not be used before ending sale
     * 
     * @return total for customer to pay,
     */
    public Double getOriginalTotal() {
        return this.originalTotal;
    }

    /**
     * Gets how much the customer paid
     * should not be used before ending sale
     * 
     * @return total the customer paid
     */
    public Double getPaidTotal() {
        return this.totalWasInput;
    }

    /**
     * Adds an item to the sale
     * 
     * @param item ItemDTO of item from external inventory
     */
    public void addItem(ItemDTO item) {
        this.items.put(item.getBarcode(), item);
    }

    /**
     * Ends the sale, calculating total
     * 
     * @return the total to pay
     */
    public double endSale() {
        this.totalToPay = this.calcTotal();
        this.originalTotal = this.totalToPay;
        return this.totalToPay;
    }

    /**
     * Adds a list of discounts onto the current sale
     * 
     * @param discounts list of discountDTO to apply to the items
     */
    public void addDiscounts(List<DiscountDTO> discounts) {
        for (DiscountDTO discount : discounts) {
            this.applyDiscount(discount);
        }
    }

    /**
     * Adds a payment into the current sale
     * 
     * @param amountPaid the double amount paid by customer
     * @return The total left to pay
     */
    public double addPayment(double amountPaid) {
        this.totalToPay -= amountPaid;
        this.totalWasInput += amountPaid;
        setChanged();
        notifyObservers(new TotalDTO(amountPaid));
        return this.totalToPay;
    }

    /**
     * Checks if an item is already in the sale
     * If it is, then increments the item by nrOfItem
     * 
     * @param nrOfItem the amount of the item to add
     * @param barcode  the barcode of the item
     * @return true if item was present, else false
     */
    public boolean checkItemInSale(int nrOfItem, long barcode) {
        if (this.hasBarcode(barcode)) {
            this.increaseExistingItem(barcode, nrOfItem);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Complete the sale, saving time, adding money to register and returing a
     * reciept
     * 
     * @return reciept object of this sale
     */
    public Reciept completeSale() {
        saveTimeOfSale();
        this.reg.changeAmount(totalWasInput);
        return new Reciept(this.DTOify());
    }

    /**
     * Increases count of item which is already in sale
     * 
     * @param barcode the barcode of the item in long format
     * @param how     many counts of the item to add
     */
    private void increaseExistingItem(long barcode, int nrOfItem) {
        ItemDTO item = this.items.get(barcode);
        // ItemDTO immutable, need to create new one with old data
        ItemDTO newItem = new ItemDTO(item.getPrice(), item.getTax(), item.getDiscount(), (item.getCount() + nrOfItem),
                item.getBarcode(),
                item.getName());
        this.items.put(barcode, newItem);
    }

    /**
     * Checks if discount applies to items in sale and if so, creates new ItemDTO
     * object with included discount
     * 
     * @param discount the discount to try to apply
     */
    private void applyDiscount(DiscountDTO discount) {
        for (long key : this.items.keySet()) {
            if (key == discount.getBarcode()) {
                ItemDTO item = this.items.get(key);
                // ItemDTO immutable, need to create new one with old data
                ItemDTO newItem = new ItemDTO(item.getPrice(), item.getTax(), discount.getDiscount(), item.getCount(),
                        item.getBarcode(),
                        item.getName());
                this.items.put(key, newItem);
            }
        }
    }

    /**
     * Saves the time of the sale
     */
    private void saveTimeOfSale() {
        this.time = LocalDateTime.now();
    }

    /**
     * Calculates the total for the sale including discounts and tax
     * 
     * @return the total as double
     */
    private double calcTotal() {
        double total = 0;

        for (long key : this.items.keySet()) {
            ItemDTO item = this.items.get(key);
            total += (((item.getPrice() * (1.0 + item.getTax())) * (1.0 - item.getDiscount())) * item.getCount());
        }

        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Creataed DTO based on this sale
     * 
     * @return this sale as SaleDTO
     */
    private SaleDTO DTOify() {
        return new SaleDTO(this);
    }

    /**
     * Checks if the items map already contains the barcode
     * 
     * @param barcode the long formatted barcode to look for
     * @return true if the map contains the barcode else false
     */
    private boolean hasBarcode(long barcode) {
        return this.items.containsKey(barcode);
    }
}
