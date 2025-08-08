package com.vighnesh.mart.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vighnesh.mart.pojo.OrderItems;

public class OrderItemsRowMapper implements RowMapper<OrderItems>{

	@Override
	public OrderItems mapRow(ResultSet rs, int arg1) throws SQLException {
		OrderItems orderItems = new OrderItems();
		orderItems.setOrder_items_id(rs.getInt("order_items_id"));
		orderItems.setOrder_id(rs.getInt("order_id"));
		orderItems.setProduct_id(rs.getInt("product_id"));
		orderItems.setQuantity(rs.getInt("quantity"));
		orderItems.setPrice(rs.getBigDecimal("price"));
		return orderItems;
	}

}
