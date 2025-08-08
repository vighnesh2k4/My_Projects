package com.vighnesh.mart.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vighnesh.mart.handler.MartException;
import com.vighnesh.mart.pojo.CartItems;
import com.vighnesh.mart.repository.CartRepository;

@Service
public class CartService {
	
	@Autowired
    private ProductService productService;
	
	private final CartRepository cartRepository;
	@Autowired
	public CartService(CartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}
	
	@Transactional
	public void addCartItem(CartItems cartItems) throws MartException {
		if (cartItems.getUser_id() <=0) {
			throw new MartException("Invalid user_id");
		}
		if (cartItems.getProduct_id() <=0) {
			throw new MartException("Invalid productId");
		}
		if (cartItems.getQuantity() <=0) {
			throw new MartException("Invalid quantity");
		}
		List<CartItems> existingProdInCart = cartRepository.getCartItem(cartItems);
		if (existingProdInCart.size() > 0) {
			cartItems.setCart_id(existingProdInCart.get(0).getCart_id());
			cartItems.setQuantity(existingProdInCart.get(0).getQuantity()+cartItems.getQuantity());
			updateCartItem(cartItems);
		}else {
			BigDecimal productPrice = productService.getProductPriceById(cartItems.getProduct_id()).multiply(BigDecimal.valueOf(cartItems.getQuantity()));
			cartItems.setPrice(productPrice);
			cartRepository.addCartItem(cartItems);
		}
	}
	
	@Transactional
	public List<CartItems> getCartItem(CartItems cartItems) throws MartException {
		if (cartItems.getCart_id() <= 0 && cartItems.getUser_id()<=0) {
			throw new MartException("Invalid cartItems");
		}
		return cartRepository.getCartItem(cartItems);
	}

	@Transactional
	public void updateCartItem(CartItems cartItems) throws MartException {
		if (cartItems.getCart_id() <= 0) {
			throw new MartException("Invalid cartItem Id");
		}
		if (cartItems.getQuantity()<=0) {
			cartRepository.deleteCartItem(cartItems);
		}else {	
			CartItems temp1=new CartItems();
			temp1.setCart_id(cartItems.getCart_id());
			List<CartItems> temp2=getCartItem(temp1);
			cartItems.setProduct_id(temp2.get(0).getProduct_id());
			BigDecimal productPrice = productService.getProductPriceById(cartItems.getProduct_id()).multiply(BigDecimal.valueOf(cartItems.getQuantity()));
			cartItems.setPrice(productPrice);
			cartRepository.updateCartItem(cartItems);
		}
	}
	
	@Transactional
	public void deleteCartItem(CartItems cartItems) throws MartException {
		if (cartItems.getCart_id() <= 0) {
			throw new MartException("Invalid cartItem Id");
		}
		cartRepository.deleteCartItem(cartItems);
	}
	
	@Transactional
	public void deleteCartItems(CartItems cartItems) throws MartException {
		if (cartItems.getUser_id() <= 0) {
			throw new MartException("Invalid User Id");
		}
		cartRepository.deleteCartItems(cartItems);
	}
}
