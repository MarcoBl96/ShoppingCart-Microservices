package com.controller;

import com.model.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.model.CustomerOrder;


import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderRepository orderRepository;

	@PostMapping("/newOrder/")
	public void createOrder(@Valid @RequestBody CustomerOrder customerOrderRequest) {

		orderRepository.save(customerOrderRequest);
	}
}
