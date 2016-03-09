/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.stgtech.chemistryHelper.view;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
import net.stgtech.chemistryHelper.ChemistryHelper;
import net.stgtech.chemistryHelper.model.Element;

/**
 *
 * @author stephen.bradley
 */
public class ElementInfoScene {
    
    public ElementInfoScene(ArrayList<Element> elementsToShow) throws IOException {
        AnchorPane elementInfoPane = new AnchorPane();
        ElementInfoSceneController ewController;
        Stage elementInfoStage = new Stage();
    
        FXMLLoader elementWindowLoader = new FXMLLoader();
        elementWindowLoader.setLocation(ChemistryHelper.class.getResource("view/ElementInfoScene.fxml"));
        
        try {
            elementInfoPane = elementWindowLoader.load();
            ewController = elementWindowLoader.getController();
            
            Image image = new Image("net/stgtech/chemistryHelper/data/background.png",1600,800, false, false);
            BackgroundPosition position = new BackgroundPosition(Side.LEFT, 0, true, Side.TOP, 0, true);
            BackgroundSize bs = new BackgroundSize(100, 100, true, true, false, true);
            BackgroundImage bi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, position, null); 
            
            elementInfoPane.setBackground(new Background(bi));
         
            elementInfoStage.setScene(new Scene(elementInfoPane));
            //elementStage.initStyle(StageStyle.UTILITY);
            elementInfoStage.setTitle("Element information display");   
            
            ewController.showElementInfo(elementsToShow);
            elementInfoStage.show();           
            
            }
        catch (IOException iox) {
            throw (new IOException(iox));
        }   
    }    
}
