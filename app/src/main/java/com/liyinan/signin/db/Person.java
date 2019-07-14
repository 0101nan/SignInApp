package com.liyinan.signin.db;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

public class Person extends LitePalSupport {
    private String Name;
    private String Department;
    private boolean IsSignIn;
    private boolean IsLeave;

    public String getName(){
        return Name;
    }

    public String getDepartment(){
        return Department;
    }

    public boolean isSignIn(){
        return IsSignIn;
    }

    public void setName(String name){
        Name=name;
    }

    public void setDepartment(String department){
        Department=department;
    }

    public void setSignIn(boolean isSignIn){
        IsSignIn=isSignIn;
    }

    public boolean isLeave(){
        return IsLeave;
    }

    public void setLeave(boolean isLeave){
        IsLeave=isLeave;
    }

}
