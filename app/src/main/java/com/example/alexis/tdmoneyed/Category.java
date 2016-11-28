package com.example.alexis.tdmoneyed;

import java.io.Serializable;

/**
 * A class to hold category information from the server
 */

public class Category implements Serializable{
	private int id;
	private String categoryName;

	public Category(int id, String categoryName) {
		this.id = id;
		this.categoryName = categoryName;
	}

	public int getId() {
		return id;
	}

	public String getCategoryName() {
		return categoryName;
	}
}
