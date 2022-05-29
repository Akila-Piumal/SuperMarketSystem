package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.Custom.ManageOrderBO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.OrderDTO;

import java.io.IOException;
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
    public JFXTextField txtSearchOrders;
    public AnchorPane manageOrderContext;
    public JFXButton btnSearch;

    ManageOrderBO manageOrderBO = (ManageOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MANAGEORDER);

    public void initialize() {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), manageOrderContext);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        txtSearchOrders.setDisable(true);
        btnSearch.setDisable(true);

        loadAllCustomerIDS();

        // add listener to selectCustomer combo box
        cmbSelectCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedCustomerID) -> {
            cmbOrderID.getItems().clear();
            txtSearchOrders.setDisable(false);
            btnSearch.setDisable(false);

            try {
                ArrayList<OrderDTO> eachCustomerOrders = getOrdersForEachCustomer(selectedCustomerID);
                for (OrderDTO eachCustomerOrder : eachCustomerOrders) {
                    cmbOrderID.getItems().add(eachCustomerOrder.getOrderId());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        // add listener to OrderID combo box
        cmbOrderID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

        });
    }

    private ArrayList<OrderDTO> getOrdersForEachCustomer(String custID) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDTO> eachCustomerOrders = manageOrderBO.getEachCustomerOrders(custID);
        return eachCustomerOrders;
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

    public void backToHomeOnAction(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) manageOrderContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CashierDashBoardForm.fxml"))));
        stage.show();
    }

    public void textFields_Key_Pressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            try {
                if (checkTheOrderIsCorrect()) {
                    cmbOrderID.getSelectionModel().select(txtSearchOrders.getText());
                } else {
                    new Alert(Alert.AlertType.ERROR,"Order Id not associated with this customer").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkTheOrderIsCorrect() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDTO> ordersForEachCustomer = getOrdersForEachCustomer(cmbSelectCustomer.getValue());
        for (OrderDTO orderDTO : ordersForEachCustomer) {
            if (txtSearchOrders.getText().equalsIgnoreCase(orderDTO.getOrderId())) {
                return true;
            }
        }
        return false;
    }

    public void btnSearchOrderOnAction(ActionEvent actionEvent) {
        try {
            if (checkTheOrderIsCorrect()) {
                cmbOrderID.getSelectionModel().select(txtSearchOrders.getText());
            }else {
                new Alert(Alert.AlertType.ERROR,"Order Id not associated with this customer").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void btnUpdateItemDetailsOnAction(ActionEvent actionEvent) {
    }

    public void btnConfirmEditsOnAction(ActionEvent actionEvent) {
    }

    public void btnCancelOrderOnAction(ActionEvent actionEvent) {
    }


}
