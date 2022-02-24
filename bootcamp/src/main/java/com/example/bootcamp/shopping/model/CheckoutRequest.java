package com.example.bootcamp.shopping.model;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CheckoutRequest{
	@NotNull(message = "paymentId is null")
	private Integer paymentId;

	@NotNull(message = "userId is null")
	private Integer userId;

	@NotNull(message = "items is null")
	private List<CheckoutItem> items;

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<CheckoutItem> getItems() {
		return items;
	}

	public void setItems(List<CheckoutItem> items) {
		this.items = items;
	}
}