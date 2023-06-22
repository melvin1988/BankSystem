package model;

import java.sql.Timestamp;

public class Transaction {
	private double balance;
	private String transactionType;
	private Timestamp transactionDate;

	public Transaction(double balance, String transactionType, Timestamp transactionDate) {
		this.balance = balance;
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}
}