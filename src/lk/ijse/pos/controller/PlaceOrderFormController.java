package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.Custom.PlaceOrderBO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDetailsDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PlaceOrderFormController {
    public JFXComboBox<String> cmbCustomerID;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerTitle;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;

    public JFXComboBox<String> cmbItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtQTY;
    public JFXTextField txtDiscount;
    public Label lblOrderID;
    public TableView<OrderDetailsDTO> tblOrderDetails;
    public Label lblDate;
    public Label lblTime;
    public Label lblPrice;
    public Label lblDiscount;
    public Label lblTotalPrice;
    public JFXButton btnNewCustomer;
    public JFXButton btnPlaceOrder;
    public JFXButton btnAddToList;


    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACEORDER);

    public void initialize() {
        txtCustomerName.setEditable(false);
        txtCustomerTitle.setEditable(false);
        txtCustomerAddress.setEditable(false);
        txtCity.setEditable(false);
        txtProvince.setEditable(false);
        txtPostalCode.setEditable(false);
        btnPlaceOrder.setDisable(true);
        btnAddToList.setDisable(true);

        lblOrderID.setText(generateNewOrderID());
        lblDate.setText(LocalDate.now().toString());

        loadAllCustomerIds();
        loadAllItemCodes();

        //ADD Listener to the Customer ID combo box
        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    if (!placeOrderBO.checkCustomerIsAvailable(newValue + "")) {
                        // if Customer is not
                        new Alert(Alert.AlertType.ERROR, "There is no customer with id " + newValue + "").show();
                    }
                    // if customer is available
                    CustomerDTO customerDTO = placeOrderBO.searchCustomer(newValue + "");
                    txtCustomerName.setText(customerDTO.getCustName());
                    txtCustomerAddress.setText(customerDTO.getCustAddress());
                    txtCustomerTitle.setText(customerDTO.getCustTitle());
                    txtCity.setText(customerDTO.getCity());
                    txtProvince.setText(customerDTO.getProvince());
                    txtPostalCode.setText(customerDTO.getPostalCode());

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                txtCustomerName.clear();
            }
        });
    }

    private void loadAllItemCodes() {
        try {
            ArrayList<ItemDTO> allItems = placeOrderBO.getAllItems();
            for (ItemDTO item : allItems) {
                cmbItemCode.getItems().add(item.getItemCode());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load item codes").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllCustomerIds() {
        try {
            ArrayList<CustomerDTO> allCustomers = placeOrderBO.getAllCustomers();
            for (CustomerDTO customerDTO : allCustomers) {
                cmbCustomerID.getItems().add(customerDTO.getCustID());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String generateNewOrderID() {

        try {
            return placeOrderBO.generateNewOrderID();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";
    }

    public void btnNewCustomerOnAction(ActionEvent actionEvent) {
        txtCustomerName.requestFocus();
        btnNewCustomer.setText("Add");
    }

    public void btnAddToListOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
    }

    public void btnCancelOrderOnAction(ActionEvent actionEvent) {
    }
}
