package com.project.hostelms;

public enum PaymentModes { 
	
	CASH("Cash"),
	DEBITCARD ("Debit Card"), 
	CREDITCARD("Credit Card"),
	UPI("UPI");
	private String name;
	PaymentModes( String name)
	{
		this.name=name;
	}
	
	
}
