package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.Custom.TransactionBo;
import lk.ijse.bo.Custom.UserBo;
import lk.ijse.bo.Custom.impl.TransactionBoImpl;
import lk.ijse.bo.Custom.impl.UserBoImpl;
import lk.ijse.dto.TransactionDto;
import lk.ijse.tm.TransactionTm;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static lk.ijse.controller.UserLoginFormController.userId;

public class UserHistoryFormController {

    @FXML
    private TableColumn<?, ?> colBookTitle;

    @FXML
    private TableColumn<?, ?> colBorrowedDate;

    @FXML
    private TableColumn<?, ?> colDueDate;

    @FXML
    private TableColumn<?, ?> colReturnedDate;

    @FXML
    private TableView<TransactionTm> tblHistory;
    private TransactionBo transactionBo = new TransactionBoImpl();
    private UserBo userBo = new UserBoImpl();
    private ObservableList<TransactionTm> observableList = FXCollections.observableArrayList();


    public void initialize(){

        setCellValuefactory();
        loadAllTransactionByUser();


    }

    private void loadAllTransactionByUser() {
        try {
            List<TransactionDto> dtoList = transactionBo.getByUser(userId);
            for (TransactionDto dto : dtoList){
                if (dto.getReturn().equals(true)) {
                    observableList.add(new TransactionTm(
                            dto.getBookId(),
                            formatDateTime(dto.getReturnedDate()),
                            formatDateTime(dto.getBorrowed()),
                            (dto.getDueDate())));
                } else {
                    observableList.add(new TransactionTm(
                            dto.getBookId(),
                            "Not returned Yet",
                            formatDateTime(dto.getBorrowed()),
                            (dto.getDueDate())));
                }

            }
            tblHistory.getItems().clear();
            tblHistory.setItems(observableList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValuefactory() {
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colBorrowedDate.setCellValueFactory(new PropertyValueFactory<>("borrow"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colReturnedDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }
    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(formatter);
    }
}

