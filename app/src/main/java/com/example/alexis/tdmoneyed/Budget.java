package com.example.alexis.tdmoneyed;

import java.io.Serializable;
import java.util.ArrayList;

public class Budget implements Serializable {

    private ArrayList<ListItem> college;
    private ArrayList<ListItem> food;
    private ArrayList<ListItem> transport;
    private ArrayList<ListItem> debt;
    private ArrayList<ListItem> entertain;
    private ArrayList<ListItem> pets;
    private ArrayList<ListItem> personal;
    private ArrayList<ListItem> totals;

    private double budgeted;
    private double spent;
    private double income;
    private double saveGoal;
    private double saveActual;

    public ArrayList<ListItem> getCollege(){
        return college;
    }
    public ArrayList<ListItem> getFood(){
        return food;
    }
    public ArrayList<ListItem> getTransport(){
        return transport;
    }
    public ArrayList<ListItem> getDebt(){
        return debt;
    }
    public ArrayList<ListItem> getEntertain(){
        return entertain;
    }
    public ArrayList<ListItem> getPets(){
        return pets;
    }
    public ArrayList<ListItem> getPersonal(){
        return personal;
    }
    public ArrayList<ListItem> getTotals(){
        return totals;
    }

    public double getBudgeted(){ return budgeted; }
    public double getSpent(){ return spent; }
    public double getIncome(){ return income;  }
    public double getSaveGoal(){ return saveGoal; }
    public double getSaveActual(){ return saveActual; }

    public void setCollege(ArrayList<ListItem> value){
        this.college = value;
    }
    public void setFood(ArrayList<ListItem> value){
        this.food = value;
    }
    public void setTransport(ArrayList<ListItem> value){
        this.transport = value;
    }
    public void setDebt(ArrayList<ListItem> value){
        this.debt = value;
    }
    public void setEntertain(ArrayList<ListItem> value){
        this.entertain = value;
    }
    public void setPets(ArrayList<ListItem> value){
        this.pets = value;
    }
    public void setPersonal(ArrayList<ListItem> value){
        this.personal = value;
    }
    public void setTotals(ArrayList<ListItem> value){
        this.totals = value;
    }

    public void setBudgeted(Double value){ this.budgeted = value; }
    public void setSpent(Double value){ this.spent = value; }
    public void setIncome(Double value){ this.income = value; }
    public void setSaveGoal(Double value){ this.saveGoal = value; }
    public void setSaveActual(Double value){ this.saveActual = value; }
}
