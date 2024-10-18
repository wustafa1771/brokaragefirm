package com.brokeragefirm.demo.model;


public class DepositWithdrawRequestDTO {

    private String customerId;
    private double amount;
    private String iban;  // Only needed for withdraw; can be null for deposit

    // Constructors, Getters, and Setters

    public DepositWithdrawRequestDTO() {}

    public DepositWithdrawRequestDTO(String customerId, double amount, String iban) {
        this.customerId = customerId;
        this.amount = amount;
        this.iban = iban;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}