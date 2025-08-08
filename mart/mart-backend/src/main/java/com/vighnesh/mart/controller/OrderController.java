package com.vighnesh.mart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vighnesh.mart.handler.MartException;
import com.vighnesh.mart.pojo.Order;
import com.vighnesh.mart.pojo.OrderItems;
import com.vighnesh.mart.pojo.ResponseObject;
import com.vighnesh.mart.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	private final OrderService orderService;	
	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@PostMapping("{userId}/placeOrder")
	public ResponseObject createOrder(@PathVariable int userId) throws MartException {
		Order order = new Order();
		order.setUser_id(userId);
		orderService.addOrder(order);
		ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, null, "Order created successfully");
		return responseObject;
	}
	
	@GetMapping("/{userId}/getAllOrders")
	public ResponseObject getAllOrders(@PathVariable int userId) throws MartException {
		Order temp = new Order();
		temp.setUser_id(userId);
		ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, orderService.getOrder(temp), "Orders retrieved successfully");
		return responseObject;
	}
	
	@PutMapping("/{userId}/updateOrder/{orderId}")
	public ResponseObject updateOrder(@PathVariable int userId, @PathVariable int orderId, @RequestBody Order updateRequest) throws MartException{
		updateRequest.setUser_id(userId);	
		updateRequest.setOrder_id(orderId);
		orderService.updateOrder(updateRequest);
	 	ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, null, "Order updated successfully");
		return responseObject;
	}
	
	@GetMapping("/getOrderItems/{orderId}")
	public ResponseObject getOrderItems(@PathVariable int orderId) throws MartException {
		OrderItems temp = new OrderItems();
		temp.setOrder_id(orderId);
		ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, orderService.getOrderItem(temp), "OrderItems retrieved successfully");
		return responseObject;
	}
}
