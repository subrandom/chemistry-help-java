package net.stgtech.chemistryHelper.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.stgtech.chemistryHelper.model.Element;
import net.stgtech.chemistryHelper.model.Elements.ELEMENTS;

public class ElementInfoSceneController implements Initializable {
    @FXML
    private AnchorPane myWindow;
    private static final double SIZEOFVBOX = 230;
    
    @FXML
    private HBox hBox;
    
    @FXML
    private VBox labelBox;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
    public void showElementInfo(ArrayList<Element> elementsToShow) {

        elementsToShow.stream().forEach((element) -> {
            hBox = (HBox) myWindow.getChildrenUnmodifiable().get(0);

            VBox elementBox = new VBox();
            elementBox.setPrefWidth(SIZEOFVBOX);
            elementBox.alignmentProperty().set(Pos.BASELINE_LEFT);

            elementBox.getChildren().addAll(
                    element.getLabelForProperty(ELEMENTS.ELEMENT_NAME),
                    element.getLabelForProperty(ELEMENTS.ELEMENT_SYMBOL),
                    element.getLabelForProperty(ELEMENTS.ATOMIC_NUMBER),
                    element.getLabelForProperty(ELEMENTS.ATOMIC_MASS),
                    element.getLabelForProperty(ELEMENTS.STANDARD_STATE),
                    element.getLabelForProperty(ELEMENTS.BONDING_TYPE),
                    element.getLabelForProperty(ELEMENTS.MELTING_POINT_K),
                    element.getLabelForProperty(ELEMENTS.BOILING_POINT_K),
                    element.getLabelForProperty(ELEMENTS.DENSITY),
                    element.getLabelForProperty(ELEMENTS.METAL_NONMETAL),
                    element.getLabelForProperty(ELEMENTS.FORMAL_OXIDATION_NUM),
                    element.getLabelForProperty(ELEMENTS.YEAR_DISCOVERED));
            
            hBox.getChildren().add(elementBox);
        });
    }
}
