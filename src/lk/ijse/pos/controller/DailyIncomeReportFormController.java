package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.Custom.DailyIncomeBO;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.util.Animation;
import lk.ijse.pos.view.tdm.IncomeTM;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.ArrayList;

public class DailyIncomeReportFormController {

    private final DailyIncomeBO dailyIncomeBO = (DailyIncomeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DAILYINCOME);
    public TableView<IncomeTM> tblIncomeDetails;
    public AnchorPane DailyIncomeFormContext;
    public JFXButton btnReport;

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

    public void btnGetReportOnAction(ActionEvent actionEvent) {
        ObservableList<IncomeTM> tableRecords = tblIncomeDetails.getItems();
        try {
            JasperReport compiledReport= (JasperReport) JRLoader.loadObject(this.getClass().getResource("/lk/ijse/pos/Report/DailyIncomeReport.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, new JRBeanCollectionDataSource(tableRecords));
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
