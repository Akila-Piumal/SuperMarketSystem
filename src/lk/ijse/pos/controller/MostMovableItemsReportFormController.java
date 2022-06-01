package lk.ijse.pos.controller;

import javafx.animation.FadeTransition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.Custom.MostMovableItemsBO;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.util.Animation;
import lk.ijse.pos.view.tdm.MovableTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class MostMovableItemsReportFormController {
    public TableView<MovableTM> tblMostMovable;

    private final MostMovableItemsBO mostMovableItemsBO= (MostMovableItemsBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MOSTMOVABLE);
    public AnchorPane mostMovableFormContext;

    public void initialize(){
        Animation.windowAnimation(mostMovableFormContext);

        tblMostMovable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("itemCode"));
        tblMostMovable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("description"));
        tblMostMovable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("unitPrice"));
        tblMostMovable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("qtyOnHand"));
        tblMostMovable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("salesCount"));
        tblMostMovable.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("income"));


        setMostMovableDetails();
    }

    private void setMostMovableDetails() {
        try {
            ArrayList<CustomDTO> mostMovableItemDetails = mostMovableItemsBO.getMostMovableItemDetails();
            for (CustomDTO dto : mostMovableItemDetails) {
                tblMostMovable.getItems().add(new MovableTM(dto.getItemCode(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand(),dto.getOrderCount(),dto.getTotal()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
