package com.BookStore.demo.controller;

import com.BookStore.demo.dto.PlaceOrderRequest;
import com.BookStore.demo.entity.Order;
import com.BookStore.demo.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/{userId}")
	public ResponseEntity<Order> placeOrder(@PathVariable Long userId,
											@RequestBody PlaceOrderRequest request) {
		Order order = orderService.placeOrder(userId, request.getBookIsbns());
		return ResponseEntity.ok(order);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<Order>> getAllOrdersByUserId(@PathVariable Long userId) {
		List<Order> orders = orderService.getAllOrdersByUserId(userId);
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/details/{orderId}")
	public ResponseEntity<Order> getOrderDetailsById(@PathVariable Long orderId) {
		Order order = orderService.getOrderDetailsById(orderId);
		return ResponseEntity.ok(order);
	}
}