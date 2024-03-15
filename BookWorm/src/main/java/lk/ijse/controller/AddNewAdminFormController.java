package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.Custom.AdminBo;
import lk.ijse.bo.Custom.impl.AdminBoImpl;
import lk.ijse.dto.AdminDto;
import lk.ijse.regex.RegexPattern;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AddNewAdminFormController {

    public AnchorPane pane2;
    @FXML
    private JFXButton btnAddAdmin;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPw;

    @FXML
    private TextField txtUserName;
    private AdminBo adminBo = new AdminBoImpl();

    @FXML
    void btnAddAdminOnAction(ActionEvent event) {
        if (validation()){

            try {
                adminBo.saveAdmin(new AdminDto(txtName.getText(),txtEmail.getText(),txtUserName.getText(),txtPw.getText()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            new Alert(Alert.AlertType.CONFIRMATION,"Admin Adeed Successfully").show();
            Parent fxml = null;
            try {
                fxml = FXMLLoader.load(getClass().getResource("/view/Admin_view/Admin_profile_form.fxml"));
                pane2.getChildren().removeAll();

                pane2.getChildren().setAll(fxml);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public boolean validation(){
        if (!(Pattern.matches(String.valueOf(RegexPattern.getNamePattern()),txtName.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid name").show();
            return  false;
        }

        if (!(Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",txtPw.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid Password type");
            return  false;
        }

        if (!(Pattern.matches(String.valueOf(RegexPattern.getEmailPattern()),txtEmail.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid Email").show();
            return  false;
        }

        return  true;

    }

}