package com.example.alexis.tdmoneyed;

import java.io.Serializable;

/**
 * A class to hold the transaction data
 */

public class Transaction  implements Serializable {
	private int id;
	private int accountId;
	private double amount;
	private int categoryId;
	private boolean appliedToPhone;

	public Transaction(int id, boolean appliedToPhone, int categoryId, double amount, int accountId) {
		this.id = id;
		this.appliedToPhone = appliedToPhone;
		this.categoryId = categoryId;
		this.amount = amount;
		this.accountId = accountId;
	}

	public int getId() {
		return id;
	}

	public int getAccountId() {
		return accountId;
	}

	public double getAmount() {
		return amount;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public boolean isAppliedToPhone() {
		return appliedToPhone;
	}

	public void setAppliedToPhone(boolean appliedToPhone) {
		this.appliedToPhone = appliedToPhone;
	}
}
