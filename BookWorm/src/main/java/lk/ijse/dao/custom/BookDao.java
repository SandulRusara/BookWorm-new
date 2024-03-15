package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDao;
import lk.ijse.entity.Book;
import lk.ijse.entity.Branch;

import java.sql.SQLException;
import java.util.List;

public interface BookDao extends CrudDao<Book> {


    List<Book> getBookByBranch(Branch branch) throws SQLException;

    List<Book> getByCategory(String category, Branch branch) throws SQLException;


    long getBookCount() throws SQLException;
}
