package lk.ijse.tm;

import java.time.LocalDate;

public class TransactionTm {
    private String transactionId;
    private String bookId;
    private String bookName;
    private String userName;
    private String branchName;
    private String borrow;
    private String returnDate;


    private long overDueDays;
    private LocalDate dueDate;

    public TransactionTm() {
    }

    public TransactionTm(String transactionId, String bookId, String bookName, String userName, String branchName, String borrow, String returnDate) {
        this.transactionId = transactionId;
        this.bookId = bookId;
        this.bookName = bookName;
        this.userName = userName;
        this.branchName = branchName;
        this.borrow = borrow;
        this.returnDate = returnDate;
    }

    public TransactionTm(String userName, String bookName, LocalDate dueDate, long daysOverdue) {
        this.userName = userName;
        this.bookName = bookName;
        this .dueDate = dueDate;
        this.overDueDays = daysOverdue;

    }



    public TransactionTm(String bookName, String returnDate, String borrow, LocalDate dueDate) {
        this.bookName =bookName;
        this.returnDate = returnDate;
        this.borrow = borrow;
        this.dueDate = dueDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBorrow() {
        return borrow;
    }

    public void setBorrow(String borrow) {
        this.borrow = borrow;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    public long getOverDueDays() {
        return overDueDays;
    }

    public void setOverDueDays(long overDueDays) {
        this.overDueDays = overDueDays;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "TransactionTm{" +
                "transactionId='" + transactionId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", userName='" + userName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", borrow='" + borrow + '\'' +
                ", returnDate='" + returnDate + '\'' +
                '}';
    }
}
