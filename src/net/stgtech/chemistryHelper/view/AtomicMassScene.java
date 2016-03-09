/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.stgtech.chemistryHelper.view;

import java.io.IOException;
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
import javafx.stage.StageStyle;
import net.stgtech.chemistryHelper.ChemistryHelper;

/**
 *
 * @author stephen.bradley
 */
public class AtomicMassScene {
    AnchorPane atomicMassPane = new AnchorPane();
    AtomicMassSceneController atomicMassSceneController;
    Stage atomicMassStage = new Stage();

    public AtomicMassScene(String equation, double result) throws IOException {
        FXMLLoader atomicMassWindowLoader = new FXMLLoader();
        atomicMassWindowLoader.setLocation(ChemistryHelper.class.getResource("view/AtomicMassScene.fxml"));

        try {
            atomicMassPane = atomicMassWindowLoader.load();
            atomicMassSceneController = atomicMassWindowLoader.getController();
            Image image = new Image("net/stgtech/chemistryHelper/data/background.png",600,125, false, false);
            BackgroundPosition position = new BackgroundPosition(Side.LEFT, 0, true, Side.TOP, 0, true);
            BackgroundSize bs = new BackgroundSize(100, 100, true, true, false, true);
            BackgroundImage bi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, position, null);

            atomicMassPane.setBackground(new Background(bi));
            atomicMassStage.setScene(new Scene(atomicMassPane));
            atomicMassStage.setTitle("Atomic Mass Calculation");
            atomicMassStage.setMaxWidth(500);
            atomicMassStage.setMaxHeight(110);
            atomicMassStage.initStyle(StageStyle.UTILITY);
            atomicMassStage.show();
            atomicMassSceneController.run(equation, result);
        } catch (IOException iox){
            throw new IOException(iox);
        }
    }
}
