package com.github.wmuth.JavaCashSystem.controller;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import com.github.wmuth.JavaCashSystem.dto.DiscountDTO;
import com.github.wmuth.JavaCashSystem.dto.ItemDTO;
import com.github.wmuth.JavaCashSystem.dto.ViewDTO;
import com.github.wmuth.JavaCashSystem.dto.ViewDTO.ActionType;
import com.github.wmuth.JavaCashSystem.integration.ItemNotFoundException;
import com.github.wmuth.JavaCashSystem.integration.ExternalAccounting;
import com.github.wmuth.JavaCashSystem.integration.ExternalDiscounts;
import com.github.wmuth.JavaCashSystem.integration.ExternalInventory;
import com.github.wmuth.JavaCashSystem.integration.Printer;
import com.github.wmuth.JavaCashSystem.model.Reciept;
import com.github.wmuth.JavaCashSystem.model.Sale;
import com.github.wmuth.JavaCashSystem.view.TotalRevenueFileOutput;
import com.github.wmuth.JavaCashSystem.view.TotalRevenueView;

/**
 * The controller class for our cash system program
 * This class combines all our different implementations and model
 * in one place callable by view and responding with data back to the view
 *
 * Besides main, this is the root for our program
 * Giving us the potential to change view in the future
 */
public class Controller {
    private final ExternalAccounting extAcc;
    private final ExternalDiscounts extDis;
    private final ExternalInventory extInv;
    private final Printer print;
    private Sale sale;

    /**
     * Create the controller object
     */
    public Controller() {
        this.extAcc = new ExternalAccounting();
        this.extDis = new ExternalDiscounts();
        this.extInv = new ExternalInventory();
        this.print = new Printer();
    }

    /**
     * Starts a new sale for this controller
     * 
     * @return status update for view
     */
    public ViewDTO startSale() {
        this.sale = new Sale();
        return new ViewDTO(Optional.empty(), ActionType.StartSale, true);
    }

    /**
     * Starts the scanning program flow
     * 
     * @param countSold integer amount sold of item
     * @param barcode   the long encoded version of the barcode
     * @throws ItemNotFoundException if the item was not found
     * @throws DatabaseException if the connection to the database dies
     * @return status update for view
     */
    public ViewDTO startScan(int countSold, long barcode) throws ItemNotFoundException {
        boolean response = this.sale.checkItemInSale(countSold, barcode);

        if (!response) {
            ItemDTO i = this.extInv.getItemInfo(barcode);
            this.sale.addItem(
                    new ItemDTO(i.getPrice(), i.getTax(), i.getDiscount(), countSold, i.getBarcode(), i.getName()));
            return new ViewDTO(Optional.of(i.getName()), ActionType.StartScan, true);

        } else {
            return new ViewDTO(Optional.empty(), ActionType.StartScan, true);
        }
    }

    /**
     * Gets discounts from external database
     * 
     * @param customerID the integer customer id
     * @return status update for view
     */
    public ViewDTO getDiscounts(int customerID) {
        List<DiscountDTO> discounts = this.extDis.getDiscounts(customerID);

        if (discounts.size() > 0) {
            this.sale.addDiscounts(discounts);
            return new ViewDTO(Optional.of("True"), ActionType.GetDiscounts, true);
        } else {
            return new ViewDTO(Optional.empty(), ActionType.GetDiscounts, false);
        }
    }

    /**
     * Ends the sale
     * 
     * @return status update for view
     */
    public ViewDTO endSale() {
        return new ViewDTO(Optional.of(String.format("%.2f", this.sale.endSale())), ActionType.EndSale, true);
    }

    /**
     * Input a new payment into the sale
     * 
     * @param amountPaid the amount paid by customer
     * @return status update for view
     */
    public ViewDTO inputPayment(double amountPaid) {
        double remaining = this.sale.addPayment(amountPaid);

        if (customerFinishedPaying(remaining)) {
            if (remaining < 0) {
                completeAndLogSale();
                return new ViewDTO(Optional.of(String.format("%.2f", -remaining)), ActionType.InputPayment, true);
            } else {
                completeAndLogSale();
                return new ViewDTO(Optional.empty(), ActionType.InputPayment, true);
            }
        } else {
            return new ViewDTO(Optional.of(String.format("%.2f", remaining)), ActionType.InputPayment, false);
        }
    }

    /**
     * Adds observer to the observable model
     *
     * @param o The obsever to add to the observable
     */
    public void addObserver(Observer o) {
        this.sale.addObserver(o);
    }

    private void completeAndLogSale() {
        Reciept rec = this.sale.completeSale();
        this.extAcc.logNewSale(rec);
        this.print.print(rec);
    }

    private boolean customerFinishedPaying(double remaining) {
        // Double inexactness issues, TODO: BigDecimal
        return (remaining <= 0.01) ? true : false;
    }
}
