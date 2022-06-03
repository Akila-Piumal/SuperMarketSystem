package lk.ijse.pos.controller;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.Custom.AnnualIncomeBO;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.util.Animation;
import lk.ijse.pos.view.tdm.IncomeTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class AnnualIncomeReportFormController {
    private final AnnualIncomeBO annualIncomeBO = (AnnualIncomeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ANNUALINCOME);
    public TableView<IncomeTM> tblIncomeDetails;
    public AnchorPane annualIncomeFormContext;

    public void initialize() {
        Animation.windowAnimation(annualIncomeFormContext);

        tblIncomeDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("yearAndMonth"));
        tblIncomeDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("orderCount"));
        tblIncomeDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("income"));

        setIncomeDetails();
    }

    private void setIncomeDetails() {
        try {
            ArrayList<CustomDTO> annualIncomeDetails = annualIncomeBO.getAnnualIncomeDetails();
            for (CustomDTO detail : annualIncomeDetails) {
                tblIncomeDetails.getItems().add(new IncomeTM(detail.getYearAndMonth(), detail.getOrderCount(), detail.getTotal()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
