package lk.ijse.pos.controller;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.Custom.LeastMovableItemsBO;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.util.Animation;
import lk.ijse.pos.view.tdm.MovableTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class LeastMovableItemsReportFormController {
    private final LeastMovableItemsBO leastMovableItemsBO = (LeastMovableItemsBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LEASTMOVABLE);
    public TableView<MovableTM> tblLeastMovable;
    public AnchorPane leastMovableFormContext;

    public void initialize() {
        Animation.windowAnimation(leastMovableFormContext);

        tblLeastMovable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("itemCode"));
        tblLeastMovable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("description"));
        tblLeastMovable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("unitPrice"));
        tblLeastMovable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("qtyOnHand"));
        tblLeastMovable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("salesCount"));
        tblLeastMovable.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("income"));

        setLeastMovableDetails();
    }

    private void setLeastMovableDetails() {
        try {
            ArrayList<CustomDTO> leastMovableItemsDetails = leastMovableItemsBO.getLeastMovableItemsDetails();
            for (CustomDTO dto : leastMovableItemsDetails) {
                tblLeastMovable.getItems().add(new MovableTM(dto.getItemCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand(), dto.getOrderCount(), dto.getTotal()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
