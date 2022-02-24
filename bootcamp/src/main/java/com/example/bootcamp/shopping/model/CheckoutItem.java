package com.example.bootcamp.shopping.model;

import javax.validation.constraints.NotNull;

public class CheckoutItem {
	@NotNull(message = "basketId is null")
	private Integer basketId;

	public Integer getBasketId() {
		return basketId;
	}

	public void setBasketId(Integer basketId) {
		this.basketId = basketId;
	}

}
