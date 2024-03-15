package lk.ijse.bo.Custom;

import lk.ijse.bo.SuperBo;
import lk.ijse.dto.TransactionDto;

import java.sql.SQLException;
import java.util.List;

public interface TransactionBo extends SuperBo {


    List<TransactionDto> getUnreturned(int userId) throws SQLException;


    TransactionDto gettransactionByBookUser(String bookId, int userId) throws SQLException;

    void saveTransactiondata(int userId, String bookTitle, TransactionDto dto) throws SQLException;

    boolean update(TransactionDto dto) throws SQLException;

    List<TransactionDto> getAll() throws SQLException;

    List<TransactionDto> getOverDue() throws SQLException;

    List<TransactionDto> getByUser(int userId) throws SQLException;

    List<TransactionDto> getTodayCheckOuts() throws SQLException;
}
