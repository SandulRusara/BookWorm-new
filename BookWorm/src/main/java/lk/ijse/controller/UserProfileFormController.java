package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.bo.Custom.UserBo;
import lk.ijse.bo.Custom.impl.UserBoImpl;
import lk.ijse.dto.UserDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class UserProfileFormController {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnLogOUt;

    @FXML
    private JFXButton btnSaveChanges;

    @FXML
    private JFXButton btnUpadte;

    @FXML
    private TextField txtEmail;

    @FXML
    public TextField txtName;

    @FXML
    private TextField txtPw;

    @FXML
    private TextField txtTele;
    private static String email;
    private static UserBo userBo = new UserBoImpl();
    public static int id;

    public void initialize(){
        setFormValues();
    }

    private void setFormValues() {
        try {
            UserDto dto = userBo.getUserById(id);
            txtEmail.setText(dto.getEmail());
            txtPw.setText(dto.getPassword());
            txtName.setText(dto.getName());
            txtTele.setText("U00"+id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        formType();
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
                userBo.deleteUser(id);
                new Alert(Alert.AlertType.INFORMATION,"Your Account is deleted");
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                try {
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/User_view/User_login_form.fxml"))));
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
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/User_view/User_login_form.fxml"))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setTitle("Login form");
            stage.centerOnScreen();
        }

    }

    @FXML
    void btnSaveChangesOnAction(ActionEvent event) {
        try {
            int userId = getUserId(email);
            var dto = new UserDto(userId,txtName.getText(),txtEmail.getText(),txtPw.getText());
            userBo.updateUser(dto);


            new Alert(Alert.AlertType.CONFIRMATION,"Your data Updated Successfully").show();
            formType();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        email = txtEmail.getText();
        btnSaveChanges.setVisible(true);
        txtEmail.setEditable(true);
        txtName.setEditable(true);
        txtPw.setEditable(true);
        txtTele.setEditable(true);


    }

    private static int getUserId(String email) throws SQLException {
        UserDto userDto = userBo.getUser(email);
        return userDto.getUserId();

    }
    private void formType(){
        btnSaveChanges.setVisible(false);
        txtTele.setEditable(false);
        txtPw.setEditable(false);
        txtName.setEditable(false);
        txtEmail.setEditable(false);
    }

    public static void setValue(String text) {
        System.out.println(text);
        try {
            id=  getUserId(text);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
