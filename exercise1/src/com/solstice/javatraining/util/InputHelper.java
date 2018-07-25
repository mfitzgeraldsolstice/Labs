/******************************************************************************
 *    Author: Margaret Fitzgerald
 *    File: Main
 *    Date: 7/22/2018
 *    Description: This file handles user input and includes error handling.
 *****************************************************************************/

package com.solstice.javatraining.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InputHelper {

    /**
     * Given a prompt, this method handles String input from the user.
     * @param prompt
     * @return String
     */

    public static String getInput(String prompt) {
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.print(prompt);
        System.out.flush();

        try {
            return stdin.readLine();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Given a prompt, this method handles Integer input from the user.
     * @param prompt
     * @return int
     */

    public static int getIntegerInput(String prompt){
        String input = null;
        Integer result = 0;
        try{
            input = getInput(prompt);
            result = Integer.parseInt(input);
        }catch (NumberFormatException e){
        }
        return result;
    }

}
