package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import lk.ijse.bo.Custom.TransactionBo;
import lk.ijse.bo.Custom.impl.TransactionBoImpl;
import lk.ijse.dto.TransactionDto;
import lk.ijse.tm.TransactionTm;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AllTransactionHistoryFormController {

    public AnchorPane root;
    @FXML
    private TableColumn<?, ?> ColBookId;

    @FXML
    private TableColumn<?, ?> ColBookName;

    @FXML
    private JFXButton btnOverdue;

    @FXML
    private TableColumn<?, ?> colBranchId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colTransactionId;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private ComboBox<String> combType;

    @FXML
    private TableView<TransactionTm> tblTransaction;

    @FXML
    private TextField txtSearch;
    private TransactionBo transactionBo = new TransactionBoImpl();
    private ObservableList<TransactionTm> observableList = FXCollections.observableArrayList();
    private ObservableList<TransactionTm> userFiltered = FXCollections.observableArrayList();
    private ObservableList<TransactionTm> typeFiltered = FXCollections.observableArrayList();

    public void initialize(){
        setCellValueFactory();
        loadAllTransactions();
        setComboBoxValues();
    }

    private void setComboBoxValues() {
        ObservableList<String> list = FXCollections.observableArrayList("All","Not returned Yet");
        combType.setItems(list);

    }

    private void loadAllTransactions() {
        try {
            List<TransactionDto> dtos = transactionBo.getAll();
            for (TransactionDto dto : dtos){
                if (dto.getReturn().equals(true)) {
                    observableList.add(new TransactionTm(
                            "T00" + dto.getId(),
                            "B00" + dto.getBookname(),
                            dto.getBookId(),
                            dto.getUserName(),
                            dto.getBranchName(),
                            formatDateTime(dto.getBorrowed()),
                            formatDateTime(dto.getReturnedDate())));
                } else {
                    observableList.add(new TransactionTm(
                            "T00" + dto.getId(),
                            "B00" + dto.getBookname(),
                            dto.getBookId(),
                            dto.getUserName(),
                            dto.getBranchName(),
                            formatDateTime(dto.getBorrowed()),
                            "Not returned yet"));
                }

            }
            tblTransaction.getItems().clear();
            tblTransaction.setItems(observableList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {
        colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colType.setCellValueFactory(new PropertyValueFactory<>("borrow"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colBranchId.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        ColBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        ColBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
    }

    @FXML
    void btnOverdueAction(ActionEvent event) {
        Parent fxml = null;
        try {
            fxml = FXMLLoader.load(getClass().getResource("/view/Admin_view/Unreturned_book_form.fxml"));
            root.getChildren().removeAll();

            root.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void combTypeOnAction(ActionEvent event) {
        typeFiltered.clear();
        String selected = combType.getValue();
        if (selected.equals("All")){
            typeFiltered.addAll(observableList);
        }else if (selected.equals("Not returned Yet")) {
            for (TransactionTm transactionTm : observableList) {
                if (transactionTm.getReturnDate().equals("Not returned yet")) {
                    typeFiltered.add(transactionTm); // add non returned transactions
                }
            }
        }
        tblTransaction.getItems().setAll(typeFiltered);
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        userFiltered.clear();
        for (TransactionTm transactionTm : observableList) {
            if (transactionTm.getUserName().equals(txtSearch.getText())) {
                userFiltered.add(transactionTm); // add matching transactions
            }
        }
        tblTransaction.getItems().setAll(userFiltered);
    }
    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(formatter);
    }

}
