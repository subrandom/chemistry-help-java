
package net.stgtech.chemistryHelper;

import javafx.application.Application;
import javafx.stage.Stage;
import net.stgtech.chemistryHelper.controller.ScreenController;
import net.stgtech.chemistryHelper.model.Elements;

public class ChemistryHelper extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        setUserAgentStylesheet(STYLESHEET_MODENA);
        Elements.loadElementDataFromFile();
        ScreenController.setStage(primaryStage);
        ScreenController.initializeApplicationWindows();
        ScreenController.showMainWindow();
        
    }
}
