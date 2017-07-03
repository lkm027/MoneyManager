package com.example.lucas.moneymanager.classes;

import com.example.lucas.moneymanager.R;

/**
 * Created by Lucas on 6/30/2017.
 */

public class Item {

    private String name;
    private float amount;

    public Item(String name, float amount) {
        this.name = name;
        this.amount = amount;
    }

    /**
     * Getter for our item name
     * @return item
     */
    public String getItemDesc() {
        return name;
    }

    /**
     * Getter for our item amount
     * @return amount
     */
    public float getAmount() {
        return amount;
    }

    /**
     * Getter for our dollar amount
     * @return amount
     */
    public String getDollarAmount() {
        if (amount < 0) {
            return String.format("- $%.02f", amount * -1);
        }
        return String.format("$%.02f", amount);
    }

}
