package lk.ijse.tm;

public class BranchTm {
    private String name;
    private  String Contact;
    private String address;
    private String adminName;

    public BranchTm(String name, String contact, String address,String adminName) {
        this.name = name;
        Contact = contact;
        this.address = address;
        this.adminName = adminName;
    }

    public BranchTm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
