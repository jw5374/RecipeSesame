package com.recipesesame.functions;

import java.io.Serializable;

public final class Ingredient implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		return this.quantity.toString() + " of " + this.material;
	}
}
