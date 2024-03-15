package lk.ijse.dao.custom.impl;


import lk.ijse.dao.BaseDao;
import lk.ijse.dao.custom.TransactionDao;
import lk.ijse.entity.Book;
import lk.ijse.entity.Transaction;
import lk.ijse.entity.User;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

import static lk.ijse.dao.BaseDao.executeTransaction;


public class TransactionDaoImpl implements TransactionDao {


    @Override
    public List<Transaction> getByUserId(User user) throws SQLException {
        System.out.println("ok");
        return executeTransaction(session -> {
            Query query = session.createQuery("from Transaction where user =:user and isReturn=:return");
            query.setParameter("user", user);
            query.setParameter("return", false);
            List<Transaction> transactions = query.getResultList();
            return transactions;

        });
    }



    @Override
    public List<Transaction> getByBookUser(User user, Book book) throws SQLException {

        System.out.println("ok");
        return executeTransaction(session -> {
            Query query = session.createQuery("from Transaction where user =:user and book=:book");
            query.setParameter("user", user);
            query.setParameter("book", book);
            List<Transaction> transactions = query.getResultList();
            return transactions;

        });
    }

    @Override
    public void save(Transaction transaction) throws SQLException {
        executeTransaction(session -> session.save(transaction));
    }

    @Override
    public boolean update(Transaction transaction) throws SQLException {
        return executeTransaction(session -> {
            Query query = session.createQuery("update Transaction set isReturn=:return,returnedDate=:date where id=:id");
            query.setParameter("return", transaction.getReturn());
            query.setParameter("date", transaction.getReturnedDate());
            query.setParameter("id", transaction.getId());
            int i = query.executeUpdate();
            System.out.println(i);
            if (i > 0) {
                return true;
            }

            return false;
        });

    }

    @Override
    public List<Transaction> getAll() throws SQLException {
        return   BaseDao.executeTransaction(session -> {
            Query query = session.createQuery("FROM Transaction ");
            return query.getResultList();

        });
    }

    @Override
    public List<Transaction> getOverDue() throws SQLException {
        return BaseDao.executeTransaction(session -> {
            Query query = session.createQuery("FROM Transaction where isReturn=:isreturn and dueDate<CURRENT_TIMESTAMP");
            query.setParameter("isreturn", false);
            return query.getResultList();
        });
    }

    @Override
    public List<Transaction> getAllByUser(User user) throws SQLException {
        return   BaseDao.executeTransaction(session -> {
            Query query = session.createQuery("FROM Transaction where user=:user");
            query.setParameter("user",user);
            return query.getResultList();

        });
    }

    @Override
    public List<Transaction> getTodayCheckOuts() throws SQLException {
        return   BaseDao.executeTransaction(session -> {
            Query query = session.createQuery("FROM Transaction where Date(borrowed)= CURRENT DATE ");
            return query.getResultList();

        });
    }
}
