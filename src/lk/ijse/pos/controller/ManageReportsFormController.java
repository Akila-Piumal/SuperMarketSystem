package lk.ijse.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class ManageReportsFormController {


    public AnchorPane reportsFormContext;

    public void dailyIncomeReportOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DailyIncomeReport");
    }

    public void monthlyIncomeReportOnAction(ActionEvent actionEvent) throws IOException {
        setUi("MonthlyIncomeReport");
    }

    public void annualIncomeReportOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AnnualIncomeReport");
    }

    public void mostMovableItemReportOnAction(ActionEvent actionEvent) throws IOException {
        setUi("MostMovableItemsReport");
    }

    public void LeastMovableItemReportOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LeastMovableItemsReport");
    }

    private void setUi(String URI) throws IOException {
        reportsFormContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/" + URI + "Form.fxml"));
        reportsFormContext.getChildren().add(parent);
    }
}
