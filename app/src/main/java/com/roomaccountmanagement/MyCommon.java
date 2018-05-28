package com.roomaccountmanagement;

public class MyCommon
{
    private static final MyCommon ourInstance = new MyCommon();

    public static String USERDETAILS = "UserDetails";
    public static String ADMINDETAILS = "AdminDetails";
    public static String UserName = "", User_Email = "", User_ID = "",User_Phone = "";

    public static MyCommon getInstance() {
        return ourInstance;
    }

    private MyCommon() {
    }
}
