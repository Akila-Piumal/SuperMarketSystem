package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.Custom.PlaceOrderBO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.dto.OrderDetailsDTO;
import lk.ijse.pos.view.tdm.OrderDetailsTM;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public TableView<OrderDetailsTM> tblOrderDetails;
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

        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("itemCode"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("description"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("qtyOnHand"));
        tblOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("unitPrice"));
        tblOrderDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("discount"));
        tblOrderDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("qty"));
        tblOrderDetails.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("total"));
        TableColumn<OrderDetailsTM, Button> lastCol = (TableColumn<OrderDetailsTM, Button>) tblOrderDetails.getColumns().get(7);
        lastCol.setCellValueFactory(param -> {
            Button btnRemove = new Button("Remove");
            btnRemove.setOnAction(event -> {
                tblOrderDetails.getItems().remove(param.getValue());
                tblOrderDetails.getSelectionModel().clearSelection();
                cmbItemCode.getSelectionModel().clearSelection();
                if (tblOrderDetails.getItems().isEmpty()) {
                    btnPlaceOrder.setDisable(true);
                    btnNewCustomer.setDisable(false);
                    cmbCustomerID.setDisable(false);
                }
                calculateTotal();
                calculateDiscount();
                calculateFullTotal();
            });
            return new ReadOnlyObjectWrapper<>(btnRemove);
        });

        txtCustomerName.setEditable(false);
        txtCustomerTitle.setEditable(false);
        txtCustomerAddress.setEditable(false);
        txtCity.setEditable(false);
        txtProvince.setEditable(false);
        txtPostalCode.setEditable(false);
        btnPlaceOrder.setDisable(true);
        btnAddToList.setDisable(true);
        txtDescription.setEditable(false);
        txtPackSize.setEditable(false);
        txtQtyOnHand.setEditable(false);
        txtUnitPrice.setEditable(false);
        txtDiscount.setEditable(false);
        txtQTY.setDisable(true);

        lblOrderID.setText(generateNewOrderID());
        lblDate.setText(LocalDate.now().toString());

        loadAllCustomerIds();
        loadAllItemCodes();

        //ADD Listener to the Customer ID combo box
        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btnNewCustomer.setText("+New Customer");
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

        // ADD Listener to the Item code combo box
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btnAddToList.setDisable(false);
                txtQTY.setDisable(false);
                try {
                    if (!placeOrderBO.checkItemIsAvailable(newValue + "")) {
                        // if Item is not
                        new Alert(Alert.AlertType.ERROR, "There is no item with itemCode " + newValue + "").show();
                    }
                    // if Item is available
                    ItemDTO itemDTO = placeOrderBO.searchItem(newValue + "");
                    txtDescription.setText(itemDTO.getDescription());
                    txtPackSize.setText(itemDTO.getPackSize());
                    txtUnitPrice.setText(itemDTO.getUnitPrice().toString());
                    Optional<OrderDetailsTM> optOrderDetail = tblOrderDetails.getItems().stream().filter(detail -> detail.getItemCode().equals(newValue)).findFirst();
                    txtQtyOnHand.setText((optOrderDetail.isPresent() ? itemDTO.getQtyOnHand() - optOrderDetail.get().getQty() : itemDTO.getQtyOnHand()) + "");
                    double discount = ((itemDTO.getUnitPrice().doubleValue()) / 100) * 5;
                    txtDiscount.setText(String.valueOf(discount));
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                txtDescription.clear();
                txtPackSize.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
                txtDiscount.clear();
                txtQTY.clear();
            }
        });

        //ADD Listener to the Table
        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cmbItemCode.setDisable(true);
                cmbItemCode.setValue(newValue.getItemCode());
                btnAddToList.setText("Update");
                txtQtyOnHand.setText(Integer.parseInt(txtQtyOnHand.getText()) + newValue.getQty() + "");
                txtQTY.setText(newValue.getQty() + "");
            } else {
                btnAddToList.setText("Add to List");
                cmbItemCode.setDisable(false);
                cmbItemCode.getSelectionModel().clearSelection();
                txtQTY.clear();
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
        txtCustomerName.setEditable(true);
        txtCustomerTitle.setEditable(true);
        txtCustomerAddress.setEditable(true);
        txtCity.setEditable(true);
        txtProvince.setEditable(true);
        txtPostalCode.setEditable(true);
        txtCustomerName.requestFocus();

        if (btnNewCustomer.getText().equalsIgnoreCase("Add")) {
            try {
                String newCustomerID = generateNewCustomerID();
                if (placeOrderBO.saveCustomer(new CustomerDTO(newCustomerID, txtCustomerTitle.getText(), txtCustomerName.getText(), txtCustomerAddress.getText(), txtCity.getText(), txtProvince.getText(), txtPostalCode.getText()))) {
                    cmbCustomerID.getItems().clear();
                    loadAllCustomerIds();
                    cmbCustomerID.setValue(newCustomerID);
                    btnNewCustomer.setText("+New Customer");
                    cmbItemCode.requestFocus();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            cmbCustomerID.getSelectionModel().clearSelection();
            txtCustomerName.clear();
            txtCustomerTitle.clear();
            txtCustomerAddress.clear();
            txtCity.clear();
            txtProvince.clear();
            txtPostalCode.clear();
            btnNewCustomer.setText("Add");
        }

    }

    private String generateNewCustomerID() throws SQLException, ClassNotFoundException {
        return placeOrderBO.generateNewCustomerID();
    }

    public void btnAddToListOnAction(ActionEvent actionEvent) {
        String itemCode = cmbItemCode.getValue();
        String description = txtDescription.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        BigDecimal unitPrice = BigDecimal.valueOf(Double.valueOf(txtUnitPrice.getText()));
        int qty = Integer.parseInt(txtQTY.getText());
        double discountForOneItem = Double.valueOf(txtDiscount.getText()) * qty;
        double totalForOneItem = (Double.valueOf(txtUnitPrice.getText()) * qty) - discountForOneItem;

        // Check that the item already on the cart
        boolean exists = tblOrderDetails.getItems().stream().anyMatch(detail -> detail.getItemCode().equals(itemCode));

        if (exists) {
            // if item already on the cart
            OrderDetailsTM orderDetailsTM = tblOrderDetails.getItems().stream().filter(detail -> detail.getItemCode().equals(itemCode)).findFirst().get();

            if (btnAddToList.getText().equalsIgnoreCase("Update")) {
                orderDetailsTM.setQty(qty);
                orderDetailsTM.setTotal(totalForOneItem);
                orderDetailsTM.setDiscount(Double.parseDouble(txtDiscount.getText()) * Integer.parseInt(txtQTY.getText()));
                tblOrderDetails.getSelectionModel().clearSelection();
            } else {
                orderDetailsTM.setQty(orderDetailsTM.getQty() + qty);
                orderDetailsTM.setDiscount(Double.valueOf(txtDiscount.getText()) * orderDetailsTM.getQty());
                double total = (unitPrice.doubleValue() * orderDetailsTM.getQty()) - orderDetailsTM.getDiscount();
                orderDetailsTM.setTotal(total);
            }
            tblOrderDetails.refresh();

        } else {
            // if item isn't in the cart
            tblOrderDetails.getItems().add(new OrderDetailsTM(itemCode, description, qtyOnHand, unitPrice, discountForOneItem, qty, totalForOneItem));
        }
        cmbItemCode.getSelectionModel().clearSelection();
        cmbItemCode.requestFocus();
        btnPlaceOrder.setDisable(false);
        calculateTotal();
        calculateDiscount();
        calculateFullTotal();
        btnNewCustomer.setDisable(true);
        cmbCustomerID.setDisable(true);
    }

    private void calculateFullTotal() {
        double total = 0;
        for (OrderDetailsTM tm : tblOrderDetails.getItems()) {
            total += tm.getTotal();
        }
        lblTotalPrice.setText(String.valueOf(total));
    }

    private void calculateDiscount() {
        double discount = 0;
        for (OrderDetailsTM tm : tblOrderDetails.getItems()) {
            discount += tm.getDiscount();
        }
        lblDiscount.setText(String.valueOf(discount));
    }

    private void calculateTotal() {
        double total = 0;
        for (OrderDetailsTM tm : tblOrderDetails.getItems()) {
            total += tm.getUnitPrice().doubleValue() * tm.getQty();
        }
        lblPrice.setText(String.valueOf(total));
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        boolean result = saveOrder(lblOrderID.getText(), LocalDate.now(), cmbCustomerID.getValue(),
                tblOrderDetails.getItems().stream().map(tm -> new OrderDetailsDTO(lblOrderID.getText(), tm.getItemCode(), tm.getQty(), tm.getDiscount())).collect(Collectors.toList()));
        if (result) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        lblOrderID.setText(generateNewOrderID());

        clearAll();
    }

    private void clearAll(){
        cmbCustomerID.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        tblOrderDetails.getItems().clear();
        txtQTY.clear();
        cmbCustomerID.setDisable(false);
        cmbCustomerID.requestFocus();
        txtCustomerAddress.clear();
        txtCustomerTitle.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        btnNewCustomer.setDisable(false);
        btnAddToList.setDisable(true);
        btnPlaceOrder.setDisable(true);
        calculateTotal();
        calculateDiscount();
        calculateFullTotal();
    }

    private boolean saveOrder(String orderID, LocalDate date, String customerID, List<OrderDetailsDTO> orderDetails) {
        try {
            return placeOrderBO.placeOrder(new OrderDTO(orderID, date, customerID, orderDetails));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void btnCancelOrderOnAction(ActionEvent actionEvent) {
        Alert alert=new Alert(Alert.AlertType.WARNING,"Are you sure to cancel order !",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().equals(ButtonType.YES)){
            clearAll();
        }
    }
}
