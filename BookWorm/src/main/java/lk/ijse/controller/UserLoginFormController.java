package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.Custom.UserBo;
import lk.ijse.bo.Custom.impl.UserBoImpl;
import lk.ijse.dto.UserDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserLoginFormController {

    public AnchorPane root1;
    @FXML
    private JFXButton btnLogIn;

    @FXML
    private JFXButton btnRegister;

    @FXML
    private CheckBox checkBoxPw;

    @FXML
    private PasswordField hiddenTxtPassword;

    @FXML
    private TextField showTextPw;

    @FXML
    private TextField txtUserName;
    public static int userId;
    private UserBo userBo = new UserBoImpl();

    @FXML
    void btnLogInOnAction(ActionEvent event) {

        login();

    }

    private void fillProfileData() {
        UserProfileFormController.setValue(txtUserName.getText());
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {

        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/view/User_view/User_register_form.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = (Stage) root1.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void checkBoxPwOnAction(ActionEvent event) {
        if (checkBoxPw.isSelected()){
            showTextPw.setText(hiddenTxtPassword.getText());
            showTextPw.setVisible(true);
            hiddenTxtPassword.setVisible(false);
        }
        else{
            hiddenTxtPassword.setText(showTextPw.getText());
            hiddenTxtPassword.setVisible(true);
            showTextPw.setVisible(false);
        }

    }

    void login(){
        String username = txtUserName.getText();
        String pw = hiddenTxtPassword.getText();
        String pw1 = showTextPw.getText();
        try {
            List<UserDto> userDtos = userBo.getAllUsers();
            L:for (UserDto dto : userDtos){
                if (dto.getEmail().equals(username) && dto.getPassword().equals(pw) || dto.getPassword().equals(pw1)){
                    userId = dto.getUserId();
                    AnchorPane anchorPane = null;
                    try {
                        anchorPane = FXMLLoader.load(getClass().getResource("/view/User_view/User_dashboard_form.fxml"));
                        Scene scene = new Scene(anchorPane);
                        Stage stage = (Stage) root1.getScene().getWindow();
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        fillProfileData();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    new Alert(Alert.AlertType.CONFIRMATION,"Login Successful").show();
                    break L;

                }
                else {
                    new Alert(Alert.AlertType.ERROR,"your Login details are incorrect").show();
                    break L;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


