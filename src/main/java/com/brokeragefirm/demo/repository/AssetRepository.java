package com.brokeragefirm.demo.repository;


import com.brokeragefirm.demo.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, String> {
    List<Asset> findByCustomerIdAndAssetName(String customerId, String assetName);

	List<Asset> findByCustomerId(String customerId);
    
}