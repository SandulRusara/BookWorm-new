package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDao;
import lk.ijse.entity.Book;
import lk.ijse.entity.Transaction;
import lk.ijse.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface TransactionDao extends SuperDao {

    List<Transaction> getByUserId(User user) throws SQLException;

    List<Transaction> getByBookUser(User user, Book book) throws SQLException;

    void save(Transaction transaction) throws SQLException;

    boolean update(Transaction transaction) throws SQLException;

    List<Transaction> getAll() throws SQLException;

    List<Transaction> getOverDue() throws SQLException;

    List<Transaction> getAllByUser(User user) throws SQLException;

    List<Transaction> getTodayCheckOuts() throws SQLException;
}
