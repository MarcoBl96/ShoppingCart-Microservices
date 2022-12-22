package com.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/carts")
@RestController
@RequiredArgsConstructor
public class CartController {
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;

	@PostMapping("/removeCartItem/{cartItemId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeCartItem(@PathVariable(value = "cartItemId") Long cartItemId) {

		cartItemRepository.deleteById(cartItemId);

	}

	@PostMapping("/removeAllItems/{cartId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeAllCartItems(@PathVariable(value = "cartId") Long cartId) {
		cartItemRepository.deleteCartItems(cartId);
		updateTotalPrice(cartId);
	}


	@PutMapping(value = "/addProductToCart/{customerId}")
	public void updateProduct(@PathVariable(value = "customerId") @Min(1) Long customerId, @Valid @RequestBody Product productRequest) {
		System.out.println("AddProductToCart");
		System.out.println(productRequest.getProductName());
		System.out.println(productRequest.getProductId());

		System.out.println(customerId);
		Cart cart = cartRepository.findCartbyCustomerId(customerId);

		if(cart == null)
		{
			cart = new Cart();
			cart.setCustomerId(customerId);
			System.out.println("Kein Cart vorhanden --> Neues Cart erstellt");
			cartRepository.save(cart);

		}
		System.out.println(cart.getCartId());
		List<CartItem> cartItems = cart.getCartItem();

		Product product = productRequest;
		if(cartItems != null)
		{
			for (int i = 0; i < cartItems.size(); i++) {
				CartItem cartItem = cartItems.get(i);
				if (product.getProductId().equals(cartItem.getProductId())) {
					cartItem.setQuality(cartItem.getQuality() + 1);
					cartItem.setPrice(cartItem.getQuality() * cartItem.getPrice());
					cartItemRepository.save(cartItem);
					updateTotalPrice(cart.getCartId());
					return;
				}
			}
		}


		CartItem cartItem = new CartItem();
		cartItem.setQuality(1);
		cartItem.setProductId(product.getProductId());
		cartItem.setProductName(product.getProductName());
		cartItem.setPrice(product.getProductPrice() * 1);
		cartItem.setCart(cart);
		cartItemRepository.save(cartItem);
		updateTotalPrice(cart.getCartId());
	}

	@RequestMapping("/{cartId}")
	public Cart getCartById(@PathVariable(value = "cartId") long cartId) {
		System.out.println("========Get CartbyId======");
		System.out.println(cartId);
		Cart cart = cartRepository.getById(cartId);
		List<CartItem> cartItems = cart.getCartItem();
		double grandTotal=0;

		if(cartItems == null)
		{
			grandTotal = 0;
		}
		else
		{
			for(CartItem item: cartItems){
				grandTotal += item.getPrice()*item.getQuality();
			}
		}
		cart.setTotalPrice(grandTotal);
		return cart;

	}

	@RequestMapping("/getCartId/{customerId}")
	public Long getCartId(@PathVariable(value = "customerId") @Min(1) Long customerId){
		System.out.println("Get Cart ID: CustomerId = " + customerId);
		Cart cart = cartRepository.findCartbyCustomerId(customerId);
		if(cart == null)
		{
			return (long) 0;

		}
		return cart.getCartId();
	}

	public double updateTotalPrice(long cartId) {

		Optional<Cart> cart = cartRepository.findById(cartId);

		List<CartItem> cartItems = cart.get().getCartItem();
		double grandTotal=0;

		if(cartItems == null)
		{
			grandTotal = 0;
		}
		else
		{
			for(CartItem item: cartItems){
				grandTotal += item.getPrice()*item.getQuality();
			}
		}

		cartRepository.updateCartTotalPrice(grandTotal, cartId);
		return grandTotal;
	}
}

