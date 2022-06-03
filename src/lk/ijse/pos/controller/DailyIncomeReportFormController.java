package lk.ijse.pos.controller;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.Custom.DailyIncomeBO;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.util.Animation;
import lk.ijse.pos.view.tdm.IncomeTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class DailyIncomeReportFormController {

    private final DailyIncomeBO dailyIncomeBO = (DailyIncomeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DAILYINCOME);
    public TableView<IncomeTM> tblIncomeDetails;
    public AnchorPane DailyIncomeFormContext;

    public void initialize() {
        Animation.windowAnimation(DailyIncomeFormContext);

        tblIncomeDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("date"));
        tblIncomeDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("orderCount"));
        tblIncomeDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("income"));

        setIncomeDetails();
    }

    private void setIncomeDetails() {
        try {
            ArrayList<CustomDTO> dailyIncomeDetails = dailyIncomeBO.getDailyIncomeDetails();
            for (CustomDTO dailyIncomeDetail : dailyIncomeDetails) {
                tblIncomeDetails.getItems().add(new IncomeTM(dailyIncomeDetail.getDate(), dailyIncomeDetail.getOrderCount(), dailyIncomeDetail.getTotal()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
