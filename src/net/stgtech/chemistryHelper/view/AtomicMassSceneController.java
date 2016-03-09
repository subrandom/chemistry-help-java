
package net.stgtech.chemistryHelper.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AtomicMassSceneController {

    @FXML Label equationEnteredLabel;
    @FXML Label atomicMassLabel;

    public void run(String equationEntered, Double atomicMass) {
        equationEnteredLabel.setText(equationEntered);
        atomicMassLabel.setText(atomicMass.toString());
    }

}
