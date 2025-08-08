package com.vighnesh.mart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vighnesh.mart.handler.MartException;
import com.vighnesh.mart.pojo.CartItems;
import com.vighnesh.mart.pojo.ResponseObject;
import com.vighnesh.mart.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	private final CartService cartService;	
	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@PostMapping("/{userId}/addCartItem")
	public ResponseObject addCartItem(@PathVariable int userId, @RequestBody CartItems cartItems) throws MartException {
		cartItems.setUser_id(userId);
		cartService.addCartItem(cartItems);
		ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, null, "Cart Item added successfully");
		return responseObject;
	}
	
	@GetMapping("/{userId}/getAllCartItems")
	public ResponseObject getAllCartItems(@PathVariable int userId) throws MartException {
		CartItems temp = new CartItems();
		temp.setUser_id(userId);
		ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, cartService.getCartItem(temp), "CartItems retrieved successfully");
		return responseObject;
	}
	
	@PutMapping("/{userId}/updateCartItem/{cartId}")
	public ResponseObject updateCartItem(@PathVariable int userId, @PathVariable int cartId, @RequestBody CartItems updateRequest) throws MartException{
		updateRequest.setUser_id(userId);	
		updateRequest.setCart_id(cartId);
		cartService.updateCartItem(updateRequest);
	 	ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, null, "Cart updated successfully");
		return responseObject;
	}
	
	@DeleteMapping("/{userId}/deleteCartItem/{cartId}")
	public ResponseObject deleteCartItem(@PathVariable int userId, @PathVariable int cartId) throws MartException{
		CartItems delCartItem = new CartItems();
		delCartItem.setUser_id(userId);
		delCartItem.setCart_id(cartId);
		cartService.deleteCartItem(delCartItem);
	 	ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, null, "CartItem deleted successfully");
		return responseObject;
	}
	
	@DeleteMapping("/{userId}/clearCartItems")
	public ResponseObject clearCart(@PathVariable int userId) throws MartException{
		CartItems delCartItems = new CartItems();
		delCartItems.setUser_id(userId);
		cartService.deleteCartItems(delCartItems);
	 	ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, null, "Cart Cleared successfully");
		return responseObject;
	}
}
