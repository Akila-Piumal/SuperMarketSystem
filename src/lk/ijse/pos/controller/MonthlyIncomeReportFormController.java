package lk.ijse.pos.controller;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.Custom.MonthlyIncomeBO;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.view.tdm.IncomeTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class MonthlyIncomeReportFormController {
    public TableView<IncomeTM> tblIncomeDetails;

    private final MonthlyIncomeBO monthlyIncomeBO= (MonthlyIncomeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MONTHLYINCOME);

    public void initialize(){
        tblIncomeDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("yearAndMonth"));
        tblIncomeDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("orderCount"));
        tblIncomeDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("income"));

        setIncomeDetails();
    }

    private void setIncomeDetails() {
        try {
            ArrayList<CustomDTO> monthlyIncomeDetails = monthlyIncomeBO.getMonthlyIncomeDetails();
            for (CustomDTO detail : monthlyIncomeDetails) {
                tblIncomeDetails.getItems().add(new IncomeTM(detail.getYearAndMonth(),detail.getOrderCount(),detail.getTotal()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
