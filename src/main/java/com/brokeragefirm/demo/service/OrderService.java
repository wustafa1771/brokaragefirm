package com.brokeragefirm.demo.service;


import com.brokeragefirm.demo.model.*;
import com.brokeragefirm.demo.repository.*;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AssetRepository assetRepository;

    public OrderService(OrderRepository orderRepository, AssetRepository assetRepository) {
        this.orderRepository = orderRepository;
        this.assetRepository = assetRepository;
    }

    public Order createOrder(Order order) throws Exception {
    	  List<Asset> tryAssets = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(),"TRY");
          Asset tryAsset =null;
          if(tryAssets.size()>0)
        	  tryAsset=tryAssets.get(0);
          else
        	  tryAsset= new Asset(order.getCustomerId(), "TRY", 0, 0); // Create a new TRY asset if none exists
          
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	Date date = new Date();
    	order.setCreateDate(date);
    	order.setStatus(OrderStatus.PENDING);
    	
        if (order.getOrderSide() == OrderSide.BUY) {
            double requiredAmount = order.getSize() * order.getPrice();
            if (tryAsset.getUsableSize() < requiredAmount) {
                throw new Exception("Not enough TRY for this order");
            }
            tryAsset.setUsableSize(tryAsset.getUsableSize() - requiredAmount);
        } else if (order.getOrderSide() == OrderSide.SELL) {
            List<Asset> assets = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(),order.getAssetName());
            Asset asset= null;
            if(assets.size()>1)
            	asset = assets.get(0);
            else
            	throw new Exception("asset not found");
            if (asset.getUsableSize() < order.getSize()) {
                throw new Exception("Not enough asset to sell");
            }
            asset.setUsableSize(asset.getUsableSize() - order.getSize());
        }

        assetRepository.save(tryAsset);
        return orderRepository.save(order);
    }

    public List<Order> listOrders(String customerId, Date startDate, Date endDate) {
        return orderRepository.findByCustomerIdAndCreateDateBetween(customerId, startDate, endDate);
    }

    public void cancelOrder(Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new Exception("Only PENDING orders can be canceled");
        }

        if (order.getOrderSide() == OrderSide.BUY) {
        	 List<Asset> tryAssets = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(),"TRY");
             Asset tryAsset =null;
             if(tryAssets.size()>0)
           	  tryAsset=tryAssets.get(0);
             else
           	  tryAsset= new Asset(order.getCustomerId(), "TRY", 0, 0); // Create a new TRY asset if none exists
            tryAsset.setUsableSize(tryAsset.getUsableSize() + order.getSize() * order.getPrice());
            assetRepository.save(tryAsset);
        } else if (order.getOrderSide() == OrderSide.SELL) {
        	  List<Asset> assets = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(),order.getAssetName());
              Asset asset= null;
              if(assets.size()>1)
              	asset = assets.get(0);
              else
            	  asset= new Asset(order.getCustomerId(), order.getAssetName(), 0, 0); // Create a new TRY asset if none exists

            asset.setUsableSize(asset.getUsableSize() + order.getSize());
            assetRepository.save(asset);
        }

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }
    

    public void matchOrder(Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new Exception("Only PENDING orders can be matched");
        }

        if (order.getOrderSide() == OrderSide.BUY) {
        	 List<Asset> tryAssets = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(),"TRY");
             Asset tryAsset =null;
             if(tryAssets.size()>0)
           	  tryAsset=tryAssets.get(0);
             else
           	  tryAsset= new Asset(order.getCustomerId(), "TRY", 0, 0); // Create a new TRY asset if none exists
           
            double requiredAmount = order.getSize() * order.getPrice();
        

            tryAsset.setSize(tryAsset.getSize() - requiredAmount);

            List<Asset> assets = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(),order.getAssetName());
            Asset asset= null;
            if(assets.size()>1)
            	asset = assets.get(0);
            else
             	  asset= new Asset(order.getCustomerId(), order.getAssetName(), 0, 0); // Create a new TRY asset if none exists

            asset.setSize(asset.getSize() + order.getSize());
            asset.setUsableSize(asset.getUsableSize() + order.getSize());

            assetRepository.save(tryAsset);
            assetRepository.save(asset);
        }

        // Handling SELL orders
        if (order.getOrderSide() == OrderSide.SELL) {
        	  List<Asset> assets = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(),order.getAssetName());
              Asset asset= null;
              if(assets.size()>1)
              	asset = assets.get(0);
              else
            	  asset= new Asset(order.getCustomerId(), order.getAssetName(), 0, 0); // Create a new TRY asset if none exists

            if (asset.getUsableSize() < order.getSize()) {
                throw new Exception("Not enough asset to sell");
            }

            asset.setSize(asset.getSize() - order.getSize());

            List<Asset> tryAssets = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(),"TRY");
            Asset tryAsset =null;
            if(tryAssets.size()>0)
          	  tryAsset=tryAssets.get(0);
            else
          	  tryAsset= new Asset(order.getCustomerId(), "TRY", 0, 0); // Create a new TRY asset if none exists
          
            double totalAmount = order.getSize() * order.getPrice();
            tryAsset.setSize(tryAsset.getSize() + totalAmount);

            assetRepository.save(asset);
            assetRepository.save(tryAsset);
        }

        order.setStatus(OrderStatus.MATCHED);
        orderRepository.save(order);
    }
}