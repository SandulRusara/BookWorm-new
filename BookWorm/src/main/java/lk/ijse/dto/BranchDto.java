package lk.ijse.dto;

public class BranchDto {

    private  int branchId;
    private String branchName;
    private String contact;

    private String Address;

    private int  adminId;

    public BranchDto(int branchId, String branchName, String contact, String address, int adminId) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.contact = contact;
        Address = address;
        this.adminId = adminId;
    }

    public BranchDto(String branchName, String contact, String address, int id) {
        this.branchName = branchName;
        this.contact = contact;
        Address = address;
        this.adminId = id;
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

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "BranchDto{" +
                "branchId='" + branchId + '\'' +
                ", branchName='" + branchName + '\'' +
                ", contact='" + contact + '\'' +
                ", Address='" + Address + '\'' +
                ", adminId='" + adminId + '\'' +
                '}';
    }
}
