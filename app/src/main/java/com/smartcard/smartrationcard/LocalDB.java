package com.smartcard.smartrationcard;

//Local Database class to store intermediate values

import android.net.Uri;

public class LocalDB {

    //Variable decelerations
    private static String rationCardID,userName,category,phoneNumber,emailID;
    private static int numberOfRations,familyMembers;
    private static Uri profilePicUri;

    public static void setRationCardID(String value)
    {
        rationCardID=value;
    }

    public  static String getRationCardID()
    {
        return rationCardID;
    }

    public static void setUserName(String value){userName=value;}

    public static String getUserName(){return userName;}

    public static void setCategory(String value){category=value;}

    public static String getCateogry(){return category;}

    public static void setNumberOfRations(int value){numberOfRations=value;}

    public static int getNumberOfRations(){return numberOfRations;}

    public static void setProfilePicUri(Uri value){profilePicUri=value;}

    public static Uri getProfilePicUri(){return  profilePicUri;}

    public static void setFamilyMembers(int value){ familyMembers=value; }

    public static int getFamilyMembers(){return  familyMembers; }

    public static void setPhoneNumber(String value) {phoneNumber = value;}

    public static String getPhoneNumber() {return phoneNumber;}

    public static void setEmailID(String value) {emailID = value;}

    public static String getEmailID() {return emailID;}
}
