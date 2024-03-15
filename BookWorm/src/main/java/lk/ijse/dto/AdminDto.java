package lk.ijse.dto;

public class AdminDto {
    private int adminId;
    private String name;
    private String email;
    private String UserName;

    public AdminDto(int adminId, String name, String email, String userName, String password) {
        this.adminId = adminId;
        this.name = name;
        this.email = email;
        UserName = userName;
        this.password = password;
    }

    private String password;

    public AdminDto(String name, String email, String userName, String password) {
        this.name = name;
        this.email = email;
        UserName = userName;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AdminDto{" +
                "adminId=" + adminId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", UserName='" + UserName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}
