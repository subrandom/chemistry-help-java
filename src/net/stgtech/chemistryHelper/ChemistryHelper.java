
package net.stgtech.chemistryHelper;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.stgtech.chemistryHelper.model.Elements;
import net.stgtech.chemistryHelper.view.ElementInfoSceneController;
import net.stgtech.chemistryHelper.view.ElementJoinSceneController;
import net.stgtech.chemistryHelper.view.MainWindowController;

public class ChemistryHelper extends Application {
    
    private final ArrayList<Elements> elements = new ArrayList<>();
    private Stage primaryStage;
    private final ChemistryHelper mainApp = this;
    private MainWindowController mwController;
    private Label errorLabel;
    
    public static void main(String[] args) {
        launch(args);
    }
    

    @Override
    public void start(Stage primaryStage) {
        setUserAgentStylesheet(STYLESHEET_MODENA);
        this.primaryStage = primaryStage;
        
        createMainWindow();
        
        doInit();   
    }

    private void createMainWindow() {
        Pane mainWindow;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Double maxWidth = (screenSize.getWidth()* .25 >= 700 ? screenSize.getWidth() *.25 : 700);
        
        try {
            FXMLLoader mainWindowLoader = new FXMLLoader();
            mainWindowLoader.setLocation(ChemistryHelper.class.getResource("view/MainWindow.fxml"));
            
            //set the size of our stage
            primaryStage.setWidth(maxWidth);
            primaryStage.setMaxWidth(maxWidth);
 
            mainWindow = mainWindowLoader.load();
            mainWindow.setMaxWidth(maxWidth);
            mainWindow.setPrefWidth(maxWidth);
            
            mwController = mainWindowLoader.getController();
            mwController.run(mainApp);
            //set an image for our window background
            Image image = new Image("net/stgtech/chemistryHelper/data/background.png",700,150, false, true);
            BackgroundPosition position = new BackgroundPosition(Side.LEFT, 0, true, Side.TOP, 0, true);
            BackgroundSize bs = new BackgroundSize(700, 150, false, false, false, false);
            BackgroundImage bi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, position, bs); 
            mainWindow.setBackground(new Background(bi));
     
            primaryStage.setScene(new Scene(mainWindow));
            primaryStage.setTitle("Chemistry Helper");
            primaryStage.setResizable(false);
            //primaryStage.initStyle(StageStyle.UTILITY);
            primaryStage.setOnCloseRequest((WindowEvent event) -> {
                Platform.exit();
            });
   
            primaryStage.setX((screenSize.getWidth() * .5) - maxWidth/2);
            primaryStage.setY(10);
            primaryStage.show();
            
        } catch (IOException ex) {
            System.out.println("IO Exception: " + ex);
        }
    }
    
    private void doInit() {
        // open the data file and read the contents
        try {
            
            InputStream is;
            String line;
            BufferedReader br;
            
            is = ChemistryHelper.class.getResourceAsStream("data/elements.txt");
            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));                                                                                                                                                                                            
            while ((line = br.readLine()) != null) {   
                String[] cols = line.split("\t");                
                elements.add(new Elements(cols[0], cols[1], cols[2], cols[3], cols[4], cols[5], 
                        cols[6], cols[7], cols[8], cols[9], cols[10], cols[11]));           
            }
            
        } catch (IOException ex) {
            String strMessage = "Unable to locate the element data file. "
                + "please contact support.";
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cannot locate data file");
            alert.setHeaderText(null);
            alert.setContentText(strMessage);
            alert.showAndWait();
            
            Platform.exit();
        }
    }  
    
    public ArrayList<Elements> getElementList() {
        return this.elements;
    }

    public void setErrorLabel(Label errorLabel) {
        this.errorLabel = errorLabel;
    }

    public void commandShow(ArrayList<String> elementsIn) {
        ArrayList<Elements> elementsToShow;
        boolean trouble = false;
        
        //we accept a maximum of 4 elements displayed
        if(elementsIn.size() > 4) {
            errorLabel.setText("Maximum 4 elements allowed. Displaying first 4.");
            errorLabel.setVisible(true);
            elementsIn.removeAll(elementsIn.subList(4, elementsIn.size()));
        }
        
        elementsToShow = validateElements(elementsIn);
        
        if(elementsToShow == null) {
            //this means one of the elements was invalid
            return;
        }
        
        try {
            AnchorPane elementWindow;
            Stage elementStage = new Stage();
            FXMLLoader elementWindowLoader = new FXMLLoader();
            elementWindowLoader.setLocation(ChemistryHelper.class.getResource("view/ElementInfoScene.fxml"));

            elementWindow = elementWindowLoader.load();
            ElementInfoSceneController ewController = elementWindowLoader.getController();
            
            ewController.showElementInfo(elementsToShow, elementWindow);
            
            Image image = new Image("net/stgtech/chemistryHelper/data/background.png",1200,700, true, false);
            BackgroundPosition position = new BackgroundPosition(Side.LEFT, 0, true, Side.TOP, 0, true);
            BackgroundSize bs = new BackgroundSize(1200, 700, false, false, false, true);
            BackgroundImage bi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, position, null); 
            
            elementWindow.setBackground(new Background(bi));
         
            elementStage.setScene(new Scene(elementWindow));
            //elementStage.initStyle(StageStyle.UTILITY);
            elementStage.setTitle("Element information display");
        
            elementStage.showAndWait();
        }
        catch (IOException iox) {
            System.out.println(iox);
            Platform.exit();
        }        
    }
    
    public void commandJoin(ArrayList<String> elementsIn) {
        ArrayList<Elements> elementsToShow;
        boolean trouble = false;

        //join two elements by their oxidation states, if possible
        
        //First, check that there are exactly two elements, reject otherwise
        if(elementsIn.size() != 2) {
            errorLabel.setText("JOIN command MUST be 2 elements. Please re-enter");
            errorLabel.setVisible(true);
            return;
        }
       
        //validate that entires are real elements
        elementsToShow = validateElements(elementsIn);
       
        //if not, forget it and return
        if(elementsToShow == null) { return; }
       
        //determine if the oxidation states allow joining
        //get the oxidation last number from the formal oxidation numbers for each element
        int oxElementOne = elementsToShow.get(0).getFinalOxidationValue();
        int oxElementTwo = elementsToShow.get(1).getFinalOxidationValue();
        
        boolean match;
        
        if(oxElementOne + oxElementTwo == 8) {
            match = true;
        } else {
            match = false;
        }
        
        //build me a window, dammit!
        try {
            AnchorPane elementWindow;
            Stage elementStage = new Stage();
            FXMLLoader elementWindowLoader = new FXMLLoader();
            elementWindowLoader.setLocation(ChemistryHelper.class.getResource("view/ElementJoinScene.fxml"));

            elementWindow = elementWindowLoader.load();
            ElementJoinSceneController ewController = elementWindowLoader.getController();
            
            ewController.showJoinResult(elementsToShow, elementWindow, match, oxElementOne, oxElementTwo);
            
            Image image = new Image("net/stgtech/chemistryHelper/data/background.png",1200,700, true, false);
            BackgroundPosition position = new BackgroundPosition(Side.LEFT, 0, true, Side.TOP, 0, true);
            BackgroundSize bs = new BackgroundSize(1200, 700, false, false, false, true);
            BackgroundImage bi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, position, null); 
            
            elementWindow.setBackground(new Background(bi));
            
            elementStage.setScene(new Scene(elementWindow));
            //elementStage.initStyle(StageStyle.UTILITY);
            elementStage.setTitle("Result of element join");
            elementStage.showAndWait();
            
        }
        catch (IOException iox) {
            System.out.println(iox);
            Platform.exit();
        }   
    }
       
    private ArrayList<Elements> validateElements(ArrayList<String> elementsIn) {
        ArrayList<Elements> elementsToShow = new ArrayList<>();
        String strInvalid = "";
        boolean trouble = false;
        
        //get element information for each input passed
        for (String e : elementsIn) {
            for(Elements element : elements) {
                if(element.searchFor(e)) {
                    elementsToShow.add(element); //add the element to our return list if good
                    trouble = false;
                    break;
                }
                else {
                    trouble = true;
                }
            }
            if(trouble) { strInvalid = strInvalid + '"' + e.toUpperCase() + '"' + " "; }
            trouble = false;
        }
        if(!"".equals(strInvalid)) {
            errorLabel.setText("Invalid element(s) " + strInvalid + " Please check and correct.");
            errorLabel.setVisible(true);
            return null;
        }
        return elementsToShow;
    }
}
