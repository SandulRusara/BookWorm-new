package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import jakarta.transaction.Transactional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import lk.ijse.bo.Custom.BookBo;
import lk.ijse.bo.Custom.TransactionBo;
import lk.ijse.bo.Custom.UserBo;
import lk.ijse.bo.Custom.impl.BookBoImpl;
import lk.ijse.bo.Custom.impl.TransactionBoImpl;
import lk.ijse.bo.Custom.impl.UserBoImpl;
import lk.ijse.dto.BookDto;
import lk.ijse.dto.TransactionDto;
import lk.ijse.tm.BookTm;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static lk.ijse.controller.UserDashboardFormController.bookDtos;
import static lk.ijse.controller.UserLoginFormController.userId;


public class BorrowBookFormController {

    public ImageView img1;
    @FXML
    private JFXButton btnBorrow;

    @FXML
    private TableColumn<?, ?> colAuthor;

    @FXML
    private TableColumn<?, ?> colBookTitle;

    @FXML
    private TableColumn<?, ?> colBtnGet;

    @FXML
    private TableColumn<?, ?> colGener;

    @FXML
    private ComboBox<String> combPeriod;

    @FXML
    private Label lblDueDate;

    @FXML
    private TableView<BookTm> tblBook;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtBookTitle;

    @FXML
    private TextField txtGener;

    @FXML
    private TextField txtSEarch;
    private BookBo bookBo = new BookBoImpl();
    private UserBo userBo = new UserBoImpl();
    private TransactionBo transactionBo = new TransactionBoImpl();

    private  ObservableList<BookTm> observableList = FXCollections.observableArrayList();
    public void initialize() {
        setcmbValue();
        setCellValueFactory();
        loadAllBooks();
        tableListener();
        textFieldFocus();
    }

    private void tableListener() {
        tblBook.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(BookTm newValue) {
        txtBookTitle.setText(newValue.getTitle());
        txtAuthor.setText(newValue.getAuthor());
        txtGener.setText(newValue.getCategory());
    }

    private void loadAllBooks() {

        for (BookDto dto : bookDtos) {
            observableList.add(new BookTm(dto.getTitle(), dto.getAuthor(), dto.getCategory(), getButton()));
        }
        tblBook.getItems().clear();
        tblBook.setItems(observableList);

    }

    private JFXButton getButton() {
        JFXButton button = new JFXButton("Get");
        button.setStyle("-fx-background-color: rgba(255,216,118,0.9);");

        return button;
    }

    private void setCellValueFactory() {
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGener.setCellValueFactory(new PropertyValueFactory<>("category"));
        colBtnGet.setCellValueFactory(new PropertyValueFactory<>("button"));

    }

    private void setcmbValue() {
        ObservableList<String> days = FXCollections.observableArrayList("7 Days", "14 Days", "21 Days");
        combPeriod.setItems(days);
    }

    @FXML
    void btnBorrowOnAction(ActionEvent event) {

        if (txtBookTitle.getText()!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Do you want to Borrow " + txtBookTitle.getText() + " book");
            alert.setContentText("Choose your option.");

            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");

            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {

                borrowBook();
            }
        }else{
            new Alert(Alert.AlertType.INFORMATION,"please select a book").show();
        }

    }
    @Transactional
    private void borrowBook() {

        String bookTitle = txtBookTitle.getText() ;
        if (bookTitle != null) {
            TransactionDto dto = new TransactionDto(userId, bookTitle, LocalDate.now(), lblDueDate.getText(), false);
            // Assuming no return type for saveTransactiondata
            BookDto bookDto = null;
            try {
                transactionBo.saveTransactiondata(userId, bookTitle, dto);
                bookDto = bookBo.getBook(bookTitle);
                bookDto.setAvailability("Unavailable");
                bookBo.updateBook(bookDto);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            // Assuming no return type for updateBook
            new Alert(Alert.AlertType.CONFIRMATION, "Your book Borrow process is successful").show();
        }

    }

    @FXML
    void txtSearch(ActionEvent event) {
        String bookName = txtSEarch.getText();
        L1:for (BookTm tm : observableList){
            if (tm.getTitle().equals(bookName)){
                BookDto dto = null;
                try {
                    dto = bookBo.getBook(bookName);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (dto!=null){
                    txtGener.setText(dto.getCategory());
                    txtAuthor.setText(dto.getAuthor());
                    txtBookTitle.setText(dto.getTitle());
                    break L1;
                }

            }else {
                new Alert(Alert.AlertType.INFORMATION,"There is no book in this name").show();
                break L1;
            }
        }

    }


    public void setValues(BookDto dto) {
        txtBookTitle.setText(dto.getTitle());
        txtAuthor.setText(dto.getAuthor());
        txtGener.setText(dto.getCategory());
    }

    public void combPeriodOnAction(ActionEvent actionEvent) {
        String selectedDate = combPeriod.getValue();
        calculateDueDate(selectedDate);
    }

    private void calculateDueDate(String selectedDate) {
        int daysToAdd = 0;
        if (selectedDate.equals("7 Days")) {
            daysToAdd = 7;
        } else if (selectedDate.equals("14 Days")) {
            daysToAdd = 14;
        } else {
            daysToAdd = 21;
        }
        LocalDate dueDate = LocalDate.now().plusDays(daysToAdd);
        lblDueDate.setText(dueDate.toString());
    }

    void textFieldFocus() {
        txtSEarch.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                // TextField gained focus, hide the image
                img1.setVisible(false);
            } else {
                // TextField lost focus, show the image
                img1.setVisible(true);
            }
        });

    }
    private void clearFields() {
        txtBookTitle.clear();
        txtAuthor.clear();
        txtGener.clear();
        combPeriod.getSelectionModel().clearSelection();
        lblDueDate.setText("");
    }
}
