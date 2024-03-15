package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDao;
import lk.ijse.entity.User;

import java.sql.SQLException;

public interface UserDao extends CrudDao<User> {

    long getUserCount() throws SQLException;
}
