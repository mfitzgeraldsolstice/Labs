/******************************************************************************
 *    Author: Margaret Fitzgerald
 *    File: Main
 *    Date: 7/22/2018
 *    Description: This file holds the Stock bean which provides the
 *                   structure for the data inputted from the db.
 *****************************************************************************/

package com.solstice.javatraining.beans;

import java.sql.Timestamp;


public class Stock {

    private String symbol;
    private double price;
    private int volume;
    private Timestamp date;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getSymbol() {

        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String toString(){
        return ("Symbol: " + this.symbol +
                " Price: " + this.price +
                " Volume: " + this.volume +
                " Date: " + this.date);
    }
}
