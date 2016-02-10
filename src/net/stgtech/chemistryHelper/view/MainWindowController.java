package net.stgtech.chemistryHelper.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import net.stgtech.chemistryHelper.controller.CommandProcessor;
import net.stgtech.chemistryHelper.controller.ScreenController;

public class MainWindowController implements Initializable {
    @FXML
    private TextField dataEntryField;
    
    @FXML
    private Label errorLabel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataEntryField.getParent().requestFocus();
    }    
    
    @FXML
    private void parseEntry() {
        String commandString = dataEntryField.getText().trim();
        dataEntryField.clear();
        dataEntryField.getParent().requestFocus();
        CommandProcessor.parseCommandEntry(commandString);
    }
    
    @FXML
    private void clearError() {
        errorLabel.setVisible(false);
    }
    
    @FXML
    private void helpIconClick(MouseEvent event) {
        event.consume();
        ScreenController.showHelpWindow();
    }
        
}
