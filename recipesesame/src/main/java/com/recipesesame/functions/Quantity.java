package com.recipesesame.functions;

public class Quantity {
	private int amount;
	private String measurement;
	
	public Quantity(int amount, String measurement) {
		this.amount = amount;
		this.measurement = measurement;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public String getMeasurement() {
		return this.measurement;
	}
	
	@Override
	public String toString() {
		return this.amount + " " + this.measurement;
	}
}
