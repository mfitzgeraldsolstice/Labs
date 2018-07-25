/******************************************************************************
*    Author: Margaret Fitzgerald
*    File: Main
*    Date: 7/22/2018
*    Description: This file manages output for user options and calls methods
*                 perform the operations for this project.
 *****************************************************************************/

package com.solstice.javatraining.db;

import com.solstice.javatraining.util.InputHelper;
import org.nd4j.shade.jackson.core.JsonParseException;
import org.nd4j.shade.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import static com.solstice.javatraining.db.AdminManager.*;

public class Main {

    /**
     * Calls functions to connect to database, get user input,
     *      call queries and display results
     * @param args
     * @return void
     */

    public static void main (String [] args) throws SQLException {
        Connection conn = null;

        try {
            conn = DBUtil.getConnection(DBType.MYSQL);
            System.out.println("Connected!");

            //Calls function to create and populate table
            DBUtil.manageDatabase(conn);

            //Beginning of output for user choice and input handling

            Integer userChoice = printUserOptions();
            String date;

            while(userChoice != 5){

                switch (userChoice){
                    case 1:
                        date = InputHelper.getInput("Enter the date (YYYY-MM-DD): ");
                        highestPriceByDate(date);
                        break;
                    case 2:
                        date = InputHelper.getInput("Enter the date (YYYY-MM-DD): ");
                        lowestPriceByDate(date);
                        break;
                    case 3:
                        date = InputHelper.getInput("Enter the date (YYYY-MM-DD): ");
                        totalVolumeByDate(date);
                        break;
                    case 4:
                        date = InputHelper.getInput("Enter the date (YYYY-MM-DD): ");
                        closingPriceByDate(date);
                        break;
                    default:
                        System.out.println("Incorrect number choice. Please try again.");
                        break;

                }

                userChoice = printUserOptions();
            }


        } catch (SQLException e) {
            System.err.println(e);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /******************************************************************************
     *    Method Name: printUserOptions
     *    Date: 7/22/2018
     *    Description: Outputs the user options for running the program
     *****************************************************************************/

    /**
     * Outputs the user options for running the program
     * @param
     * @return Integer
     */

    public static Integer printUserOptions(){

        System.out.println();
        System.out.println("(1) Get highest price for a specific date");
        System.out.println("(2) Get lowest price for a specific date");
        System.out.println("(3) Get total volume for a specific date");
        System.out.println("(4) Get closing price for a specific date");
        System.out.println("(5) Quit");

        Integer userChoice = InputHelper.getIntegerInput("Choose a number option " +
                "above by entering that number: ");

        return userChoice;
    }

}
