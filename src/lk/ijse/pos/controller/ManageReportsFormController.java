package lk.ijse.pos.controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import lk.ijse.pos.util.Animation;

import java.io.IOException;
import java.text.DateFormat;

public class ManageReportsFormController {


    public AnchorPane reportsFormContext;
    public AnchorPane SideContext;
    public AnchorPane manageReportsFormContext;

    public void initialize(){
        Animation.windowAnimation(manageReportsFormContext);
    }

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

    public void backToHomeOnAction(MouseEvent mouseEvent) throws IOException {
        Parent parent = SideContext.getParent();
        Stage stage = (Stage) parent.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AdminDashBoardForm.fxml"))));
    }
}
