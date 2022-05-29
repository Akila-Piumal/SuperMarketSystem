package lk.ijse.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class CashierDashBoardFormController {
    public AnchorPane CashierDashBoardContext;

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
}
