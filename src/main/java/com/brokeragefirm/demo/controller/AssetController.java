package com.brokeragefirm.demo.controller;


import com.brokeragefirm.demo.model.Asset;
import com.brokeragefirm.demo.model.DepositWithdrawRequestDTO;
import com.brokeragefirm.demo.service.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> depositMoney(@RequestBody DepositWithdrawRequestDTO request) {
        assetService.depositMoney(request.getCustomerId(), request.getAmount());
        return ResponseEntity.ok("Deposit successful");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdrawMoney(@RequestBody DepositWithdrawRequestDTO request) {
        try {
            assetService.withdrawMoney(request.getCustomerId(), request.getAmount());
            return ResponseEntity.ok("Withdrawal successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> listAssets(@RequestParam String customerId) {
        return ResponseEntity.ok(assetService.listAssets(customerId));
    }
}