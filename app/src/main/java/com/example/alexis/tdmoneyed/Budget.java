package com.example.alexis.tdmoneyed;


import android.app.Application;

import java.io.Serializable;
import java.util.List;

public class Budget extends Application implements Serializable {

    private List<ListItem> college;
    private List<ListItem> food;
    private List<ListItem> transport;
    private List<ListItem> debt;
    private List<ListItem> entertain;
    private List<ListItem> pets;
    private List<ListItem> personal;

    private double budgeted;
    private double spent;
    private double income;
    private double saveGoal;
    private double saveActual;


    public List<ListItem> getCollege(){
        return college;
    }
    public List<ListItem> getFood(){
        return food;
    }
    public List<ListItem> getTransport(){
        return transport;
    }
    public List<ListItem> getDebt(){
        return debt;
    }
    public List<ListItem> getEntertain(){
        return entertain;
    }
    public List<ListItem> getPets(){
        return pets;
    }
    public List<ListItem> getPersonal(){
        return personal;
    }

    public double getBudgeted(){
        return budgeted;
    }
    public double getSpent(){
        return spent;
    }
    public double getIncome(){
        return income;
    }
    public double getSaveGoal(){
        return saveGoal;
    }
    public double getSaveActual(){
        return saveActual;
    }

    public void setCollege(List<ListItem> value){
        this.college = value;
    }
    public void setFood(List<ListItem> value){
        this.food = value;
    }
    public void setTransport(List<ListItem> value){
        this.transport = value;
    }
    public void setDebt(List<ListItem> value){
        this.debt = value;
    }
    public void setEntertain(List<ListItem> value){
        this.entertain = value;
    }
    public void setPets(List<ListItem> value){
        this.pets = value;
    }
    public void setPersonal(List<ListItem> value){
        this.personal = value;
    }

    public void setBudgeted(Double value){
        this.budgeted = value;
    }
    public void setSpent(Double value){
        this.spent = value;
    }
    public void setIncome(Double value){
        this.income = value;
    }
    public void setSaveGoal(Double value){
        this.saveGoal = value;
    }
    public void setSaveActual(Double value){
        this.saveActual = value;
    }
}
