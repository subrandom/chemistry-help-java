/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.stgtech.chemistryHelper.controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import net.stgtech.chemistryHelper.ChemistryHelper;
import net.stgtech.chemistryHelper.model.Element;
import net.stgtech.chemistryHelper.view.ElementInfoSceneController;
import net.stgtech.chemistryHelper.view.MainWindowController;

/**
 *
 * @author Stephen.Bradley
 */
public class ScreenController {
    private static Stage primaryStage;
    private static Stage elementInfoStage;
    private static Stage helpStage = new Stage();
    private static Pane mainWindowPane;
    private static AnchorPane elementInfoPane, helpWindowPane;
    private static MainWindowController mwController;
    private static ElementInfoSceneController ewController;
    private static Dimension screenSize;
    private static Double maxWidth;
    
    public static void setStage(Stage stage) {
        ScreenController.primaryStage = stage;
    }
    
    public static void initializeApplicationWindows() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        maxWidth = (screenSize.getWidth()* .25 >= 700 ? screenSize.getWidth() *.25 : 700);
        
        initializeMainWindow();
        initializeHelpWindow();
        elementInfoStage = new Stage();
    }
    
    public static void showMainWindow() {
        primaryStage.show();
    }
    
    public static void showHelpWindow() {
        helpStage.show();
    }
    
    private static void initializeMainWindow() {
          
        try {
            FXMLLoader mainWindowLoader = new FXMLLoader();
            mainWindowLoader.setLocation(ChemistryHelper.class.getResource("view/MainWindow.fxml"));
            
            //set the size of our stage
            primaryStage.setWidth(maxWidth);
            primaryStage.setMaxWidth(maxWidth);
 
            mainWindowPane = mainWindowLoader.load();
            mainWindowPane.setMaxWidth(maxWidth);
            mainWindowPane.setPrefWidth(maxWidth);
            
            mwController = mainWindowLoader.getController();

            //set an image for our window background
            Image image = new Image("net/stgtech/chemistryHelper/data/background.png",700,150, false, true);
            BackgroundPosition position = new BackgroundPosition(Side.LEFT, 0, true, Side.TOP, 0, true);
            BackgroundSize bs = new BackgroundSize(700, 150, false, false, false, false);
            BackgroundImage bi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, position, bs); 
            mainWindowPane.setBackground(new Background(bi));
     
            primaryStage.setScene(new Scene(mainWindowPane));
            primaryStage.setTitle("Chemistry Helper");
            primaryStage.setResizable(false);
            //primaryStage.initStyle(StageStyle.UTILITY);
            primaryStage.setOnCloseRequest((WindowEvent event) -> {
                Platform.exit();
            });
   
            primaryStage.setX((screenSize.getWidth() * .5) - maxWidth/2);
            primaryStage.setY(10);
            
        } catch (IOException ex) {
            showErrorWindow("Main Window");
        }
    }
    
    private static void initializeHelpWindow() {
        try {
            FXMLLoader helpWindowLoader = new FXMLLoader();
            helpWindowLoader.setLocation(ChemistryHelper.class.getResource("view/HelpWindow.fxml"));
            
            //set the size of our stage
            helpStage.setWidth(600);
            helpStage.setMaxWidth(700);
            helpStage.setHeight(500);
            helpStage.setMaxHeight(500);
 
            helpWindowPane = helpWindowLoader.load();
            helpWindowPane.setMaxWidth(600);
            helpWindowPane.setPrefWidth(700);

            //set an image for our window background
            Image image = new Image("net/stgtech/chemistryHelper/data/background.png",700,500, false, true);
            BackgroundPosition position = new BackgroundPosition(Side.LEFT, 0, true, Side.TOP, 0, true);
            BackgroundSize bs = new BackgroundSize(700, 500, false, false, false, false);
            BackgroundImage bi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, position, bs); 
            helpWindowPane.setBackground(new Background(bi));
     
            helpStage.setScene(new Scene(helpWindowPane));
            helpStage.setTitle("Chemistry Helper Help");
            helpStage.setResizable(true);
            helpStage.initStyle(StageStyle.UTILITY);
  
            
        } catch (IOException ex) {
            showErrorWindow("Help Window");
        }
    }
    
    public static void showElementInfoWindow(ArrayList<Element> elementsToShow) {
        
        if (elementInfoStage.isShowing()) {
            elementInfoStage.close();
        }
        
        FXMLLoader elementWindowLoader = new FXMLLoader();
        elementWindowLoader.setLocation(ChemistryHelper.class.getResource("view/ElementInfoScene.fxml"));
        
        try {
            elementInfoPane = elementWindowLoader.load();
            ewController = elementWindowLoader.getController();
            
            Image image = new Image("net/stgtech/chemistryHelper/data/background.png",1200,700, true, false);
            BackgroundPosition position = new BackgroundPosition(Side.LEFT, 0, true, Side.TOP, 0, true);
            BackgroundSize bs = new BackgroundSize(1200, 700, false, false, false, true);
            BackgroundImage bi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, position, null); 
            
            elementInfoPane.setBackground(new Background(bi));
         
            elementInfoStage.setScene(new Scene(elementInfoPane));
            //elementStage.initStyle(StageStyle.UTILITY);
            elementInfoStage.setTitle("Element information display");   
            
            ewController.showElementInfo(elementsToShow);
            elementInfoStage.show();           
            
            }
        catch (IOException iox) {
            showErrorWindow("Element Information Window");
        } 
    }
    
    public static void closeElementInfoWindow() {
        elementInfoStage.close();
    }
    private static void initializeMolecularWeightWindow() {
        
    }   
    
    private static void showErrorWindow(String windowInError) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("FX Load Error");
    alert.setHeaderText(null);
    alert.setContentText("Unable to load " + windowInError);
    alert.showAndWait();
    Platform.exit();
    }
}
