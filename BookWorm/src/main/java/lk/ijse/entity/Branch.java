package lk.ijse.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int branchId;
    @Column(nullable = false)
    private String branchName;
    private String contact;

    private String Address;
    @ManyToOne
    private Admin admin;
    @OneToMany(mappedBy = "branch")
    private List<Book> books;
    @OneToMany(mappedBy = "branch")
    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Branch() {
    }

    public Branch(int branchId, String branchName, String contact, String address, Admin admin) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.contact = contact;
        Address = address;
        this.admin = admin;
    }

    public Branch(int branchId, String branchName, String contact, String address, Admin admin, List<Book> books) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.contact = contact;
        Address = address;
        this.admin = admin;
        this.books = books;
    }

    public Branch(String branchName, String contact, String address, Admin admin) {
        this.branchName = branchName;
        this.contact = contact;
        Address = address;
        this.admin = admin;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "branchId=" + branchId +
                ", branchName='" + branchName + '\'' +
                ", contact='" + contact + '\'' +
                ", Address='" + Address + '\'' +
                ", admin=" + admin +
                ", books=" + books +
                '}';
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
