package com.recipesesame.functions;

public final class Ingredient {
	private Quantity quantity;
	private String material;
	
	public Ingredient(Quantity quantity, String material) {
		this.quantity = quantity;
		this.material = material;
	}

	public Quantity getQuantity() {
		return this.quantity;
	}

	public String getMaterial() {
		return this.material;
	}
	
	@Override
	public String toString() {
		return this.quantity + this.material;
	}
}