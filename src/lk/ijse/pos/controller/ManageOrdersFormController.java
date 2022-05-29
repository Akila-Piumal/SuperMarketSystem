package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.Custom.ManageOrderBO;
import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

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

    ManageOrderBO manageOrderBO = (ManageOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MANAGEORDER);

    public void initialize(){
        loadAllCustomerIDS();

        cmbSelectCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedCustomerID) -> {
            cmbOrderID.getItems().clear();
            loadOrderIDSForEachCustomer(selectedCustomerID);
        });

    }

    private void loadOrderIDSForEachCustomer(String custID) {
        try {
            ArrayList<OrderDTO> eachCustomerOrders = manageOrderBO.getEachCustomerOrders(custID);
            for (OrderDTO eachCustomerOrder : eachCustomerOrders) {
                cmbOrderID.getItems().add(eachCustomerOrder.getOrderId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllCustomerIDS() {
        try {
            ArrayList<CustomerDTO> allCustomers = manageOrderBO.getAllCustomers();
            for (CustomerDTO dto : allCustomers) {
                cmbSelectCustomer.getItems().add(dto.getCustID());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
