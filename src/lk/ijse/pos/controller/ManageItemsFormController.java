package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.Custom.ManageItemBO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.util.Animation;
import lk.ijse.pos.util.ValidationUtil;
import lk.ijse.pos.view.tdm.ItemTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class ManageItemsFormController {
    private final ManageItemBO manageItemBO = (ManageItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MANAGEITEM);
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXButton btnSave;
    public TableView<ItemTM> tblItemDetails;
    public AnchorPane manageItemFormContext;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() {
        Animation.windowAnimation(manageItemFormContext);

        tblItemDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("itemCode"));
        tblItemDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("description"));
        tblItemDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("packSize"));
        tblItemDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("unitPrice"));
        tblItemDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("qtyOnHand"));


        TableColumn<ItemTM, HBox> lastCol = (TableColumn<ItemTM, HBox>) tblItemDetails.getColumns().get(5);
        lastCol.setCellValueFactory(param -> {
            Button btnUpdate = new Button("Update");
            Button btnDelete = new Button("Delete");
            HBox hBox = new HBox(10, btnUpdate, btnDelete);

            btnDelete.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Are You Sure ?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)) {
                    boolean b = false;
                    try {
                        b = manageItemBO.deleteItem(param.getValue().getItemCode());
                        tblItemDetails.getItems().remove(param.getValue());
                        if (b) {
                            new Alert(Alert.AlertType.INFORMATION, "Deleted!").show();
                            txtItemCode.setText(generateNewItemCode());
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Delete Unsuccessfull!").show();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            });

            btnUpdate.setOnAction(event -> {
                btnSave.setText("Update");
                tblItemDetails.getSelectionModel().select(param.getValue());
                txtItemCode.setText(param.getValue().getItemCode());
                txtDescription.setText(param.getValue().getDescription());
                txtPackSize.setText(param.getValue().getPackSize());
                txtUnitPrice.setText(String.valueOf(param.getValue().getUnitPrice()));
                txtQtyOnHand.setText(String.valueOf(param.getValue().getQtyOnHand()));
                txtItemCode.setEditable(false);
                txtDescription.requestFocus();
            });

            return new ReadOnlyObjectWrapper<>(hBox);
        });

        //add pattern and text to the map
        //Create a pattern and compile it to use
        Pattern descriptionPattern = Pattern.compile("^[A-z0-9- ]{2,}$");
        Pattern packSizePattern = Pattern.compile("^[0-9gK]{1,}$");
        Pattern qtyPattern = Pattern.compile("[0-9]{1,}$");
        Pattern unitPricePattern = Pattern.compile("[1-9][0-9]*(.[0-9]{2})?$");

        map.put(txtDescription, descriptionPattern);
        map.put(txtPackSize, packSizePattern);
        map.put(txtQtyOnHand, qtyPattern);
        map.put(txtUnitPrice, unitPricePattern);

        try {
            loadAllItemDetails();

            txtItemCode.setText(generateNewItemCode());

            txtItemCode.setEditable(false);

            txtDescription.requestFocus();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String generateNewItemCode() throws SQLException, ClassNotFoundException {
        return manageItemBO.generateNewItemCode();

    }

    private void loadAllItemDetails() throws SQLException, ClassNotFoundException {
        tblItemDetails.getItems().clear();
        ArrayList<ItemDTO> allItems = manageItemBO.getAllItems();
        for (ItemDTO dto : allItems) {
            tblItemDetails.getItems().add(new ItemTM(dto.getItemCode(), dto.getDescription(), dto.getPackSize(), dto.getUnitPrice(), dto.getQtyOnHand()));
        }
    }

    public void btnSaveItemOnAction(ActionEvent actionEvent) {
        if (btnSave.getText().equalsIgnoreCase("Update")) {
            try {
                // UPDATE ITEM

                boolean updated = manageItemBO.updateItem(new ItemDTO(txtItemCode.getText(), txtDescription.getText(), txtPackSize.getText(), new BigDecimal(txtUnitPrice.getText()), Integer.parseInt(txtQtyOnHand.getText())));
                if (updated) {
                    new Alert(Alert.AlertType.INFORMATION, "Updated!").show();

                    ItemTM itemTM1 = tblItemDetails.getItems().stream().filter(itemTM -> itemTM.getItemCode().equalsIgnoreCase(txtItemCode.getText())).findFirst().get();
                    itemTM1.setDescription(txtDescription.getText());
                    itemTM1.setPackSize(txtPackSize.getText());
                    itemTM1.setUnitPrice(new BigDecimal(txtUnitPrice.getText()));
                    itemTM1.setQtyOnHand(Integer.parseInt(txtQtyOnHand.getText()));

                    txtItemCode.setText(generateNewItemCode());
                    clearTextFields();
                    btnSave.setText("Register");
                    tblItemDetails.getSelectionModel().clearSelection();
                    txtDescription.requestFocus();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            // SAVE ITEM
            saveItem();
        }
        tblItemDetails.refresh();
    }

    private void saveItem(){
        try {
            boolean saved = manageItemBO.saveItem(new ItemDTO(txtItemCode.getText(), txtDescription.getText(), txtPackSize.getText(), new BigDecimal(txtUnitPrice.getText()), Integer.parseInt(txtQtyOnHand.getText())));
            if (saved) {
                new Alert(Alert.AlertType.INFORMATION, "Registered Item!").show();
                tblItemDetails.getItems().add(new ItemTM(txtItemCode.getText(), txtDescription.getText(), txtPackSize.getText(), new BigDecimal(txtUnitPrice.getText()), Integer.parseInt(txtQtyOnHand.getText())));
                txtItemCode.setText(generateNewItemCode());
                clearTextFields();
                txtDescription.setStyle("-fx-border-color: white");
                txtPackSize.setStyle("-fx-border-color: white");
                txtQtyOnHand.setStyle("-fx-border-color: white");
                txtUnitPrice.setStyle("-fx-border-color: white");
                txtDescription.requestFocus();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tblItemDetails.refresh();
    }

    private void clearTextFields() {
        txtDescription.clear();
        txtPackSize.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
    }

    public void backToHomeOnAction(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) manageItemFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AdminDashBoardForm.fxml"))));
        stage.show();
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map, btnSave);
        //if the enter key pressed
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(map, btnSave);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();// if there is a error just focus it
            } else if (response instanceof Boolean) {
                saveItem();
            }
        }
    }
}
