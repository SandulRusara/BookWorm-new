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
import lk.ijse.bo.Custom.AdminBo;
import lk.ijse.bo.Custom.impl.AdminBoImpl;
import lk.ijse.dto.AdminDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminLoginFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField showTextPw;

    @FXML
    private PasswordField hiddenTxtPassword;

    @FXML
    private CheckBox checkBoxPw;

    @FXML
    private JFXButton btnLogIn;

    private AdminBo adminBo = new AdminBoImpl();

    @FXML
    void btnLogInOnAction(ActionEvent event) {

        login();

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
            List<AdminDto> adminDtoList = adminBo.getAllAdmins();
            L: for (AdminDto dto : adminDtoList){
                if (dto.getUserName().equals(username) && dto.getPassword().equals(pw) || dto.getPassword().equals(pw1)){
                    AnchorPane anchorPane = null;
                    try {
                        anchorPane = FXMLLoader.load(getClass().getResource("/view/Admin_view/Admin_dashboard-form.fxml"));
                        Scene scene = new Scene(anchorPane);
                        Stage stage = (Stage) root.getScene().getWindow();
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

    private void fillProfileData() {
        AdminProfileFormController.setValue(txtUserName.getText());
    }

}
