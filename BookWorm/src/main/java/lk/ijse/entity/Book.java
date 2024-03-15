package lk.ijse.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Book {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BookId;
   @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String category;
    private  String availability;
    @ManyToOne
    private Branch branch;
    @ManyToMany(mappedBy = "bookList" , cascade = CascadeType.ALL)
    private List<User> userList;

    public Book() {
    }

    public Book(int bookId, String title, String author, String category, String availability, Branch branch, List<User> userList) {
        BookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.availability = availability;
        this.branch = branch;
        this.userList = userList;
    }

    public Book(String title, String author, String category, String availability, Branch branch) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.availability = availability;
        this.branch = branch;
    }

    public Book(int bookId, String title, String author, String category, String availability, Branch branch) {
        BookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.availability = availability;
        this.branch = branch;
    }

    public int getBookId() {
        return BookId;
    }

    public void setBookId(int bookId) {
        BookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Branch getBranchId() {
        return branch;
    }

    public void setBranchId(Branch branch) {
        this.branch = branch;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "Book{" +
                "BookId='" + BookId + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", availability='" + availability + '\'' +
                ", branch='" + branch + '\'' +
                ", userList=" + userList +
                '}';
    }
}
