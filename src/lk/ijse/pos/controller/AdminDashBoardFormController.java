package lk.ijse.pos.controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import lk.ijse.pos.util.Animation;

import java.io.IOException;

public class AdminDashBoardFormController {
    public AnchorPane AdminDashBoardContext;

    public void initialize(){
        Animation.windowAnimation(AdminDashBoardContext);
    }

    public void btnManageItemsFormOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) AdminDashBoardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageItemsForm.fxml"))));
        stage.show();
    }

    public void btnManageReportsFormOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) AdminDashBoardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageReportsForm.fxml"))));
        stage.show();
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) AdminDashBoardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
        stage.show();
    }
}
