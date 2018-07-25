/******************************************************************************
 *    Author: Margaret Fitzgerald
 *    File: Main
 *    Date: 7/22/2018
 *    Description: This file creates a connection to the database.
 *****************************************************************************/

package com.solstice.javatraining.db;

import com.solstice.javatraining.beans.Stock;
import org.nd4j.shade.jackson.databind.ObjectMapper;
import org.nd4j.shade.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DBUtil {

    private static final String USERNAME = "dbuser";
    private static final String PASSWORD = "dbpassword";
    private static final String M_CONN_STRING =
            "jdbc:mysql://localhost/exerciseone?serverTimezone=UTC";


    /**
     * Establishes connection to database
     * @param dbType
     * @return Connection
     */

    public static Connection getConnection(DBType dbType) throws SQLException {

        switch (dbType) {
            case MYSQL:
                return DriverManager.getConnection(M_CONN_STRING, USERNAME, PASSWORD);
            default:
                return null;
        }

    }


    /**
     * Handles SQL Exceptions
     * @param e
     * @return void
     */

    public static void processException(SQLException e) {
        System.err.println("Error message: " + e.getMessage());
        System.err.println("Error code: " + e.getErrorCode());
        System.err.println("SQL state: " + e.getSQLState());
    }


    /**
     * Handles deletion, creation and insert into database
     * @param conn
     * @return void
     */

    public static void manageDatabase(Connection conn) throws IOException, SQLException {

        //Get data from the URL
        ObjectMapper objectMapper = new ObjectMapper();
        URL url = new URL("https://bootcamp-training-files.cfapps.io/week1/week1-stocks.json");
        TypeFactory typeFactory = objectMapper.getTypeFactory();

        //Store data in a list of the bean, Stock
        List<Stock> stocks = objectMapper.readValue(url,
                typeFactory.constructCollectionType(List.class, Stock.class));

        //Table management: Drop the table if it exists, otherwise create it and insert data
        String dropQuery = "DROP TABLE IF EXISTS stocks";
        String createQuery = "CREATE TABLE stocks (symbol VARCHAR(30), price DOUBLE, " +
                "volume INT(11), date TIMESTAMP)";
        String insertQuery = "INSERT INTO stocks (symbol, price, volume, date) VALUES (?, ?, ?, ?)";

        //Create prepared statements and execute the SQL queries
        PreparedStatement drop, create, insert;
        drop = conn.prepareStatement(dropQuery);
        drop.execute();
        create = conn.prepareStatement(createQuery);
        create.execute();
        insert = conn.prepareStatement(insertQuery);

        //Loops through each row of data returned and inserts into the table, executing after each row
        for (Stock stock: stocks){
            insert.setString(1, stock.getSymbol());
            insert.setDouble(2, stock.getPrice());
            insert.setInt(3, stock.getVolume());
            insert.setTimestamp(4, stock.getDate());
            insert.execute();
        }
    }

}

