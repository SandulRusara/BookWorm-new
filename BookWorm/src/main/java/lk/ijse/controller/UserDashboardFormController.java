package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.Custom.BookBo;
import lk.ijse.bo.Custom.BranchBo;
import lk.ijse.bo.Custom.UserBo;
import lk.ijse.bo.Custom.impl.BookBoImpl;
import lk.ijse.bo.Custom.impl.BranchBoImpl;
import lk.ijse.bo.Custom.impl.UserBoImpl;
import lk.ijse.dto.BookDto;
import lk.ijse.dto.UserDto;
import lk.ijse.tm.BookTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDashboardFormController {

    public AnchorPane pane2;
    public AnchorPane root;
    public ImageView img1;
    public Label lblName;
    @FXML
    private CheckBox CheckboxFilter;

    @FXML
    private JFXButton btnBorrow;

    @FXML
    private JFXButton btnDashbord;

    @FXML
    private JFXButton btnHistory;

    @FXML
    private JFXButton btnLibrary;

    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private TableColumn<?, ?> colAuthor;

    @FXML
    private TableColumn<?, ?> colBookName;

    @FXML
    private TableColumn<?, ?> colGener;


    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private ImageView imgUsername;

    @FXML
    private TableView<BookTm> tblDashboardBook;

    @FXML
    private TextField txtSearchBook;
    private int userId;
    private String branchName;
    private UserBo userBo = new UserBoImpl();
    private BookBo bookBo = new BookBoImpl();
    private BranchBo branchBo = new BranchBoImpl();
    private ObservableList<BookTm> observableList = FXCollections.observableArrayList();
    private   ObservableList<BookTm> filteredCategoryList = FXCollections.observableArrayList();
    private   ObservableList<BookTm> filteredyList = FXCollections.observableArrayList();
    public static List<BookDto> bookDtos;
    public void initialize(){
        setCellValueFactory();
        setUserId();
        textFieldFocus();
        loadAllBooks();
        setComboBoxValue();
        /*setUserName();*/

    }

    private void setUserName() {
        try {
            UserDto dto = userBo.getUserById(userId);
            lblName.setText(dto.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setComboBoxValue() {
        ObservableList<String> observableList = FXCollections.observableArrayList("All");
        Set<String> uniqueCategories = new HashSet<>();
        for (BookDto dto : bookDtos) {
            // check the category is not already in the set before adding it to the list
            if (uniqueCategories.add(dto.getCategory())) {
                observableList.add(dto.getCategory());
            }
        }
        cmbCategory.setItems(observableList);
    }

    private void setUserId() {
        userId = UserLoginFormController.userId;
        System.out.println(userId);
        try {
            branchName = userBo.getUserById(userId).getBranchName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllBooks() {

        try {
            bookDtos = bookBo.getBookByBranch(branchName);
            for (BookDto dto : bookDtos){
                observableList.add(new BookTm(dto.getTitle(), dto.getAuthor(), dto.getAvailability(), dto.getCategory()));
            }
            tblDashboardBook.getItems().clear();
            tblDashboardBook.setItems(observableList);
            System.out.println(observableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {

        colBookName.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colGener.setCellValueFactory(new PropertyValueFactory<>("category") );
    }

    @FXML
    void btnBorrowOnAction(ActionEvent event) {

        Parent fxml = null;
        try {
            fxml = FXMLLoader.load(getClass().getResource("/view/User_view/Borrow_book_form.fxml"));
            pane2.getChildren().removeAll();

            pane2.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {

        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/view/User_view/User_dashboard_form.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnHistoryOnAction(ActionEvent event) {
        Parent fxml = null;
        try {
            fxml = FXMLLoader.load(getClass().getResource("/view/User_view/User_history_form.fxml"));
            pane2.getChildren().removeAll();

            pane2.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnLibraryOnAction(ActionEvent event) {
        Parent fxml = null;
        try {
            fxml = FXMLLoader.load(getClass().getResource("/view/User_view/My_library_form.fxml"));
            pane2.getChildren().removeAll();

            pane2.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void checkBoxFilterOnAction(ActionEvent event) {
        boolean isChecked = CheckboxFilter.isSelected();


        if (!isChecked) {
            // display all books if checkbox is not checked
            filteredyList.clear();
            filteredyList.addAll(observableList);
        } else {
            filteredyList.clear();
            for (BookTm tm : observableList) {
                if (tm.getStatus().equals("Available")) { // filter based on availability
                    filteredyList.add(tm);
                }
            }
        }

        tblDashboardBook.setItems(filteredyList);
    }

    @FXML
    void cmbCategoryOnAction(ActionEvent event) {

        String selectedCategory = cmbCategory.getValue();

        if (selectedCategory.equals("All")) { // display all books if "All" is selected
            tblDashboardBook.getItems().setAll(observableList);
        } else {
            System.out.println(observableList);
            filteredCategoryList.clear(); // clear existing filtered data
            for (BookTm tm : observableList) {
                if (tm.getCategory().equals(selectedCategory)) {
                    filteredCategoryList.add(tm); // add books matching the selected category
                }
            }
            tblDashboardBook.setItems(filteredCategoryList); // set filtered list to table view
        }
    }



    @FXML
    void imageCloseOnAction(MouseEvent event) {

    }

    @FXML
    void imgClose(DragEvent event) {

    }

    @FXML
    void imgUserNameOnAction(MouseEvent event) {
        Parent fxml = null;
        try {
            fxml = FXMLLoader.load(getClass().getResource("/view/User_view/User_profile_form.fxml"));
            pane2.getChildren().removeAll();

            pane2.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String bookName = txtSearchBook.getText();
        try {
            var dto = bookBo.getBook(bookName);
            if (dto!=null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/User_view/Book_popup_form.fxml"));
                AnchorPane root = loader.load();
                BookPopupCardFormController controller = loader.getController();
                controller.setValues(dto,pane2);

                Scene scene = new Scene(root);
                Stage newStage = new Stage();
                newStage.setScene(scene);
                newStage.setTitle("Detail");

                newStage.show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    void textFieldFocus(){
        txtSearchBook.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                img1.setVisible(false);
            } else {
                img1.setVisible(true);
            }
        });
    }


}

