package com.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
	private final UserRepository userRepository;
	private final CustomerRepository customerRepository;

	//username = email

	@RequestMapping("/getCustomer/{customerId}")
	public Optional<Customer> getCustomer(@PathVariable(value = "customerId") Long customerId) {
		System.out.println("========Get Customer======");
		return customerRepository.findById(customerId);

	}


	@RequestMapping("/{username}")
	public User getProductById(@PathVariable(value = "username") String username) {
		System.out.println("========LOGIN======");
		System.out.println(username);
		return userRepository.findByemailId(username);

	}
	@RequestMapping(value = "/getCustomerIdFromUsername/{username}")
	public Long customerId(@PathVariable(value = "username") String username)
	{
		User user = userRepository.findByemailId(username);
		Customer customer = customerRepository.findByUser(user);
		System.out.println("CUSTOMER GEFUNDEN");
		System.out.println(customer.getFirstName());
		Long customerId = customerRepository.findByUser(user).getCustomerId();
		System.out.println("getCustomerIdFrom Username = " + customerId);
		return customerId;

	}

	@PostMapping(value = "/registration")
	@ResponseStatus(HttpStatus.CREATED)
	public Customer getRegistrationForm(@Valid @RequestBody Customer customer) {
		System.out.println("Save User in Database");
		return customerRepository.save(customer);
	}

	@PutMapping(value = "/editCustomer/{customerId}")
	public void updateProduct(@PathVariable("customerId") @Min(1) long customerId, @Valid @RequestBody Customer customerRequest) {
		final Optional<Customer> customer = customerRepository.findById(customerId);
		final Customer customerModel = customer.orElseThrow(() -> new ResourceNotFoundException("Customer "+customerId+" not found"));

		// This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
		customerModel.setCustomerId(customerRequest.getCustomerId());
		customerModel.setCustomerPhone(customerRequest.getCustomerPhone());
		customerModel.setCart(customerRequest.getCart());
		customerModel.setBillingAddress(customerRequest.getBillingAddress());
		customerModel.setShippingAddress(customerRequest.getShippingAddress());
		customerModel.setUsers(customerRequest.getUsers());
		customerModel.setFirstName(customerRequest.getFirstName());
		customerModel.setLastName(customerRequest.getLastName());
		log.info("Saving customer {}", customerModel);

		System.out.println("Billing-ID: " + customerModel.getBillingAddress().getBillindAddressId());
		System.out.println("Billing-ID Request: " + customerRequest.getBillingAddress().getBillindAddressId());

		customerRepository.save(customerModel);
	}
}

