package com.brokeragefirm.demo.model;


import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "assets")
public class Asset {

    @Id


@GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    private String customerId;
    
    private String assetName;
    
    private double size;          // Total size of the asset (e.g., total shares or total cash)
    
    private double usableSize;    // Size available for use in orders

    // Constructors, Getters, and Setters

    public Asset() {}

    public Asset(String customerId, String assetName, double size, double usableSize) {
        this.customerId = customerId;
        this.assetName = assetName;
        this.size = size;
        this.usableSize = usableSize;
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

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getUsableSize() {
        return usableSize;
    }

    public void setUsableSize(double usableSize) {
        this.usableSize = usableSize;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    
}