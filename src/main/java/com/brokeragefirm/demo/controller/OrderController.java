package com.brokeragefirm.demo.controller;


import com.brokeragefirm.demo.model.Order;
import com.brokeragefirm.demo.model.OrderDTO;
import com.brokeragefirm.demo.repository.*;
import com.brokeragefirm.demo.service.OrderService;

import com.brokeragefirm.demo.service.AssetService;
import java.sql.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@GetMapping("/hello")
	public String hello() {
		return "hello world";
	}

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
        	Order createdOrder  =  Order.createOrder(orderDTO);
            createdOrder = orderService.createOrder(createdOrder);
            return ResponseEntity.ok(createdOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Order>> listOrders(
            @RequestParam String customerId,
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        List<Order> orders = orderService.listOrders(customerId, startDate, endDate);
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        try {
            orderService.cancelOrder(orderId);
            return ResponseEntity.ok("Order canceled successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/match/{orderId}")
    public ResponseEntity<String> matchOrder(@PathVariable Long orderId) {
        try {
            orderService.matchOrder(orderId);
            return ResponseEntity.ok("Order matched successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
