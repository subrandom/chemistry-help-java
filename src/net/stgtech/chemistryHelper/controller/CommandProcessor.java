/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.stgtech.chemistryHelper.controller;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.control.Alert;
import net.stgtech.chemistryHelper.model.Element;
import net.stgtech.chemistryHelper.model.Elements;

/**
 *
 * @author Stephen.Bradley
 */
public class CommandProcessor {   
    public static void parseCommandEntry(String commandEntered) {
        ArrayList<String> commands = new ArrayList<>();
        
        commands.addAll(Arrays.asList(commandEntered.split(" ")));
        
        if(commands.get(0).equals("?")) {
            commands.remove(0);
            executeShowCommand(commands);
        } else if(commands.get(0).equals("am")) {
            commands.remove(0);
            executeAtomicWeightCommand(commands);
        } else if (commands.get(0).equals("/help")) {
            ScreenController.showHelpWindow();
        } else {
            showBadCommandError(commands.get(0));
        }
    }
    
    private static void executeAtomicWeightCommand(ArrayList<String> arrayList) {
        //make a single string from the arraylist
        String expression = arrayList.toString().substring(1, arrayList.toString().length()-1);

        //eat spaces and commas
        expression = expression.replaceAll("[\\s,]", "")    ;

        //create a valid math equation while also checking the validity of the element symbols
        StringBuilder equation = new StringBuilder();
        char previousChar, currentChar, nextChar;
        boolean errorFlag = false;

        for(int i = 0; i < expression.length(); i++) {
            currentChar = expression.charAt(i);
            nextChar = expression.charAt(i+1);

        }
    }
    
    private static boolean isUpperCaseLetter(char c) {
            
        return true;
    }
    
    private static boolean isLowerCaseLetter(char c) {
        
        return true;
    }
    
    private static boolean isNumber(char c) {
        return true;
    }
    
    
       
    private static void executeShowCommand(ArrayList<String> elements) {
            ArrayList<Element> elementsToShow = new ArrayList<>();
            //a maximum of 5 elements will be displayed
            if (elements.size() > 5) { elements.removeAll(elements.subList(5, elements.size()));}

            elements.stream().forEach((e) -> {
                if(Elements.isValidElement(e)) {
                    elementsToShow.add(Elements.getElementBySymbol(e));
                }
        });
            
            if(elementsToShow.isEmpty()) {
                String errorString = "No valid elements enterd. Enter elements by name or symbol,"
                    + " example: Helium or He";
                showGeneralError(errorString); 
            } else {
                ScreenController.showElementInfoWindow(elementsToShow);
            }
    }
    
    private static void showBadCommandError(String badCommandString) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Bad Command");
        alert.setHeaderText(null);
        alert.setContentText(badCommandString + " is not a valid command. For a list of commands click the question mark at the "
                + "bottom of the window or type /help");
        alert.showAndWait();
    }
    
    private static void showGeneralError(String errorString) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setContentText(errorString);
        alert.setHeaderText(null);
        alert.showAndWait();       
    }
}
