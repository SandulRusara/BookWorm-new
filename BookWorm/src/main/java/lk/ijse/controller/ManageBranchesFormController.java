package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.Custom.AdminBo;
import lk.ijse.bo.Custom.BranchBo;
import lk.ijse.bo.Custom.impl.AdminBoImpl;
import lk.ijse.bo.Custom.impl.BranchBoImpl;
import lk.ijse.dto.AdminDto;
import lk.ijse.dto.BranchDto;
import lk.ijse.regex.RegexPattern;
import lk.ijse.tm.BranchTm;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class ManageBranchesFormController {

    public TableColumn<?,?> colAdmin;
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<BranchTm> tblBranch;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private JFXButton txtDelete;

    @FXML
    private TextField txtName;
    private int branchId;
    private int adminId;
    private BranchBo branchBo = new BranchBoImpl();
    private AdminBo adminBo = new AdminBoImpl();
    public  void  initialize(){
        setCellValueFactory();
        setTableValues();
        tableListener();
    }

    private void setCellValueFactory() {
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colAdmin.setCellValueFactory(new PropertyValueFactory<>("adminName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
    private void tableListener() {
        tblBranch.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            setData( newValue);
        });
    }

    private void setData(BranchTm newValue) {
        txtContact.setText(newValue.getContact());
        txtAddress.setText(newValue.getAddress());
        txtName.setText(newValue.getName());
        try {
            BranchDto branchDto = branchBo.getBranch(newValue.getName());
            branchId = branchDto.getBranchId();
            adminId = branchDto.getAdminId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (validation()){
            var dto = new BranchDto(txtName.getText(),txtContact.getText(),txtAddress.getText(),AdminProfileFormController.id);

            try {
                branchBo.saveBranch(dto);
                new Alert(Alert.AlertType.CONFIRMATION,"New Branch Added").show();
                setTableValues();
                tblBranch.refresh();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,"Failed to Added Branch").show();
            }
        }


    }

    public boolean validation(){
        if (!(Pattern.matches(String.valueOf(RegexPattern.getNamePattern()),txtName.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid name").show();
            return  false;
        }
        if (!(Pattern.matches(String.valueOf(RegexPattern.getAddressPattern()),txtAddress.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid Address").show();
            return  false;
        }
        if (!(Pattern.matches(String.valueOf(RegexPattern.getMobilePattern()),txtContact.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid Contact Information").show();
            return  false;
        }


        return  true;

    }

    @FXML
    void txtDeleteAction(ActionEvent event) {
        try {
            branchBo.deleteBranch(branchId);
            new Alert(Alert.AlertType.CONFIRMATION,"Branch Deleted").show();
            setTableValues();
            tblBranch.refresh();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Failed to delete the branch").show();
        }


    }

    @FXML
    void txtUpdateOnAction(ActionEvent event) {
        if (validation()){
            try {
                branchBo.updateBranch(new BranchDto(branchId,txtName.getText(),txtContact.getText(),txtAddress.getText(),adminId));
                new Alert(Alert.AlertType.CONFIRMATION,"Branch updated").show();
                setTableValues();
                tblBranch.refresh();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,"Failed to update the branch").show();

            }

        }


    }
    public void setTableValues(){
        ObservableList<BranchTm> observableList = FXCollections.observableArrayList();
        try {
            List<BranchDto> branchDtoList = branchBo.getAllBranches();
            for (BranchDto dto : branchDtoList){
                AdminDto dto1 = adminBo.getAdminById(dto.getAdminId());

                observableList.add(new BranchTm(dto.getBranchName(), dto.getContact(), dto.getAddress(), dto1.getName()));
            }
            tblBranch.getItems().clear();
            tblBranch.setItems(observableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}