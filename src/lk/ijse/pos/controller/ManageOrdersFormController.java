package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ManageOrdersFormController {
    public JFXComboBox<String> cmbOrderID;
    public JFXComboBox<String> cmbSelectCustomer;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtQTY;
    public JFXTextField txtDiscount;
    public TableView tblOrderDetails;
    public JFXTextField txtItemCode;
    public Label lblPrice;
    public Label lblDiscount;
    public Label lblTotalPrice;

    public void initialize(){
        loadAllCustomerIDS();
    }

    private void loadAllCustomerIDS() {

    }

    public void backToHomeOnAction(MouseEvent mouseEvent) {
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
    }

    public void btnSearchOrderOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateItemDetailsOnAction(ActionEvent actionEvent) {
    }

    public void btnConfirmEditsOnAction(ActionEvent actionEvent) {
    }

    public void btnCancelOrderOnAction(ActionEvent actionEvent) {
    }
}
