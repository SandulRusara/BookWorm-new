package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.Custom.AdminBo;
import lk.ijse.bo.Custom.impl.AdminBoImpl;
import lk.ijse.dto.AdminDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class AdminProfileFormController {

    @FXML
    private AnchorPane pane2;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtPw;

    @FXML
    private TextField txtEmail;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnAddAdmin;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnSaveChanges;

    public static int id;
    private static AdminBo adminBo = new AdminBoImpl();

    public void initialize() {
        setFormValues();
    }

    private void setFormValues() {
        try {
            AdminDto dto = adminBo.getUserById(id);
            txtEmail.setText(dto.getEmail());
            txtPw.setText(dto.getPassword());
            txtName.setText(dto.getName());
            txtUserName.setText(dto.getUserName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        formType();
    }

    private void formType() {
        txtUserName.setEditable(false);
        txtPw.setEditable(false);
        txtEmail.setEditable(false);
        txtName.setEditable(false);
        btnAddAdmin.setVisible(true);
        btnDelete.setVisible(true);
        btnLogOut.setVisible(true);
        btnUpdate.setVisible(true);
        btnSaveChanges.setVisible(false);
    }

    @FXML
    void btnAddAdminOnAction(ActionEvent event) {
        Parent fxml = null;
        try {
            fxml = FXMLLoader.load(getClass().getResource("/view/Admin_view/Add_new_admin_form.fxml"));
            pane2.getChildren().removeAll();

            pane2.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Do you want to log out?");
        alert.setContentText("Choose your option.");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            try {
                adminBo.deleteAdmin(id);
                new Alert(Alert.AlertType.INFORMATION, "Your Account is deleted");
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get the current stage
                try {
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Admin_view/Admin_login_form.fxml")))); // load and set the scene for login form
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.setTitle("Login form");
                stage.centerOnScreen();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }


    @FXML
    void btnLogOutOnAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Do you want to log out?");
        alert.setContentText("Choose your option.");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yesButton) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Admin_view/Admin_login_form.fxml"))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setTitle("Login form");
            stage.centerOnScreen();
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        txtUserName.setEditable(true);
        txtPw.setEditable(true);
        txtEmail.setEditable(true);
        txtName.setEditable(true);
        btnAddAdmin.setVisible(false);
        btnDelete.setVisible(false);
        btnLogOut.setVisible(false);
        btnUpdate.setVisible(false);
        btnSaveChanges.setVisible(true);


    }

    private static int getAdminId(String userName) throws SQLException {
        AdminDto dto = adminBo.getAdminByEmail(userName);
        return dto.getAdminId();
    }

    public void btnSaveChangesOnAction(ActionEvent actionEvent) {
        try {

            var dto = new AdminDto(id, txtName.getText(), txtEmail.getText(), txtUserName.getText(), txtPw.getText());
            adminBo.updateAdmin(dto);


            new Alert(Alert.AlertType.CONFIRMATION, "Your data Updated Successfully").show();
            formType();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static void setValue(String text) {
        try {
            id = getAdminId(text);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}