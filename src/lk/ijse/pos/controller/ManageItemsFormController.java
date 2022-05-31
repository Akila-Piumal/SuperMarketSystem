package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.Custom.ManageItemBO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.view.tdm.ItemTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

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

    public void initialize() {

        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), manageItemFormContext);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

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

        try {
            loadAllItemDetails();

            String newItemCode = generateNewItemCode();

            txtItemCode.setText(newItemCode);

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

                    txtItemCode.clear();
                    txtDescription.clear();
                    txtPackSize.clear();
                    txtUnitPrice.clear();
                    txtQtyOnHand.clear();
                    btnSave.setText("Register");
                    tblItemDetails.getSelectionModel().clearSelection();
                    txtItemCode.setText(generateNewItemCode());
                    txtDescription.requestFocus();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            // SAVE ITEM

            try {
                boolean saved = manageItemBO.saveItem(new ItemDTO(txtItemCode.getText(), txtDescription.getText(), txtPackSize.getText(), new BigDecimal(txtUnitPrice.getText()), Integer.parseInt(txtQtyOnHand.getText())));
                if (saved) {
                    new Alert(Alert.AlertType.INFORMATION, "Registered Item!").show();
                    tblItemDetails.getItems().add(new ItemTM(txtItemCode.getText(), txtDescription.getText(), txtPackSize.getText(), new BigDecimal(txtUnitPrice.getText()), Integer.parseInt(txtQtyOnHand.getText())));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        tblItemDetails.refresh();
    }

    public void backToHomeOnAction(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) manageItemFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AdminDashBoardForm.fxml"))));
        stage.show();
    }
}
