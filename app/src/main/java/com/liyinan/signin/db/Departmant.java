package com.liyinan.signin.db;

import org.litepal.crud.LitePalSupport;

public class Departmant extends LitePalSupport {
    private String Name;
    private int Counts;

    public String getName(){
        return Name;
    }

    public void setName(String name){
        Name=name;
    }

    public int getCounts(){
        return Counts;
    }

    public void setCounts(int counts){
        Counts=counts;
    }
}
