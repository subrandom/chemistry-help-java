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
        ArrayList<Element> elementsToShow;
        
        commands.addAll(Arrays.asList(commandEntered.split(" ")));
        
        if(commands.get(0).equals("/?")) {
            commands.remove(0);
            
            //a maximum of 5 elements will be displayed
            if (commands.size() > 5) { commands.removeAll(commands.subList(5, commands.size()));}
            
            elementsToShow = validateElementsEntered(commands);
            
            if(elementsToShow.isEmpty()) {
                String errorString = "No valid elements enterd. Enter elements by name or symbol,"
                    + " example: Helium or He";
                showGeneralError(errorString); 
            } else {
                ScreenController.showElementInfoWindow(elementsToShow);
            }
        }
        else {
            showBadCommandError(commands.get(0));
        }
    }
       
    private static ArrayList<Element> validateElementsEntered(ArrayList<String> elementsIn) {
        ArrayList<Element> elementsToShow = new ArrayList<>();
        
                //validate elements & load into ArrayList
        elementsIn.stream().forEach((e) -> {
            Element el = Elements.isValidElement(e);
            if(el == null) {
                
            }else {
                elementsToShow.add(Elements.isValidElement(e));
            }
        });
        return elementsToShow;
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
