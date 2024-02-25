package com.github.wmuth.JavaCashSystem.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.github.wmuth.JavaCashSystem.controller.Controller;
import com.github.wmuth.JavaCashSystem.dto.ViewDTO;
import com.github.wmuth.JavaCashSystem.dto.ViewDTO.ActionType;
import com.github.wmuth.JavaCashSystem.integration.ItemNotFoundException;
import com.github.wmuth.JavaCashSystem.integration.FileLogger;;

/**
 * The dummy view class for our cash system program
 */
public class View implements ActionListener {
    Controller contr;
    String message;
    String title;
    JFrame frame;
    JLabel messL;
    JTextField tf;
    TotalRevenueView totRV;
    TotalRevenueFileOutput totRFO;

    /**
     * Create the view object
     */
    public View() {
        this.contr = new Controller();
        this.message = "Welcome to POS program!";
        this.title = "POS program";
        this.initGUI();
        this.totRV = new TotalRevenueView();
        this.totRFO = new TotalRevenueFileOutput("runningTotal.txt");
    }

    /**
     * Handles button clicks
     */
    public void actionPerformed(ActionEvent e) {
        // Switch case depending on which button pressed
        switch (e.getActionCommand()) {
            case "start":
                this.updateUI(this.contr.startSale());
                this.contr.addObserver(this.totRV);
                this.contr.addObserver(this.totRFO);
                break;
            case "scan":
                Optional<Long[]> itemInfo = itemInput();
                if (itemInfo.isPresent()) {
                    Long[] info = itemInfo.get();
                    try {
                        this.updateUI(this.contr.startScan(Math.toIntExact(info[0]), info[1]));
                    } catch (ItemNotFoundException ex) {
                        // Do not log this, since program works as expected
                        this.updateUI(new ViewDTO(Optional.empty(), ActionType.StartScan, false));
                    } catch (Exception uncaught) {
                        FileLogger fl = new FileLogger("log.txt");
                        fl.log(String.format("%s", uncaught.getMessage()));
                        uncaught.printStackTrace(fl.getPw());
                        JOptionPane.showMessageDialog(null,
                                "The program has encountered a fatal condition and will exit. Contact system administrator.");
                        System.exit(1);
                    }
                } else {
                    this.updateMessage(
                            "Could not understand input integer. Format is count,barcode");
                }
                break;
            case "discount":
                Optional<Integer> id = userInt();
                if (id.isPresent()) {
                    this.updateUI(this.contr.getDiscounts(id.get()));
                } else {
                    this.updateMessage("Could not understand input integer");
                }
                break;
            case "end":
                this.updateUI(this.contr.endSale());
                break;
            case "pay":
                Optional<Double> paid = userDouble();
                if (paid.isPresent()) {
                    this.updateUI(this.contr.inputPayment(paid.get()));
                } else {
                    this.updateMessage("Could not understand input double");
                }
                break;
            default:
                System.exit(0);
                break;
        }
    }

    /**
     * Parses the text input field as a double
     * 
     * @return Optional.empty if error otherwise Optional with double
     */
    private Optional<Double> userDouble() {
        Optional<String> opt = this.getTextInput();
        if (opt.isPresent()) {
            try {
                return Optional.of(Double.parseDouble(opt.get()));
            } catch (Exception e) {
                // Do not log this, since program works as expected
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    /**
     * Parses the text input field as an integer
     * 
     * @return Optional.empty if error otherwise Optional with integer
     */
    private Optional<Integer> userInt() {
        Optional<String> opt = this.getTextInput();
        if (opt.isPresent()) {
            try {
                return Optional.of(Integer.parseInt(opt.get()));
            } catch (Exception e) {
                // Do not log this, since program works as expected
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    /**
     * Parses the text input field into a Long[2] where the first index is count of
     * item and the second is the barcode
     * 
     * @return the array, [0] = item count, [1] = item barcode
     */
    private Optional<Long[]> itemInput() {
        Optional<String> opt = this.getTextInput();
        if (opt.isPresent()) {
            try {
                String[] split = opt.get().split(",");
                return Optional.of(new Long[] { Long.parseLong(split[0]), Long.parseLong(split[1]) });
            } catch (Exception e) {
                // Do not log this, since program works as expected
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    /**
     * Gets the text from the text input field
     * 
     * @return Optional.empty if error, otherwise Optional with string
     */
    private Optional<String> getTextInput() {
        try {
            return Optional.of(this.tf.getText());
        } catch (Exception e) {
            // Do not log this, since program works as expected
            return Optional.empty();
        }
    }

    /**
     * Initialises the GUI with startup state
     */
    private void initGUI() {
        this.frame = this.createFrame();
        this.addSpace();

        // Create message label
        this.messL = new JLabel(this.message);
        this.messL.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.frame.getContentPane().add(this.messL);

        // Create text input field
        this.tf = new JTextField("Input values here. Items like: count,barcode e.g. 5,123456");
        this.frame.getContentPane().add(this.tf);

        this.createButton("Start sale", "start");
        this.createButton("Scan", "scan");
        this.createButton("Get Discounts", "discount");
        this.createButton("End Sale", "end");
        this.createButton("Input payment", "pay");
        this.createButton("Exit", "exit");

        this.renderFrame();
    }

    /**
     * Create the frame with defaults we want
     * 
     * @return the newly created default frame
     */
    private JFrame createFrame() {
        JFrame frame = new JFrame(this.title);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        return frame;
    }

    /**
     * Adds space to the frame
     * Will not work if frame not already set
     */
    private void addSpace() {
        this.frame.getContentPane().add(Box.createVerticalStrut(10));
    }

    /**
     * Creates a button with a text and a linked command
     * 
     * @param text    the text on the button
     * @param command the command string to look for in action handler
     */
    private void createButton(String text, String command) {
        JButton b = new JButton(text);
        b.setActionCommand(command);
        b.addActionListener(this);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.frame.getContentPane().add(b);
        this.addSpace();
    }

    /**
     * Render the views frame
     */
    private void renderFrame() {
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    /**
     * Updates the ui based on a ViewDTO
     * 
     * @param props the ViewDTO props to show
     */
    private void updateUI(ViewDTO props) {
        switch (props.getType()) {
            case StartSale:
                this.updateMessage("Started new sale.");
                break;
            case StartScan:
                if (props.getSuccess()) {
                    if (props.getData().isPresent()) {
                        this.updateMessage(String.format("Item %s added to sale.", props.getData().get()));
                    } else {
                        this.updateMessage("Item was already in sale, has been incremented.");
                    }
                } else {
                    this.updateMessage("Item does not exist in inventory.");
                }
                break;
            case GetDiscounts:
                if (props.getData().isPresent()) {
                    this.updateMessage("Applied all available discounts.");
                } else {
                    this.updateMessage("No discounts found for that customer.");
                }
                break;
            case EndSale:
                this.updateMessage(String.format("The total is %s.", props.getData().get()));
                break;
            case InputPayment:
                if (props.getSuccess()) {
                    if (props.getData().isPresent()) {
                        this.updateMessage(String.format("Your change is %s.", props.getData().get()));
                    } else {
                        this.updateMessage("No change.");
                    }
                } else {
                    this.updateMessage(String.format("Left to pay: %s", props.getData().get()));
                }
                break;
        }
    }

    /**
     * Updates the message text on the view
     * 
     * @param text the text to show
     */
    private void updateMessage(String text) {
        this.messL.setText(text);
    }
}
