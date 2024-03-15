package lk.ijse.dao.custom.impl;

import lk.ijse.dao.BaseDao;
import lk.ijse.dao.custom.AdminDao;
import lk.ijse.entity.Admin;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

import static lk.ijse.dao.BaseDao.executeTransaction;


public class AdminDaoImpl implements AdminDao {
    @Override
    public void save(Admin entity) throws SQLException {
        BaseDao.executeTransaction(session -> session.save(entity));

    }

    @Override
    public void update(Admin entity) throws SQLException {
        BaseDao.executeTransaction(session -> {
           session.update(entity);
           return null;
        });

    }

    @Override
    public void delete(int id) throws SQLException {
        BaseDao.executeTransaction(session -> {
            Admin entity = null;
            try {
                entity = getbyId(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            session.delete(entity);
            return null;
        });
    }

    @Override
    public List<Admin> loadAll() throws SQLException {

       return   BaseDao.executeTransaction(session -> {
            Query query = session.createQuery("FROM Admin");
             return query.getResultList();

         });

    }

    @Override
    public Admin get( String data) throws SQLException {

        return executeTransaction(session -> {
            Query query = session.createQuery("from Admin where userName=:username");
            query.setParameter("username", data);
            List<Admin> admins= query.getResultList();
            for (Admin admin : admins){
                return admin;
            }
            System.out.println("ok");
            return null;
        });
    }

    @Override
    public Admin getbyId(int id) throws SQLException {
        return executeTransaction(session -> session.get(Admin.class,id));
    }

}
