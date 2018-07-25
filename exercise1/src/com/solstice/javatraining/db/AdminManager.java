/******************************************************************************
 *    Author: Margaret Fitzgerald
 *    File: Main
 *    Date: 7/22/2018
 *    Description: This file contains the functions for performing each of
 *                  the 4 query options.
 *****************************************************************************/

package com.solstice.javatraining.db;

import com.solstice.javatraining.beans.Stock;

import java.sql.*;

public class AdminManager {

    /**
     * Returns the stock with the highest price based on either a
     *      day or month within a year, entered by the user.
     * @param date
     * @return void
     */

    public static void highestPriceByDate(String date) throws SQLException {
        String[]checkDate = date.split("-");
        String daySQL = "SELECT symbol, MAX(price), volume, date FROM stocks WHERE DATE(date) = ?";
        String monthSQL = "SELECT symbol, MAX(price), volume, date FROM stocks WHERE YEAR(DATE(date)) = ? AND MONTH(DATE(date)) = ?";
        ResultSet rs;

        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement monthStmt = conn.prepareStatement(monthSQL);
                PreparedStatement dayStmt = conn.prepareStatement(daySQL);
        ) {
            if (checkDate.length > 2){
                rs = executeQuery(date, checkDate, dayStmt);
            }
            else{
                rs = executeQuery(date, checkDate, monthStmt);
            }

            while (rs.next()){
                Stock bean = new Stock();
                bean.setSymbol(rs.getString("symbol"));
                bean.setPrice(rs.getDouble("MAX(price)"));
                bean.setVolume(rs.getInt("volume"));
                bean.setDate(rs.getTimestamp("date"));
                System.out.println("Lowest Price: " + bean.getPrice());
                System.out.println("Stock information: " + bean);
            }

        }

    }

    /**
     * Returns the stock with the lowest price based on either a
     *      day or month within a year, entered by the user.
     * @param date
     * @return void
     */

    public static void lowestPriceByDate(String date) throws SQLException{
        String[]checkDate = date.split("-");
        String daySQL = "SELECT symbol, MIN(price), volume, date FROM stocks WHERE DATE(date) = ?";
        String monthSQL = "SELECT symbol, MIN(price), volume, date FROM stocks WHERE YEAR(DATE(date)) = ? AND MONTH(DATE(date)) = ?";
        ResultSet rs;

        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement monthStmt = conn.prepareStatement(monthSQL);
                PreparedStatement dayStmt = conn.prepareStatement(daySQL);
        ) {
            if (checkDate.length > 2){
                rs = executeQuery(date, checkDate, dayStmt);
            }
            else{
                rs = executeQuery(date, checkDate, monthStmt);
            }

            while (rs.next()) {
                Stock bean = new Stock();
                bean.setSymbol(rs.getString("symbol"));
                bean.setPrice(rs.getDouble("MIN(price)"));
                bean.setVolume(rs.getInt("volume"));
                bean.setDate(rs.getTimestamp("date"));
                System.out.println("Lowest Price: " + bean.getPrice());
                System.out.println("Stock information: " + bean);
            }

        }
    }

    /**
     * Returns sum of the volumes of stocks based on either a
     *      specific day or month within a year, entered by the user.
     * @param date
     * @return void
     */

    public static void totalVolumeByDate(String date) throws SQLException{
        String[]checkDate = date.split("-");
        String daySQL = "SELECT SUM(volume) FROM stocks WHERE DATE(date) = ?";
        String monthSQL = "SELECT SUM(volume) FROM stocks WHERE YEAR(DATE(date)) = ? AND MONTH(DATE(date)) = ?";
        ResultSet rs;


        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement monthStmt = conn.prepareStatement(monthSQL);
                PreparedStatement dayStmt = conn.prepareStatement(daySQL);
        ) {

            if (checkDate.length > 2){
                rs = executeQuery(date, checkDate, dayStmt);
            }
            else{
                rs = executeQuery(date, checkDate, monthStmt);
            }

            while (rs.next()) {
                Stock bean = new Stock();
                bean.setVolume(rs.getInt("SUM(volume)"));
                System.out.println("Volume for " + date + ": " + bean.getVolume());
            }

        }
    }

    /**
     * Returns the closing price of stock based on either a
     *      day or month within a year, entered by the user.
     * @param date
     * @return void
     */

    public static void closingPriceByDate(String date) throws SQLException{
        String[]checkDate = date.split("-");
        String daySQL = "SELECT symbol, price, volume, MAX(date) FROM stocks WHERE DATE(date) = ?";
        String monthSQL = "SELECT symbol, price, volume, MAX(date) FROM stocks WHERE YEAR(DATE(date)) = ? AND MONTH(DATE(date)) = ?";
        ResultSet rs;

        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement monthStmt = conn.prepareStatement(monthSQL);
                PreparedStatement dayStmt = conn.prepareStatement(daySQL);
        ) {

            if (checkDate.length > 2){
                rs = executeQuery(date, checkDate, dayStmt);
            }
            else{
                rs = executeQuery(date, checkDate, monthStmt);
            }

            while (rs.next()) {
                Stock bean = new Stock();
                bean.setSymbol(rs.getString("symbol"));
                bean.setPrice(rs.getDouble("price"));
                bean.setVolume(rs.getInt("volume"));
                bean.setDate(rs.getTimestamp("MAX(date)"));
                System.out.println("Closing price: " + bean.getPrice());
                System.out.println("Stock information: " + bean);
            }

        }
    }

    /**
     * Contains the logic to determine if the user entered a
     *      month or a date to perform a query on.
     * @param input, checkDate, ps
     * @return void
     */

    public static ResultSet executeQuery(String input, String[] checkDate, PreparedStatement ps) throws SQLException {
        ResultSet rs = null;
        if (checkDate.length > 2){
            ps.setString(1, input);
            rs = ps.executeQuery();
        }
        else{
            ps.setString(1, checkDate[0]);
            ps.setString(2, checkDate[1]);
            rs = ps.executeQuery();
        }

        return rs;
    }
}
