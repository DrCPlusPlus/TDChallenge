package com.example.alexis.tdmoneyed;

import java.io.Serializable;

/**
 * Created by Alexis on 11/22/2016.
 */

public class Settings implements Serializable {

    private String gardiansName;
    private String gardiansNumber;
    private String youthsName;
    private String youthsNumber;
    private String accountNumber;

    public void setGardiansName(String value){
        this.gardiansName = value;
    }
    public void setGardiansNumber(String value){
        this.gardiansNumber = value;
    }
    public void setYouthsName(String value){
        this.youthsName = value;
    }
    public void setYouthsNumber(String value){
        this.youthsNumber = value;
    }
    public void setAccountNumber(String value){
        this.accountNumber = value;
    }

    public String getGardiansName(){
        return gardiansName;
    }
    public String getGardiansNumber(){
        return gardiansNumber;
    }
    public String getYouthsName(){
        return youthsName;
    }
    public String getYouthsNumber(){
        return youthsNumber;
    }
    public String getAccountNumber(){ return accountNumber; }
}
