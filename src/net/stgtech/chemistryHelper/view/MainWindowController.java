package net.stgtech.chemistryHelper.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import net.stgtech.chemistryHelper.ChemistryHelper;

public class MainWindowController implements Initializable {
    private ChemistryHelper mainApp;
    
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
        //Get the value from the text box, put them in a list
        String strCommands = dataEntryField.getText().trim(); //remove white spaces
        
        //clear the field and move the focus
        dataEntryField.clear();
        dataEntryField.getParent().requestFocus();
        
        ArrayList<String> commands = new ArrayList<>();
        commands.addAll(Arrays.asList(strCommands.split(" ")));
        
        //Is the first item in the list >= 4 characters? If so, it should be a command
        if (commands.get(0).length() >= 4) {
            switch(commands.get(0).toLowerCase()) {
                case "show":
                    commands.remove(0);
                    mainApp.commandShow(commands);
                    break;
                case "join":
                    commands.remove(0);
                    mainApp.commandJoin(commands);
                    break;
                default:
                    errorLabel.setText("Please enter elements to lookup or a valid command.");
                    errorLabel.setVisible(true);
                    dataEntryField.requestFocus();
                    break;
            }
        }
        else if (commands.get(0).length() <= 2 && commands.get(0).length() > 0) { //should be an element
            mainApp.commandShow(commands);    
        }
        else {
            errorLabel.setText("The first value must be a command or an element lookup (name, symbol, atomic number)");
            errorLabel.setVisible(true);
        }
    }
    
    @FXML
    private void clearError() {
        errorLabel.setVisible(false);
    }
    
    public void run(ChemistryHelper mainApp) {
        this.mainApp = mainApp;
        mainApp.setErrorLabel(this.errorLabel);
    }
    
    @FXML
    private void helpIconClick(MouseEvent event) {
        
        event.consume(); //eat the mouse event, or the window will pop up twice
        
        Stage helpStage = new Stage();
        Pane contentPane = new Pane();
        WebView webView = new WebView();
        String strMessage = 
            "<html><h1>Chemistry Helper</h1>"
                + "<h2>Usage:</h2>"
                + "<p>Commands: </p>"
                + "<h4>show</h4>shows properties on 1 - 4 elements types, "
                + "search by element name, symbol, or atomic number. <br>"
                + "Separate elments with a space.</p>"
                + "<p>EX: show carbon 18 oxygen he </p>"
                + "<p>Note that the 'show' command is optional, you may simply enter the elements.</p>"
                + "<h4>join</h4>enter two elements to see if they can be combined using Oxidation levels</p>"
                + "<p>EX: join s oxygen</p>"
                + "<p><b>Send feature requests to: stephen@stg-tech.net</b?</p>"
                + "<p>(C)2015 Stephen Bradley, STG Technology</p>"
                + "</html>";
            
            
            webView.getEngine().loadContent(strMessage);
            webView.setPrefSize(400,500);
            contentPane.getChildren().add(webView);
            helpStage.setScene(new Scene(contentPane));
            helpStage.setWidth(400);
            helpStage.setHeight(500);
            helpStage.setTitle("Command reference");
            helpStage.show();
    }
}
