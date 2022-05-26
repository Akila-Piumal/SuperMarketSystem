package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.Custom.LoginBO;
import lk.ijse.pos.bo.SuperBO;

import java.sql.SQLException;

public class LoginFormController {
    public JFXTextField txtUserName;
    public JFXPasswordField pwdPassword;
    public CheckBox cbShowPassword;
    public JFXTextField txtPassword;

    LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);

    public void initialize(){


        txtPassword.textProperty().bind(pwdPassword.textProperty());
        txtPassword.visibleProperty().bind(cbShowPassword.selectedProperty());
        pwdPassword.visibleProperty().bind(cbShowPassword.selectedProperty().not());
    }

    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (txtUserName.getText() != null) {
                pwdPassword.requestFocus();
                if (pwdPassword.getText().length()>0){
                    loginToSystem();
                }
            }
        }
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        loginToSystem();
    }

    private void loginToSystem() {
        try {
            if (loginBO.loginToSystem(txtUserName.getText(), pwdPassword.getText())) {
                if (txtUserName.getText().equalsIgnoreCase("Cashier")){
                    //load cashier ui
                    System.out.println("Cashier");
                }else if (txtUserName.getText().equalsIgnoreCase("Admin")){
                    // load admin ui
                    System.out.println("admin");
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"Invalid User name or password.").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
