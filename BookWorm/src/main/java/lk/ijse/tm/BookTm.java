package lk.ijse.tm;

import com.jfoenix.controls.JFXButton;

public class BookTm {
private String title;
private String author;
private String category;

private String status;
private String branch;

private JFXButton button;

    public BookTm(String title, String author, String category, JFXButton button) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.button = button;
    }

    public BookTm() {
    }

    public BookTm(String title, String author, String category, String status, String branch) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.status = status;
        this.branch = branch;
    }

    public BookTm(String title, String author, String status, String category) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
    public JFXButton getButton() {
        return button;
    }

    public void setButton(JFXButton button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "ManageBookTm{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", status='" + status + '\'' +
                ", branch='" + branch + '\'' +
                '}';
    }
}
