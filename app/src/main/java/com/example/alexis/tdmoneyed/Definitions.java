package com.example.alexis.tdmoneyed;

/**
 * The definitions for client/server communication
 */

public class Definitions {
	public static final byte GET_ALL_CATEGORIES = (byte)0xAA;
	public static final byte GET_ALL_TRANSACTIONS = (byte)0xAB;
	public static final byte GET_ALL_BANK_ACCOUNTS = (byte)0xAC;
	public static final byte GET_NEW_TRANSACTIONS = (byte)0xAD;

	public static final byte ADD_NEW_TRANSACTION = (byte)0xAE;
	public static final byte ADD_NEW_CATEGORY = (byte)0xAF;
	public static final byte ADD_NEW_BANK_ACCOUNT = (byte)0xB0;

	public static final byte UPDATE_TRANSACTION = (byte)0xB1;

	public static final byte ACK_OK = (byte)0xB2;
	public static final byte ACK_ERROR = (byte)0xB3;
	public static final byte GET_NEW_TRANSACTIONS_FOR_ACCOUNT = (byte)0xB4;
}
