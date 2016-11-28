package com.example.alexis.tdmoneyed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

public class Budget implements Serializable {

    private ArrayList<ListItem> college;
    private ArrayList<ListItem> food;
    private ArrayList<ListItem> transport;
    private ArrayList<ListItem> debt;
    private ArrayList<ListItem> entertain;
    private ArrayList<ListItem> pets;
    private ArrayList<ListItem> personal;
    private ArrayList<ListItem> totals;

    private ArrayList<Category> categories;
	private ArrayList<Transaction> transactions;

    private double budgeted;
    private double income;
    private double saveGoal;

	public Budget(){
		college = new ArrayList<>();
		food = new ArrayList<>();
		transport = new ArrayList<>();
		debt = new ArrayList<>();
		entertain = new ArrayList<>();
		pets = new ArrayList<>();
		personal = new ArrayList<>();
		totals = new ArrayList<>();
		categories = new ArrayList<>();
		transactions = new ArrayList<>();

	}

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

	public ArrayList<Category> getCategories() {return categories; }
	public ArrayList<Transaction> getTransactions() { return transactions; }


    public double getBudgeted(){ return budgeted; }

	public double getSpent(){
		double amountSpent = 0.0;
		for(Transaction t : transactions)
			amountSpent += t.getAmount();

		return amountSpent;
	}

    public double getIncome(){ return income;  }
    public double getSaveGoal(){ return saveGoal; }
    public double getSaveActual(){
		return income - getSpent();
	}

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

	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}


	public void setBudgeted(Double value){ this.budgeted = value; }
    public void setIncome(Double value){ this.income = value; }
    public void setSaveGoal(Double value){ this.saveGoal = value; }


    public void addTransaction(Transaction t){
		this.transactions.add(t);
	}

	private int getCategoryId(String catName){
		for (Category c : categories)
			if (c.getCategoryName().toUpperCase().equals(catName))
				return c.getId();

		return -1;
	}


	public boolean isOverBudget(){
		double collegeBud = 0.0;
		double foodBud = 0.0;
		double transportBud = 0.0;
		double debtBud = 0.0;
		double entertainBud = 0.0;
		double petsBud = 0.0;
		double personalBud = 0.0;

		if (college != null)
			for(ListItem li : college)
				collegeBud += li.getAmount();

		if (food != null)
			for (ListItem li : food)
				foodBud += li.getAmount();

		if (transport != null)
			for (ListItem li : transport)
				transportBud += li.getAmount();

		if (debt != null)
			for (ListItem li : debt)
				debtBud += li.getAmount();

		if (entertain != null)
			for (ListItem li : entertain)
				entertainBud += li.getAmount();

		if (pets != null)
			for (ListItem li : pets)
				petsBud += li.getAmount();

		if(personal != null)
			for (ListItem li : personal)
				personalBud += li.getAmount();

		TreeMap<Integer, Double> actualAmounts = new TreeMap<Integer, Double>();

		for(Category c : categories)
			actualAmounts.put(c.getId(), 0.0);

		for(Transaction t : transactions)
			actualAmounts.put(t.getCategoryId(), actualAmounts.get(t.getCategoryId()) + t.getAmount());

		if (actualAmounts.get(getCategoryId("COLLEGE")) > collegeBud || actualAmounts.get(getCategoryId("FOOD")) > foodBud ||
				actualAmounts.get(getCategoryId("TRANSPORTATION")) > transportBud ||
				actualAmounts.get(getCategoryId("DEBT")) > debtBud ||
				actualAmounts.get(getCategoryId("ENTERTAINMENT")) > entertainBud ||
				actualAmounts.get(getCategoryId("PETS")) > petsBud ||
				actualAmounts.get(getCategoryId("PERSONAL")) > personalBud)
			return true;

		return false;

	}
}
