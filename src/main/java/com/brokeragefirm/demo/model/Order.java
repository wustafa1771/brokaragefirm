package com.brokeragefirm.demo.model;


import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerId;

    private String assetName;

    @Enumerated(EnumType.STRING)
    private OrderSide orderSide;  // BUY or SELL

    private double size;          // Number of shares

    private double price;         // Price per share

    @Enumerated(EnumType.STRING)
    private OrderStatus status;   // PENDING, MATCHED, CANCELED

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    // Constructors, Getters, and Setters

    public Order() {}

    public Order(String customerId, String assetName, OrderSide orderSide, double size, double price) {
        this.customerId = customerId;
        this.assetName = assetName;
        this.orderSide = orderSide;
        this.size = size;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public static Order createOrder(OrderDTO dto) {
    	Order order = new Order();
    	order.setCustomerId(dto.getCustomerId());
    	order.setAssetName(dto.getAssetName());
    	order.setOrderSide(dto.getOrderSide());
    	order.setSize(dto.getSize());
    	order.setPrice(dto.getPrice());
    	
    	return order;
    }
}