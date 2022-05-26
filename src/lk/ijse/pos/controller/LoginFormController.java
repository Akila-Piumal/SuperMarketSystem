package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginFormController {
    public JFXTextField txtUserName;
    public JFXPasswordField pwdPassword;
    public CheckBox cbShowPassword;
    public JFXTextField txtPassword;

    public void initialize(){
        txtPassword.textProperty().bind(pwdPassword.textProperty());
        txtPassword.visibleProperty().bind(cbShowPassword.selectedProperty());
        pwdPassword.visibleProperty().bind(cbShowPassword.selectedProperty().not());
    }

    public void abcd(){

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

    private void loginToSystem() {

    }

    public void btnLoginOnAction(ActionEvent actionEvent) {

    }
}
