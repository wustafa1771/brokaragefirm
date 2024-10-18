package com.brokeragefirm.demo.repository;


import com.brokeragefirm.demo.model.Asset;
import com.brokeragefirm.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Date;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerIdAndCreateDateBetween(String customerId, Date startDate, Date endDate);
    List<Asset> findByCustomerIdAndAssetName(String customerId, String assetName);

	List<Asset> findByCustomerId(String customerId);
}