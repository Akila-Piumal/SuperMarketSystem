package lk.ijse.pos.controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;

public class AdminDashBoardFormController {
    public AnchorPane AdminDashBoardContext;

    public void initialize(){
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), AdminDashBoardContext);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    public void btnManageItemsFormOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) AdminDashBoardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageItemsForm.fxml"))));
        stage.show();
    }
}
