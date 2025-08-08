package com.vighnesh.mart.utility;

public class DbQueries {
	//User Queries
	public static final String INSERT_USER="INSERT INTO users(username, email, password) VALUES(:username, :email, :password)";
	public static final String GET_USER="SELECT user_id, username, email, password, role, status, created_at FROM users WHERE (:userIdFlag=0 or user_id=:user_id) and (:usernameFlag=0 or username=:username) and (:passwordFlag=0 or password=:password)";
		
	//Product Queries
	public static final String INSERT_PRODUCT="INSERT INTO products(name, description, price) VALUES(:name, :description, :price)";
	public static final String GET_PRODUCT="SELECT product_id, name, description, price, status, created_at FROM products WHERE (:productIdFlag=0 or product_id=:product_id) and (:productNameFlag=0 or name=:name)";
	
	//Cart Queries
	public static final String INSERT_CART="INSERT INTO cart_items(user_id, product_id, quantity, price) VALUES(:user_id, :product_id, :quantity, :price)";
	public static final String GET_CART="SELECT cart_id, user_id, product_id, quantity, price FROM cart_items WHERE (:cartIdFlag=0 or cart_id=:cart_id) and (:userIdFlag=0 or user_id=:user_id) and (:productIdFlag=0 or product_id=:product_id)";
	public static final String DELETE_CARTITEM="DELETE FROM cart_items WHERE cart_id = :cart_id";
	public static final String DELETE_CART="DELETE FROM cart_items WHERE user_id = :user_id";
	
	//Order Queries
	public static final String INSERT_ORDER="INSERT INTO orders(user_id, total_amount, status) VALUES(:user_id, :total_amount, :status)";
	public static final String GET_ORDER="SELECT order_id, user_id, total_amount, status, order_date FROM orders WHERE (:orderIdFlag=0 or order_id=:order_id) and (:userIdFlag=0 or user_id=:user_id) and (:statusFlag=0 or status=:status)";
	
	//OrderItems Queries
	public static final String INSERT_ORDERITEM="INSERT INTO order_items(order_id, product_id, quantity, price) VALUES(:order_id, :product_id, :quantity, :price)";
	public static final String GET_ORDERITEM="SELECT order_items_id, order_id, product_id, quantity, price FROM order_items WHERE (:orderItemsIdFlag=0 or order_items_id=:order_items_id) and (:orderIdFlag=0 or order_id=:order_id)";
}
