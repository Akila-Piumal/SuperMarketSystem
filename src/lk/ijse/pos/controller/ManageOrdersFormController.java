package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
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
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.Custom.ManageOrderBO;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.dto.OrderDetailsDTO;
import lk.ijse.pos.util.Animation;
import lk.ijse.pos.view.tdm.OrderDetailsTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ManageOrdersFormController {
    public JFXComboBox<String> cmbOrderID;
    public JFXComboBox<String> cmbSelectCustomer;
    public JFXTextField txtDescription;
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
    public ArrayList<OrderDetailsTM> removedItemList = new ArrayList<>();
    public JFXButton btnCancelOrder;

    ManageOrderBO manageOrderBO = (ManageOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MANAGEORDER);

    public void initialize() {
        Animation.windowAnimation(manageOrderContext);

        txtItemCode.setEditable(false);
        txtDescription.setEditable(false);
        txtQtyOnHand.setEditable(false);
        txtUnitPrice.setEditable(false);
        txtDiscount.setEditable(false);
        txtSearchOrders.setDisable(true);
        btnSearch.setDisable(true);
        cmbOrderID.setDisable(true);
        btnUpdate.setDisable(true);
        btnConfirm.setDisable(true);
        btnCancelOrder.setDisable(true);

        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("itemCode"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("description"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("qtyOnHand"));
        tblOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("unitPrice"));
        tblOrderDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("qty"));
        TableColumn<OrderDetailsTM, Button> lastCol = (TableColumn<OrderDetailsTM, Button>) tblOrderDetails.getColumns().get(5);

        lastCol.setCellValueFactory(param -> {
            Button btnRemove = new Button("Remove");
            btnRemove.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure ? ", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();

                if (buttonType.get().equals(ButtonType.YES)) {
                    tblOrderDetails.getItems().remove(param.getValue());
                    tblOrderDetails.getSelectionModel().clearSelection();
                    removedItemList.add(param.getValue());
                    btnConfirm.setDisable(false);
                    btnUpdate.setDisable(true);
                    cmbOrderID.setDisable(true);
                    cmbSelectCustomer.setDisable(true);
                    clearTextFields();
                    calculateTotalAndDiscount();

                    if (tblOrderDetails.getItems().isEmpty()) {
                        new Alert(Alert.AlertType.WARNING, "Order is Empty").show();
                        try {
                            manageOrderBO.removeOrderAndOrderDetails(cmbOrderID.getValue());
                            cmbOrderID.getItems().remove(cmbOrderID.getValue());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            return new ReadOnlyObjectWrapper<>(btnRemove);
        });

        // Load All Customer IDS
        loadAllCustomerIDS();

        // add listener to selectCustomer combo box
        cmbSelectCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedCustomerID) -> {
            cmbOrderID.getItems().clear();
            txtSearchOrders.setDisable(false);
            btnSearch.setDisable(false);
            cmbOrderID.setDisable(false);
            btnConfirm.setDisable(true);
            clearTextFields();

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
            calculateTotalAndDiscount();
            btnConfirm.setDisable(true);
            btnCancelOrder.setDisable(false);
            clearTextFields();

            // Clear Arraylist of removed items from a order
            removedItemList.clear();
        });

        // add listener to Table
        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetails) -> {
            if (selectedOrderDetails != null) {

                btnUpdate.setDisable(false);

                // set Data to the text fields
                txtItemCode.setText(selectedOrderDetails.getItemCode());
                txtDescription.setText(selectedOrderDetails.getDescription());
                txtQtyOnHand.setText(String.valueOf(selectedOrderDetails.getQtyOnHand()));
                txtUnitPrice.setText(String.valueOf(selectedOrderDetails.getUnitPrice()));
                txtQTY.setText(String.valueOf(selectedOrderDetails.getQty()));
                double discount = ((selectedOrderDetails.getUnitPrice()).doubleValue() / 100) * 5;
                txtDiscount.setText(String.valueOf(discount));
                txtQtyOnHand.setText(selectedOrderDetails.getQtyOnHand() + "");
            } else {
//                txtQTY.clear();
            }
        });

    }

    private void clearTextFields(){
        txtItemCode.clear();
        txtDescription.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
        txtDiscount.clear();
        txtQTY.clear();
    }

    private void calculateTotalAndDiscount() {
        double price = 0;
        double discount = 0;

        for (OrderDetailsTM item : tblOrderDetails.getItems()) {
            double oneItemPrice = item.getUnitPrice().doubleValue() * item.getQty();
            price += oneItemPrice;
            double oneItemDiscount = (((item.getUnitPrice().doubleValue()) / 100) * 5) * item.getQty();
            discount += oneItemDiscount;
        }
        lblPrice.setText(String.valueOf(BigDecimal.valueOf(price).setScale(2)));
        lblDiscount.setText(String.valueOf(BigDecimal.valueOf(discount).setScale(2)));
        lblTotalPrice.setText(String.valueOf(BigDecimal.valueOf(price - discount).setScale(2)));
    }

    private void setTableData(String selectedOrderID) {
        try {
            ArrayList<CustomDTO> orderDetails = manageOrderBO.getOrderDetails(selectedOrderID);
            for (CustomDTO orderDetail : orderDetails) {
                tblOrderDetails.getItems().add(new OrderDetailsTM(orderDetail.getItemCode(), orderDetail.getDescription(), orderDetail.getQtyOnHand(), orderDetail.getUnitPrice(), orderDetail.getQty()));
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
        int oldQty = orderDetailsTM.getQty();
        int newQty = Integer.parseInt(txtQTY.getText());
        orderDetailsTM.setQty(newQty);
        if (newQty < oldQty) {
            orderDetailsTM.setQtyOnHand(orderDetailsTM.getQtyOnHand() + (oldQty - newQty));
        } else {
            orderDetailsTM.setQtyOnHand(orderDetailsTM.getQtyOnHand() - (newQty - oldQty));
        }

        tblOrderDetails.refresh();
        tblOrderDetails.getSelectionModel().clearSelection();
        clearTextFields();
        btnConfirm.setDisable(false);
        cmbOrderID.setDisable(true);
        cmbSelectCustomer.setDisable(true);
        calculateTotalAndDiscount();
    }

    public void btnConfirmEditsOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ? ", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().equals(ButtonType.YES)) {
            cmbOrderID.setDisable(false);
            cmbSelectCustomer.setDisable(false);
            ObservableList<OrderDetailsTM> items = tblOrderDetails.getItems();
            for (OrderDetailsTM item : items) {
                try {
                    // Update QtyOnHand of item
                    manageOrderBO.updateItemDetails(item.getItemCode(), item.getQtyOnHand());

                    // update order Details
                    double discount = (((item.getUnitPrice().doubleValue()) / 100) * 5) * item.getQty();
                    manageOrderBO.updateOrderDetails(new OrderDetailsDTO(cmbOrderID.getValue(), item.getItemCode(), item.getQty(), discount));

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            // remove the items from order that removed in table in UI and update item details
            if (removedItemList != null) {
                for (OrderDetailsTM orderDetailsTM : removedItemList) {
                    try {
                        manageOrderBO.updateItemDetails(orderDetailsTM.getItemCode(), orderDetailsTM.getQty() + orderDetailsTM.getQtyOnHand());

                        manageOrderBO.deleteItemFromOrder(cmbOrderID.getValue(), orderDetailsTM.getItemCode());

                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void btnCancelOrderOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure to cancel order? ", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().equals(ButtonType.YES)) {
            try {
                if (tblOrderDetails.getItems() != null) {
                    for (OrderDetailsTM item : tblOrderDetails.getItems()) {
                        manageOrderBO.updateItemDetails(item.getItemCode(), (item.getQty() + item.getQtyOnHand()));
                    }
                }

                manageOrderBO.removeOrderAndOrderDetails(cmbOrderID.getValue());
                tblOrderDetails.getItems().clear();
                cmbOrderID.getItems().remove(cmbOrderID.getValue());
                cmbOrderID.getSelectionModel().clearSelection();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}




