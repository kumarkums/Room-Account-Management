package userDetails;

public class UserDetails
{
    public String userName,userEmail,userPhoneNumber,userUid;

    public boolean checkUser=false;



    public UserDetails()
    {

    }

    public UserDetails(String userName, String userUid, boolean checkUser) {
        this.userName = userName;
        this.userUid = userUid;
        this.checkUser = checkUser;
    }

    public UserDetails(String userName, String userEmail, String userPhoneNumber) {

        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
    }

    public UserDetails(String userName, boolean checkUser) {
        this.userName = userName;
        this.checkUser = checkUser;
    }

    public UserDetails(String userUid) {
        this.userUid = userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public boolean isCheckUser() {
        return checkUser;
    }

    public void setCheckUser(boolean checkUser) {
        this.checkUser = checkUser;
    }

    public String getUserUid() {
        return userUid;
    }
}
