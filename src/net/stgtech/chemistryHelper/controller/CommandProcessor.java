/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.stgtech.chemistryHelper.controller;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Platform;
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
        
        if(hasUnmatchedParentheses(expression)) {
            showGeneralError("Bad formula: open/close parenthesis mis-match");
            return;
        }
       
        StringBuilder equation = new StringBuilder();
        char previousChar;
        char currentChar = '*';
        int errorCode = 0;
        
        for(int i = 0; i < expression.length(); i++) {
            currentChar = expression.charAt(i);
            previousChar = (i == 0 ? '#' : expression.charAt(i -1));
            
            if(previousChar == '#') {
                if(isOperator(currentChar) || isCloseParenthesis(currentChar) || isLowerCaseLetter(currentChar)) {
                    errorCode = 120;
                    break;
                }
            }
            
            if(isLowerCaseLetter(currentChar)) {
                if(isUpperCaseLetter(previousChar)) {
                    equation.append(currentChar);
                    continue;
                } else {
                    errorCode = 100;
                    break;
                }
            }
            
            if(isNumber(currentChar)) {
                if(previousChar == '#') {
                    equation.append(currentChar);
                    continue;
                }
                
                if(isNumber(previousChar)) {
                    equation.append(currentChar);
                    continue;
                }
                
                if(isCloseParenthesis(previousChar)) {
                    equation.append(" * ").append(currentChar);
                    continue;
                }
                
                if (isOpenParenthesis(previousChar)) {
                    equation.append(currentChar);
                    continue;
                }
                
                if(isUpperCaseLetter(previousChar) || isLowerCaseLetter(previousChar)) {
                    equation.append(" * ").append(currentChar);
                    continue;
                }
                
                if(isOperator(previousChar)) {
                    equation.append(" ").append(currentChar);
                    continue;
                }
                
                System.err.println("Something bad happened. Character being processed = " + currentChar);
                Platform.exit();
            }
            
            if(isOperator(currentChar)) {
                if(isOperator(previousChar)) {
                    errorCode = 110;
                    break;
                }
                
                if(isOpenParenthesis(previousChar)) {
                    errorCode = 140;
                    break;
                }
                
                equation.append(" ").append(currentChar);
                continue;
                
            }
            
            if(isUpperCaseLetter(currentChar)) {
                char nextChar;
                String elementValue;
                String elementSymbol = "";
                
                if(i != expression.length() - 1) {
                    nextChar  = expression.charAt(i + 1);
                    if(isLowerCaseLetter(nextChar)) {
                        elementSymbol = String.valueOf(currentChar) + String.valueOf(nextChar);
                        i++; //move the index past the lower-case letter
                    } else {
                        elementSymbol = String.valueOf(currentChar);
                    }     
                } else {
                    elementSymbol = String.valueOf(currentChar);
                }

                if(Elements.isValidElement(elementSymbol)) {
                    elementValue = Elements.getAtomicMassString(elementSymbol);
                } else {
                    errorCode = 150;
                    break;
                }
                
                if(previousChar == '#') {
                    equation.append(elementValue);
                    continue;
                }
                
                if(isOperator(previousChar)) {
                    equation.append(" ").append(elementValue);
                    continue;
                }

                if(isCloseParenthesis(previousChar)) {
                    equation.append(" * ").append(elementValue);
                    continue;
                }

                if(isOpenParenthesis(previousChar)) {
                    equation.append(" ").append(elementValue);
                    continue;
                }

                if(isLowerCaseLetter(previousChar) || isUpperCaseLetter(previousChar)) {
                    equation.append(" + ").append(elementValue);
                    continue;
                }

                if(isNumber(previousChar)) {
                    equation.append(" + ").append(elementValue);
                    continue;
                }

                System.err.println("Something bad happened. Character being processed = " + currentChar);
                Platform.exit();
            }
            
            if(isOpenParenthesis(currentChar)) {
                if(previousChar == '#') {
                    equation.append("(");
                    continue;
                }
                
                if(isOperator(previousChar)) {
                    equation.append(" (");
                    continue;
                }
                
                equation.append(" * ").append(currentChar);
                continue;
            }
            
            if(isCloseParenthesis(currentChar)) {
                if(isOperator(previousChar)) {
                    errorCode = 130;
                    break;
                }
                
                equation.append(" )");
                continue;
            }
        }
        
        if(errorCode != 0) {
            switch(errorCode) {
                case 100:
                    showGeneralError("Bad formula: lower case letter not preceded by uppercase letter");
                    break;
                case 110:
                    showGeneralError("Bad formula: two operators in a row, like '+ +'");
                    break;
                case 120:
                    showGeneralError("Bad formula: illegal first character. Must be number or symbol");
                    break;
                case 130:
                    showGeneralError("Bad formula: close parenthesis after an operator, like +), is illogicial");
                    break;
                case 140:
                    showGeneralError("Bad formula: operator after open parenthesis, like (+, is illogical");
                    break;
                case 150:
                    showGeneralError("Bad formula: invalid element symbol in equation");
                    break;
            }
        }
        
        System.out.println(equation.toString());
   
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
