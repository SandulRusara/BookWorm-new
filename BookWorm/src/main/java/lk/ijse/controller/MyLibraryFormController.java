package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lk.ijse.bo.Custom.BookBo;
import lk.ijse.bo.Custom.TransactionBo;
import lk.ijse.bo.Custom.UserBo;
import lk.ijse.bo.Custom.impl.BookBoImpl;
import lk.ijse.bo.Custom.impl.TransactionBoImpl;
import lk.ijse.bo.Custom.impl.UserBoImpl;
import lk.ijse.dto.TransactionDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static lk.ijse.controller.UserLoginFormController.userId;

public class MyLibraryFormController {

    public AnchorPane pane2;
    public javafx.scene.control.ScrollPane ScrollPane;
    public GridPane bookGrid;
    private ObservableList<TransactionDto> observableList = FXCollections.observableArrayList();
    private BookBo bookBo = new BookBoImpl();
    private TransactionBo transactionBo = new TransactionBoImpl();
    private UserBo userBo = new UserBoImpl();




    public void initialize(){
        loadUnreturnedBooks();
    }

    private void loadUnreturnedBooks() {
        try {
            List<TransactionDto> dtos = transactionBo.getUnreturned(userId);
            for (TransactionDto dto : dtos){
                observableList.add(dto);
            }
            displayUi();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void displayUi() {
        int row = 0;
        int col =0;
        bookGrid.getRowConstraints().clear();
        bookGrid.getColumnConstraints().clear();
        for (int i =0;i< observableList.size();i++){

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/User_view/BookCard_form.fxml"));
                AnchorPane pane = loader.load();
                BookCardFormController controller = loader.getController();
                controller.setData(observableList.get(i));
                controller.setLibraryFormController(this);
                if (col==1){
                    col=0;
                    row++;
                }
                bookGrid.add(pane,col++,row);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}