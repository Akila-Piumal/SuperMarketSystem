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

public class CashierDashBoardFormController {
    public AnchorPane CashierDashBoardContext;

    public void initialize(){
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), CashierDashBoardContext);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    public void btnPlaceOrderFormOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) CashierDashBoardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/PlaceOrderForm.fxml"))));
        stage.show();
    }

    public void btnManageOrdersOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) CashierDashBoardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageOrdersForm.fxml"))));
        stage.show();
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) CashierDashBoardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
        stage.show();
    }
}
