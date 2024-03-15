package lk.ijse.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TransactionDto {
  private int id;
  private int userId;
  private String userName;

  private String branchName;
  private int bookId;

  private String bookname;

  private LocalDateTime borrowed;

  private LocalDate dueDate;
  private LocalDateTime returnedDate;
  private Boolean isReturn;
  private long daysOverDue;


  public TransactionDto(int userId, String bookId, LocalDateTime borrowed, LocalDate dueDate, LocalDateTime returnedDate, Boolean isReturn) {
    this.userId = userId;
    this.bookname = bookId;
    this.borrowed = borrowed;
    this.dueDate = dueDate;
    this.returnedDate = returnedDate;
    this.isReturn = isReturn;
  }

  public TransactionDto(int id, int userId, String userName, String branchName, int bookId, String bookname, LocalDateTime borrowed, LocalDate dueDate, LocalDateTime returnedDate, Boolean isReturn) {
    this.id = id;
    this.userId = userId;
    this.userName = userName;
    this.branchName = branchName;
    this.bookId = bookId;
    this.bookname = bookname;
    this.borrowed = borrowed;
    this.dueDate = dueDate;
    this.returnedDate = returnedDate;
    this.isReturn = isReturn;
  }

  public TransactionDto(int userId, String bookname, LocalDate borrowed, String dueDate, boolean isReturn) {
    this.userId = userId;
    this.bookname = bookname;
    this.borrowed = LocalDateTime.of(borrowed, LocalTime.now());
    this.dueDate = LocalDate.parse(dueDate);
    this.isReturn = isReturn;
  }

  public TransactionDto(int id, int userId, String title, LocalDateTime borrowed, LocalDate dueDate, LocalDateTime returnedDate, Boolean aReturn) {
    this.id = id;
    this.userId = userId;
    this.bookname = title;
    this.borrowed = borrowed;
    this.dueDate = dueDate;
    this.returnedDate = returnedDate;
    this.isReturn = aReturn;
  }

  public TransactionDto(String userName, String title, String branchName, LocalDate dueDate) {
    this.userName = userName;
    this.branchName = branchName;
    this.dueDate = dueDate;
    this.bookname = title;
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


  public int getBookname() {
    return bookId;
  }

  public void setBookname(int bookId) {
    this.bookId = bookId;
  }



  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getBookId() {
    return bookname;
  }

  public void setBookId(String bookId) {
    this.bookname = bookId;
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
    return "TransactionDto{" +
            "id=" + id +
            ", userId=" + userId +
            ", bookname='" + bookname + '\'' +
            ", borrowed=" + borrowed +
            ", dueDate=" + dueDate +
            ", returnedDate=" + returnedDate +
            ", isReturn=" + isReturn +
            '}';
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
