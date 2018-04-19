package com.dj81.hereiam.utils;

import com.dj81.hereiam.utils.DBUtils;

public class ContactVO {
    String id;
    String name;
    String phone_number;

    public ContactVO(){

    }
    public ContactVO(String id, String name, String phone_number){
        this.id = id==null? DBUtils._instance.getNextPk():id;
        this.name = name;
        this.phone_number = phone_number;
    }

    public ContactVO(String name, String phone_number){
        this(DBUtils._instance.getNextPk(), name, phone_number);
    }

    public String getID(){
        return this.id;
    }

    public void setID(String id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPhoneNumber(){
        return this.phone_number;
    }

    public void setPhoneNumber(String phone_number){
        this.phone_number = phone_number;
    }


    @Override
    public String toString(){
        return this.getID()+" "+this.getName() + " " + this.getPhoneNumber() + "                                                             ";
    }
}
