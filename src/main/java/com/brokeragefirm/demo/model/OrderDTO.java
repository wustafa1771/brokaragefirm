package com.brokeragefirm.demo.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class OrderDTO {

    private String customerId;

    private String assetName;

    @Enumerated(EnumType.STRING)
    private OrderSide orderSide;  // BUY or SELL

    private double size;          // Number of shares

    private double price;         // Price per share

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public OrderSide getOrderSide() {
		return orderSide;
	}

	public void setOrderSide(OrderSide orderSide) {
		this.orderSide = orderSide;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public OrderDTO(String customerId, String assetName, OrderSide orderSide, double size, double price) {
		super();
		this.customerId = customerId;
		this.assetName = assetName;
		this.orderSide = orderSide;
		this.size = size;
		this.price = price;
	}
	
	public OrderDTO() {}
	
    
}
