package com.vighnesh.mart.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vighnesh.mart.handler.MartException;
import com.vighnesh.mart.pojo.CartItems;
import com.vighnesh.mart.pojo.Order;
import com.vighnesh.mart.pojo.OrderItems;
import com.vighnesh.mart.repository.OrderRepository;

@Service
public class OrderService {
	
	private final CartService cartService;
	private final OrderRepository orderRepository;
	
	@Autowired
	public OrderService(OrderRepository orderRepository, CartService cartService) {
		this.cartService=cartService;
		this.orderRepository = orderRepository;
	}
	
	@Transactional
	public void addOrder(Order order) throws MartException {
		if (order.getUser_id() <= 0) {
			throw new MartException("Invalid user_id");
		}
	    CartItems userIdCart= new CartItems();
	    userIdCart.setUser_id(order.getUser_id());
		List<CartItems> cartItems = cartService.getCartItem(userIdCart);
	    if (cartItems == null || cartItems.isEmpty()) {
	        throw new MartException("Cart is empty, cannot place order.");
	    }
	    BigDecimal total = cartItems.stream()
	            .map(item -> item.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
	    order.setTotal_amount(total);
		order.setStatus(Order.Status.COMPLETED);
		
	    int newOrderId = orderRepository.addOrder(order);
	    
		for (CartItems cartItem : cartItems) {
			OrderItems orderItem = new OrderItems();
			orderItem.setOrder_id(newOrderId);
	        orderItem.setProduct_id(cartItem.getProduct_id());
	        orderItem.setQuantity(cartItem.getQuantity());
	        orderItem.setPrice(cartItem.getPrice());
			orderRepository.addOrderItem(orderItem);
	    }
		CartItems clearItems= new CartItems();
		clearItems.setUser_id(order.getUser_id());
		cartService.deleteCartItems(clearItems);
	}
	
	@Transactional
	public List<Order> getOrder(Order order) throws MartException {
		if (order.getOrder_id() <= 0 && order.getUser_id()<=0) {
			throw new MartException("Invalid orders");
		}
		return orderRepository.getOrder(order);
	}

	@Transactional
	public void updateOrder(Order order) throws MartException {
		if (order.getOrder_id() <= 0) {
			throw new MartException("Invalid order Id");
		}
		orderRepository.updateOrder(order);
	}
	
	@Transactional
	public List<OrderItems> getOrderItem(OrderItems orderItems) throws MartException {
		if (orderItems.getOrder_id() <= 0 && orderItems.getOrder_items_id()<=0) {
			throw new MartException("Invalid orderItems");
		}
		return orderRepository.getOrderItem(orderItems);
	}
}
