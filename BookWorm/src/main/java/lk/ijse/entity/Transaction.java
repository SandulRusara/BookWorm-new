package lk.ijse.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;
    private LocalDateTime borrowed;

    private LocalDate dueDate;
    private LocalDateTime returnedDate;
    private Boolean isReturn;

    public Transaction() {
    }

    public Transaction(int id, User user, Book book, LocalDateTime borrowed, LocalDate dueDate, LocalDateTime returnedDate, Boolean isReturn) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.borrowed = borrowed;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
        this.isReturn = isReturn;
    }

    public Transaction(User user, Book book, LocalDateTime borrowed, LocalDate dueDate, Boolean aReturn) {

        this.user = user;
        this.book = book;
        this.borrowed = borrowed;
        this.dueDate = dueDate;
        this.isReturn = aReturn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(LocalDateTime borrowed) {
        this.borrowed = borrowed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDateTime returnedDate) {
        this.returnedDate = returnedDate;
    }

    public Boolean getReturn() {
        return isReturn;
    }

    public void setReturn(Boolean aReturn) {
        isReturn = aReturn;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", user=" + user +
                ", book=" + book +
                ", borrowed=" + borrowed +
                ", dueDate=" + dueDate +
                ", returnedDate=" + returnedDate +
                ", isReturn=" + isReturn +
                '}';
    }
}
