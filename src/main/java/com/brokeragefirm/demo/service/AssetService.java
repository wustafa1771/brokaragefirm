package com.brokeragefirm.demo.service;

import com.brokeragefirm.demo.model.Asset;
import com.brokeragefirm.demo.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {

    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public void depositMoney(String customerId, double amount) {
        List<Asset> tryAssets = assetRepository.findByCustomerIdAndAssetName(customerId,"TRY");
        Asset asset =null;
        if(tryAssets.size()>0)
        	asset=tryAssets.get(0);
        else
        	asset= new Asset(customerId, "TRY", 0, 0); // Create a new TRY asset if none exists
        
        asset.setSize(asset.getSize() + amount);
        asset.setUsableSize(asset.getUsableSize() + amount);
        assetRepository.save(asset);
    }

    public void withdrawMoney(String customerId, double amount) throws Exception {
        List<Asset> tryAssets = assetRepository.findByCustomerIdAndAssetName(customerId,"TRY");
        Asset asset =null;
        if(tryAssets.size()>0)
        	asset=tryAssets.get(0);
        else
        	asset= new Asset(customerId, "TRY", 0, 0); // Create a new TRY asset if none exists
        
        if (asset.getUsableSize() < amount) {
            throw new Exception("Insufficient TRY balance");
        }

        asset.setUsableSize(asset.getUsableSize() - amount);
        asset.setSize(asset.getSize() - amount);
        assetRepository.save(asset);
    }

    public List<Asset> listAssets(String customerId) {
        return assetRepository.findByCustomerId(customerId);
    }

}
