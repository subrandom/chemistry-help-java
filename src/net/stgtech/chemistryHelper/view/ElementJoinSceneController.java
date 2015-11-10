/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.stgtech.chemistryHelper.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import net.stgtech.chemistryHelper.model.Elements;

/**
 * FXML Controller class
 *
 * @author stephen.bradley
 */
public class ElementJoinSceneController implements Initializable {
    private AnchorPane myWindow;
    
    @FXML
    private Label elementOneNameLabel;
    @FXML
    private Label elementTwoNameLabel;
    @FXML
    private Label elementOneOxidationStatesLabel;
    @FXML
    private Label elementTwoOxidationStatesLabel;
    @FXML
    private Label results;
    
    /* there isn't a good way to print superscripts into labels, so I'm using the
    unicode numbers and storing them in an integer array, so I can us them later.
    */
    private static final String [] SuperScript = {"\u207B", "\u00B9","\u00B2", "\u00B3", "\u2074", "\u2075","\u2076", "\u2077"};
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    

    public void showJoinResult(ArrayList<Elements> elementsToShow, AnchorPane elementWindow, boolean match, int valueOne, int valueTwo) {
        this.myWindow = elementWindow;
        
        //what were the element names & symbols
        elementOneNameLabel.setText(elementsToShow.get(0).getElementName() + " (" + elementsToShow.get(0).getElementSymbol() + ")");
        elementTwoNameLabel.setText(elementsToShow.get(1).getElementName() + " (" + elementsToShow.get(1).getElementSymbol() + ")");
        
        //what were the oxidation states?
        elementOneOxidationStatesLabel.setText(elementsToShow.get(0).getOxidationLevelsAsString());
        elementTwoOxidationStatesLabel.setText(elementsToShow.get(1).getOxidationLevelsAsString());
        
        //get integer values of the last oxidation value
        
        //what was the result?
        if(match == false) {
            results.setText("Not joinable");
        }
        else {
            String strResult;
            System.out.println(valueOne + "," + valueTwo);
            
            if(valueOne > 0) {
                
                strResult = elementsToShow.get(0).getElementSymbol() + SuperScript[valueTwo] + " " + 
                        elementsToShow.get(1).getElementSymbol() + SuperScript[0] + SuperScript[valueTwo];
            } else {
                strResult = elementsToShow.get(1).getElementSymbol() + SuperScript[valueOne] + " " + 
                        elementsToShow.get(0).getElementSymbol() + SuperScript[0] + SuperScript[valueOne];
            } 
            
            results.setText(strResult);
        }
    }
}
