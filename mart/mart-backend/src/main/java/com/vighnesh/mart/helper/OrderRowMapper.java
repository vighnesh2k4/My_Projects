package com.vighnesh.mart.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vighnesh.mart.pojo.Order;

public class OrderRowMapper implements RowMapper<Order> {

	@Override
	public Order mapRow(ResultSet rs, int arg1) throws SQLException {
		Order order = new Order();
		order.setOrder_id(rs.getInt("order_id"));
		order.setUser_id(rs.getInt("user_id"));
		order.setTotal_amount(rs.getBigDecimal("total_amount"));
		order.setStatus(Order.Status.valueOf(rs.getString("status")));
		order.setOrder_date(rs.getTimestamp("order_date"));
		return order;
	}

}
