package lk.ijse.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false , unique = true)
    private String userName;
    @Column(nullable = false,unique = true)
    private String email;
    @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL)
    private List<Branch> branches;

    public Admin(int adminId, String name, String password, String userName, String email, List<Branch> branches) {
        this.adminId = adminId;
        this.name = name;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.branches = branches;
    }

    public Admin() {

    }

    public Admin(int adminId, String name, String password, String userName, String email) {
        this.adminId = adminId;
        this.name = name;
        this.password = password;
        this.userName = userName;
        this.email = email;
    }

    public Admin(String email, String name, String password, String userName) {
        this.name = name;
        this.password = password;
        this.userName = userName;
        this.email = email;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", branches=" + branches +
                '}';
    }
}
