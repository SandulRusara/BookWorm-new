package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.Custom.BookBo;
import lk.ijse.bo.Custom.BranchBo;
import lk.ijse.bo.Custom.impl.BookBoImpl;
import lk.ijse.bo.Custom.impl.BranchBoImpl;
import lk.ijse.dto.BookDto;
import lk.ijse.dto.BranchDto;
import lk.ijse.regex.RegexPattern;
import lk.ijse.tm.BookTm;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class ManageBookFormController {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> cmbAvalability;

    @FXML
    private ComboBox<String> cmbBranch;

    @FXML
    private TableColumn<?, ?> colAuthor;

    @FXML
    private TableColumn<?, ?> colAvail;

    @FXML
    private TableColumn<?, ?> colCopies;

    @FXML
    private TableColumn<?, ?> collName;


    @FXML
    private TableView<BookTm> tblBook;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtGenere;

    @FXML
    private TextField txtTitle;
    private  int id;
    private BookBo bookBo = new BookBoImpl();
    private BranchBo branchBo = new BranchBoImpl();
    public void initialize(){
        setBranches();
        setStatus();
        setCellvalueFactory();
        tableListener();
        loadAllBooks();

    }


    private void setCellvalueFactory() {
        collName.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colAvail.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCopies.setCellValueFactory(new PropertyValueFactory<>("branch") );
    }
    private void tableListener() {
        tblBook.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
//            System.out.println(newValue);
            setData( newValue);
        });
    }

    private void setData(BookTm newValue) {
        txtGenere.setText(newValue.getCategory());
        txtAuthor.setText(newValue.getAuthor());
        txtTitle.setText(newValue.getTitle());
        cmbAvalability.setValue(newValue.getStatus());
        cmbBranch.setValue(newValue.getBranch());
        try {
            id = bookBo.getBook(txtTitle.getText()).getBookId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (validation()){
            try {
                BranchDto branchDto = branchBo.getBranch(cmbBranch.getValue());
                bookBo.saveBook(new BookDto(txtTitle.getText(),txtAuthor.getText(),txtGenere.getText(),  cmbAvalability.getValue(), branchDto.getBranchId()));
                new Alert(Alert.AlertType.CONFIRMATION,"New Book Added").show();
                loadAllBooks();
                tblBook.refresh();

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,"Failed to added new book").show();
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        try {
            bookBo.deleteBook(id);
            new Alert(Alert.AlertType.CONFIRMATION,"Book deleted").show();
            loadAllBooks();
            tblBook.refresh();
        } catch (SQLException e) {

            new Alert(Alert.AlertType.ERROR,"Failed to delete book").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (validation()){
            try {
                int branchId = branchBo.getBranch(cmbBranch.getValue()).getBranchId();
                bookBo.updateBook(new BookDto(id,txtTitle.getText(),txtAuthor.getText(),txtGenere.getText(),cmbAvalability.getValue(),branchId));
                loadAllBooks();
                tblBook.refresh();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public boolean validation(){
        if (!(Pattern.matches(String.valueOf(RegexPattern.getNamePattern()),txtTitle.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid BookTitle").show();
            return  false;
        }
        if (!(Pattern.matches(String.valueOf(RegexPattern.getNamePattern()),txtAuthor.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid name").show();
            return  false;
        }
        if (!(Pattern.matches(String.valueOf(RegexPattern.getNamePattern()),txtGenere.getText()))){
            new Alert(Alert.AlertType.ERROR,"Invalid Category").show();
            return  false;
        }


        return  true;

    }
    public void setBranches(){
        ObservableList<String> branchNames = FXCollections.observableArrayList();
        try {
            List<BranchDto> branches = branchBo.getAllBranches();
            for (BranchDto dto : branches){
                branchNames.add(dto.getBranchName());
            }
            cmbBranch.setItems(branchNames);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void setStatus(){
        ObservableList<String> status = FXCollections.observableArrayList("Available", "Unavailable");
        cmbAvalability.setItems(status);


    }
    public void loadAllBooks(){
        ObservableList<BookTm> observableList = FXCollections.observableArrayList();
        try {
            List<BookDto> bookDtos = bookBo.getAllBook();
            for (BookDto bookDto :bookDtos){
                BranchDto dto = branchBo.getBranchById(bookDto.getBranchId());
                observableList.add(new BookTm(bookDto.getTitle(), bookDto.getAuthor(), bookDto.getCategory(), bookDto.getAvailability(), dto.getBranchName()));
            }
            tblBook.getItems().clear();
            tblBook.setItems(observableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}