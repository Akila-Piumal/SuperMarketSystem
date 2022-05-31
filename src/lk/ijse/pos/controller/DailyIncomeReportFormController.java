package lk.ijse.pos.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.Custom.DailyIncomeBO;
import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.view.tdm.IncomeTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DailyIncomeReportFormController {

    public TableView<IncomeTM> tblIncomeDetails;
    private final DailyIncomeBO dailyIncomeBO = (DailyIncomeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DAILYINCOME);

    public void initialize(){
        tblIncomeDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("date"));
        tblIncomeDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("orderCount"));
        tblIncomeDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("income"));

        setIncomeDetails();
    }

    private void setIncomeDetails() {
        try {
            ArrayList<CustomDTO> dailyIncomeDetails = dailyIncomeBO.getDailyIncomeDetails();
            for (CustomDTO dailyIncomeDetail : dailyIncomeDetails) {
                tblIncomeDetails.getItems().add(new IncomeTM(dailyIncomeDetail.getDate(),dailyIncomeDetail.getOrderCount(),dailyIncomeDetail.getTotal()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
