package adminPackage;

public class AdminDetails {
    private String adminName, adminPhoneNumber, adminEmail;

    public AdminDetails() {
    }

    public AdminDetails(String adminName, String adminPhoneNumber, String adminEmail) {
        this.adminName = adminName;
        this.adminPhoneNumber = adminPhoneNumber;
        this.adminEmail = adminEmail;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPhoneNumber() {
        return adminPhoneNumber;
    }

    public void setAdminPhoneNumber(String adminPhoneNumber) {
        this.adminPhoneNumber = adminPhoneNumber;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }
}
