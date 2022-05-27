package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.Custom.PlaceOrderBO;
import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.OrderDetailsDTO;

import java.sql.SQLException;

public class PlaceOrderFormController {
    public JFXComboBox<String> cmbCustomerID;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public Label lblOrderID;
    public JFXComboBox<String> cmbItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtQTY;
    public JFXTextField txtDiscount;
    public TableView<OrderDetailsDTO> tblOrderDetails;
    public Label lblPrice;
    public Label lblDiscount;
    public Label lblTotalPrice;

    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACEORDER);

    public void initialize(){
        lblOrderID.setText(generateNewOrderID());

    }

    private String generateNewOrderID() {

        try {
            System.out.println(placeOrderBO.generateNewOrderID());
            return placeOrderBO.generateNewOrderID();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";
    }

    public void btnNewCustomerOnAction(ActionEvent actionEvent) {
    }

    public void btnAddToListOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
    }

    public void btnCancelOrderOnAction(ActionEvent actionEvent) {
    }
}
