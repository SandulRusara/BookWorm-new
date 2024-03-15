package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.Custom.BranchBo;
import lk.ijse.bo.Custom.TransactionBo;
import lk.ijse.bo.Custom.impl.BranchBoImpl;
import lk.ijse.bo.Custom.impl.TransactionBoImpl;
import lk.ijse.dto.BranchDto;
import lk.ijse.dto.TransactionDto;
import lk.ijse.tm.TransactionTm;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class UnreturnedBookFormController {

    @FXML
    private ComboBox<String> cmbBranch;

    @FXML
    private TableColumn<?, ?> colBook;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colOverdueDays;

    @FXML
    private TableView<TransactionTm> tblOverdueBooks;
    private TransactionBo transactionBo = new TransactionBoImpl();
    private BranchBo branchBo = new BranchBoImpl();
    private List<TransactionDto> dtoList;
    private ObservableList<TransactionTm> observableList = FXCollections.observableArrayList();
    private ObservableList<TransactionTm> filteredlist = FXCollections.observableArrayList();



    public void initialize(){
        loadAllOverDueBooks();
        setCellValueFactory();
        setComboBoxValues();

    }

    private void setComboBoxValues() {
        ObservableList<String> list = FXCollections.observableArrayList("All");
        try {
            List<BranchDto> dtos = branchBo.getAllBranches();
            for (BranchDto dto : dtos){
                list.add(dto.getBranchName());
            }
            cmbBranch.setItems(list);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colBook.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colOverdueDays.setCellValueFactory(new PropertyValueFactory<>("overDueDays"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("userName"));
    }

    private void loadAllOverDueBooks() {
        try {
            dtoList = transactionBo.getOverDue();
            for (TransactionDto dto : dtoList){
                long daysOverdue = Math.abs(Period.between(dto.getDueDate(), LocalDate.now()).getDays()); // Calculate absolute days
                observableList.add(new TransactionTm(dto.getUserName(), dto.getBookId(), dto.getDueDate(),daysOverdue));

            }
            tblOverdueBooks.getItems().clear();
            tblOverdueBooks.setItems(observableList);



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void cmbBranchOnActions(ActionEvent event) {
        String selectedBranch = cmbBranch.getValue();
        filteredlist.clear();
        if (selectedBranch.equals("All")) {
            filteredlist.addAll(observableList); // show all transactions
        } else {
            for (TransactionDto dto : dtoList) {
                if (dto.getBranchName().equals(selectedBranch)) {
                    long daysOverdue = Math.abs(Period.between(dto.getDueDate(), LocalDate.now()).getDays());
                    filteredlist.add(new TransactionTm(dto.getUserName(), dto.getBookId(), dto.getDueDate(), daysOverdue));
                }
            }
        }
        tblOverdueBooks.getItems().clear();
        tblOverdueBooks.setItems(filteredlist);
    }


}
