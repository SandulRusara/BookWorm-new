package lk.ijse.dao.custom.impl;

import lk.ijse.dao.BaseDao;
import lk.ijse.dao.custom.BookDao;
import lk.ijse.entity.Book;
import lk.ijse.entity.Branch;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

import static lk.ijse.dao.BaseDao.executeTransaction;

public class BookDaoImpl implements BookDao {
    @Override
    public void save(Book entity) throws SQLException {
        BaseDao.executeTransaction(session -> session.save(entity));
    }

    @Override
    public void update(Book entity) throws SQLException {
        BaseDao.executeTransaction(session -> {
            session.update(entity);
            return null;
        });

    }

    @Override
    public void delete(int id) throws SQLException {
        BaseDao.executeTransaction(session -> {
            Book entity = null;
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
    public List<Book> loadAll() throws SQLException {
        return   BaseDao.executeTransaction(session -> {
            Query query = session.createQuery("FROM Book ");
            return query.getResultList();

        });

    }

    @Override
    public Book get(String data) throws SQLException {
        return executeTransaction(session -> {
            Query query = session.createQuery("from Book where title=:title");
            query.setParameter("title", data);
            List<Book> books= query.getResultList();
            for (Book book : books){
                return book;
            }
            System.out.println("ok");
            return null;
        });
    }

    @Override
    public Book getbyId(int id) throws SQLException {
        return executeTransaction(session -> session.get(Book.class,id));
    }

    @Override
    public List<Book> getBookByBranch(Branch branch) throws SQLException {
        return executeTransaction(session -> {
            Query query = session.createQuery("from Book where branch=:branch");
            query.setParameter("branch", branch);
            List<Book> books= query.getResultList();
             return books;

        });
    }

    @Override
    public List<Book> getByCategory(String category, Branch branch) throws SQLException {
        return executeTransaction(session -> {
            Query query = session.createQuery("from Book b where b.branch = :branch and b.category = :category");
            query.setParameter("branch", branch);
            query.setParameter("category", category);
            List<Book> books= query.getResultList();
            return books;

        });
    }

    @Override
    public long getBookCount() throws SQLException {
        return executeTransaction(session -> {
            Long bookCount =(Long) session.createQuery("select count(*) from Book ").getSingleResult();
            return bookCount;
        });
    }


}
