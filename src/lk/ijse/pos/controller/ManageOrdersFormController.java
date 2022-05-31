package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.Custom.ManageOrderBO;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.view.tdm.OrderDetailsTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ManageOrdersFormController {
    public JFXComboBox<String> cmbOrderID;
    public JFXComboBox<String> cmbSelectCustomer;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtQTY;
    public JFXTextField txtDiscount;
    public TableView<OrderDetailsTM> tblOrderDetails;
    public JFXTextField txtItemCode;
    public Label lblPrice;
    public Label lblDiscount;
    public Label lblTotalPrice;
    public JFXTextField txtSearchOrders;
    public AnchorPane manageOrderContext;
    public JFXButton btnSearch;
    public JFXButton btnUpdate;
    public JFXButton btnConfirm;










    //////////////////////////////////////////////////////////

    // details tka text field walte set karanna on table eken aragena

    ///////////








    ManageOrderBO manageOrderBO = (ManageOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MANAGEORDER);

    public void initialize() {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), manageOrderContext);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("itemCode"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("description"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("unitPrice"));
        tblOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("qty"));
        TableColumn<OrderDetailsTM, Button> lastCol = (TableColumn<OrderDetailsTM, Button>) tblOrderDetails.getColumns().get(4);
        lastCol.setCellValueFactory(param -> {
            Button btnRemove = new Button("Remove");
            btnRemove.setOnAction(event -> {
                OrderDetailsTM orderDetailsTM = param.getValue();
                tblOrderDetails.getItems().remove(param.getValue());
                tblOrderDetails.getSelectionModel().clearSelection();
                try {
                    manageOrderBO.updateItemDetails(orderDetailsTM.getItemCode(), orderDetailsTM.getQty());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
            return new ReadOnlyObjectWrapper<>(btnRemove);
        });
        txtItemCode.setEditable(false);
        txtDescription.setEditable(false);
        txtPackSize.setEditable(false);
        txtQtyOnHand.setEditable(false);
        txtUnitPrice.setEditable(false);
        txtDiscount.setEditable(false);
        txtSearchOrders.setDisable(true);
        btnSearch.setDisable(true);
        cmbOrderID.setDisable(true);
        btnUpdate.setDisable(true);
        btnConfirm.setDisable(true);

        loadAllCustomerIDS();

        // add listener to selectCustomer combo box
        cmbSelectCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedCustomerID) -> {
            cmbOrderID.getItems().clear();
            txtSearchOrders.setDisable(false);
            btnSearch.setDisable(false);
            cmbOrderID.setDisable(false);

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
        cmbOrderID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderID) -> {
            tblOrderDetails.getItems().clear();
            setTableData(selectedOrderID);
        });

        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
        try {
        btnUpdate.setDisable(false);

        ItemDTO itemDetails = manageOrderBO.getItemDetails(newValue.getItemCode());
        txtItemCode.setText(itemDetails.getItemCode());
        txtDescription.setText(itemDetails.getDescription());
        txtPackSize.setText(itemDetails.getPackSize());
//                    txtQtyOnHand.setText(String.valueOf(itemDetails.getQtyOnHand()));
        txtUnitPrice.setText(String.valueOf(itemDetails.getUnitPrice()));
        double discount = ((itemDetails.getUnitPrice().doubleValue()) / 100) * 5;
        txtDiscount.setText(String.valueOf(discount));
        txtQTY.setText(String.valueOf(newValue.getQty()));

        Optional<OrderDetailsTM> optOrderDetail = tblOrderDetails.getItems().stream().filter(detail -> detail.getItemCode().equals(newValue.getItemCode())).findFirst();
        txtQtyOnHand.setText((optOrderDetail.isPresent() ? itemDetails.getQtyOnHand() - (Integer.parseInt(txtQTY.getText())-optOrderDetail.get().getQty()) : itemDetails.getQtyOnHand()) + "");

        } catch (SQLException e) {
        e.printStackTrace();
        } catch (ClassNotFoundException e) {
        e.printStackTrace();
        }
//                cmbItemCode.setValue(newValue.getItemCode());
//                btnAddToList.setText("Update");
//                txtQtyOnHand.setText(Integer.parseInt(txtQtyOnHand.getText()) + newValue.getQty() + "");
//                txtQTY.setText(newValue.getQty() + "");
        } else {
//                txtQTY.clear();
        }
        });


    }

    private void setTableData(String selectedOrderID) {
        try {
            ArrayList<CustomDTO> orderDetails = manageOrderBO.getOrderDetails(selectedOrderID);
            for (CustomDTO orderDetail : orderDetails) {
                tblOrderDetails.getItems().add(new OrderDetailsTM(orderDetail.getItemCode(), orderDetail.getDescription(), orderDetail.getUnitPrice(), orderDetail.getQty()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
                    new Alert(Alert.AlertType.ERROR, "Order Id not associated with this customer").show();
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
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Id not associated with this customer").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnUpdateItemDetailsOnAction(ActionEvent actionEvent) {
        OrderDetailsTM orderDetailsTM = tblOrderDetails.getItems().stream().filter(detail -> detail.getItemCode().equals(txtItemCode.getText())).findFirst().get();
        orderDetailsTM.setQty(Integer.parseInt(txtQTY.getText()));
        tblOrderDetails.refresh();

    }

    public void btnConfirmEditsOnAction(ActionEvent actionEvent) {
    }

    public void btnCancelOrderOnAction(ActionEvent actionEvent) {
    }


}



//tblOrderDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//        if (newValue != null) {
//        try {
//        btnUpdate.setDisable(false);
//
//        ItemDTO itemDetails = manageOrderBO.getItemDetails(newValue.getItemCode());
//        txtItemCode.setText(itemDetails.getItemCode());
//        txtDescription.setText(itemDetails.getDescription());
//        txtPackSize.setText(itemDetails.getPackSize());
////                    txtQtyOnHand.setText(String.valueOf(itemDetails.getQtyOnHand()));
//        txtUnitPrice.setText(String.valueOf(itemDetails.getUnitPrice()));
//        double discount = ((itemDetails.getUnitPrice().doubleValue()) / 100) * 5;
//        txtDiscount.setText(String.valueOf(discount));
//        txtQTY.setText(String.valueOf(newValue.getQty()));
//
//        Optional<OrderDetailsTM> optOrderDetail = tblOrderDetails.getItems().stream().filter(detail -> detail.getItemCode().equals(newValue.getItemCode())).findFirst();
//        txtQtyOnHand.setText((optOrderDetail.isPresent() ? itemDetails.getQtyOnHand() - (Integer.parseInt(txtQTY.getText())-optOrderDetail.get().getQty()) : itemDetails.getQtyOnHand()) + "");
//
//        } catch (SQLException e) {
//        e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//        e.printStackTrace();
//        }
////                cmbItemCode.setValue(newValue.getItemCode());
////                btnAddToList.setText("Update");
////                txtQtyOnHand.setText(Integer.parseInt(txtQtyOnHand.getText()) + newValue.getQty() + "");
////                txtQTY.setText(newValue.getQty() + "");
//        } else {
////                txtQTY.clear();
//        }
//        });




