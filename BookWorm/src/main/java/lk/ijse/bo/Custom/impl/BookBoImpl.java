package lk.ijse.bo.Custom.impl;


import lk.ijse.bo.Custom.BookBo;
import lk.ijse.dao.DaoFactory;
import lk.ijse.dao.custom.BookDao;
import lk.ijse.dao.custom.BranchDao;
import lk.ijse.dto.BookDto;
import lk.ijse.entity.Book;
import lk.ijse.entity.Branch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookBoImpl implements BookBo {
    private BookDao bookDao = (BookDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DataType.BOOK);
    private BranchDao branchDao = (BranchDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DataType.BRANCH);
    @Override
    public void saveBook(BookDto bookDto) throws SQLException {
        Branch branch = branchDao.getbyId(bookDto.getBranchId());
        bookDao.save(new Book(bookDto.getTitle(), bookDto.getAuthor(), bookDto.getCategory(), bookDto.getAvailability(),branch ));

    }

    @Override
    public List<BookDto> getAllBook() throws SQLException {
        List<BookDto> bookDtos = new ArrayList<>();
        List<Book> books = bookDao.loadAll();
        for (Book book : books){
            bookDtos.add(new BookDto(book.getBookId(), book.getTitle(), book.getAuthor(), book.getCategory(), book.getAvailability(), book.getBranchId().getBranchId()));
        }
        return bookDtos;
    }

    @Override
    public BookDto getBook(String data) throws SQLException {
        Book book = bookDao.get(data);
        return new BookDto(book.getBookId(), book.getTitle(), book.getAuthor(), book.getCategory(), book.getAvailability(), book.getBranchId().getBranchId());
    }

    @Override
    public void deleteBook(int id) throws SQLException {
     bookDao.delete(id);
    }

    @Override
    public void updateBook(BookDto bookDto) throws SQLException {
        Branch branch = branchDao.getbyId(bookDto.getBranchId());
        bookDao.update(new Book(bookDto.getBookId(), bookDto.getTitle(), bookDto.getAuthor(), bookDto.getCategory(), bookDto.getAvailability(), branch));
    }

    @Override
    public List<BookDto> getBookByBranch(String branchName) throws SQLException {
        Branch branch = branchDao.get(branchName);
       List<Book> list = bookDao.getBookByBranch(branch);
       List<BookDto> dtos = new ArrayList<>();
       for (Book book :list){
           dtos.add(new BookDto(book.getBookId(), book.getTitle(),book.getAuthor(), book.getCategory(), book.getAvailability(),book.getBranchId().getBranchId()));
       }
        return dtos;
    }

    @Override
    public List<BookDto> getBookByCategory(String category, String branchName) throws SQLException {
        Branch branch = branchDao.get(branchName);
        List<Book> books = bookDao.getByCategory(category,branch);
        List<BookDto> dtos = new ArrayList<>();
        for (Book book :books){
            dtos.add(new BookDto(book.getBookId(), book.getTitle(),book.getAuthor(), book.getCategory(), book.getAvailability(),book.getBranchId().getBranchId()));
        }
        return dtos;

    }

    @Override
    public long getBookCount() throws SQLException {
       return bookDao.getBookCount();
    }


}
