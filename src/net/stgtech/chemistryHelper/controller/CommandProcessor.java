package net.stgtech.chemistryHelper.controller;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.control.Alert;
import net.stgtech.chemistryHelper.model.Element;
import net.stgtech.chemistryHelper.model.Elements;

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
    
    private static boolean hasUnmatchedParentheses(String expression) {
        int countOpenParenthesis = 0;
        int countCloseParenthesis = 0;
        
        for (char c : expression.toCharArray()) {
            if(isOpenParenthesis(c)) {
                countOpenParenthesis++;
                continue;
            }
            
            if(isCloseParenthesis(c)) {
                countCloseParenthesis++;
            }
        }
        
        if (countOpenParenthesis != countCloseParenthesis) {
            return true;
        }
        
        return false;
    }
    
    private static void executeAtomicWeightCommand(ArrayList<String> arrayList) {      
        //make a single string from the arraylist
        String expression = arrayList.toString().substring(1, arrayList.toString().length()-1);

        //eat spaces and commas
        expression = expression.replaceAll("[\\s,]", "");
       
        //normalize the expression
        System.out.println(expression);
        expression = expression.replaceAll("([A-Z][a-z])([1-9])", "$1 * $2"); //space out double-letter elements
        System.out.println(expression);
        expression = expression.replaceAll("([A-Z]{1})([1-9])", "$1 * $2"); //space out numbers that follow single-letter elements
        System.out.println(expression);
        expression = expression.replaceAll("([A-Z])([A-Z])", "$1 + $2"); //space out any two single-letter elements next to each other
        System.out.println(expression);
        expression = expression.replaceAll("([0-9])([A-Z])", "$1 * $2"); //add multiply between any numbers followed by elements
        System.out.println(expression);
        expression = expression.replaceAll("([0-9]+)([\\+\\-\\*])", "$1 $2"); //space out any numbers followed by operataors
        System.out.println(expression);
        expression = expression.replaceAll("([\\+\\-\\*\\^\\(\\)]?)([0-9]+)([\\+\\-\\*\\^\\(\\)]?)", "$1 $2 $3"); //space out any operator-Number-operator groups
        System.out.println(expression);
        expression = expression.replaceAll("([\\+\\-\\*\\^\\(\\)])([A-Z])", "$1 $2"); //space out any operator-element groups
        System.out.println(expression);
        expression = expression.replaceAll("([A-Z])([\\+\\-\\*\\^\\(\\)])", "$1 $2"); //space out any element-operator groups
        System.out.println(expression);
        expression = expression.replaceAll("(\\))(\\S)", "$1 $2"); //space out any closed parenthesis with anything after it
        System.out.println(expression);
        expression = expression.replaceAll("(\\s{2,})", " "); //remove any double spaces
        System.out.println(expression);
    }
    
    
    
    private static boolean isUpperCaseLetter(char c) {
        return Character.isAlphabetic(c) && Character.isUpperCase(c);
    }
    
    private static boolean isLowerCaseLetter(char c) {
        return Character.isAlphabetic(c) && Character.isLowerCase(c);
    }
    
    private static boolean isNumber(char c) {
        return Character.isDigit(c);
    }
    
    private static boolean isOpenParenthesis(char c) {
        return c == '(';
    }
    
    private static boolean isCloseParenthesis(char c) {
        return c == ')';
    }
    
    private static boolean isOperator(char c) {
        return c == '^' || c == '+' || c == '*';
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
