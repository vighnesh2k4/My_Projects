package com.vighnesh.mart.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vighnesh.mart.pojo.CartItems;

public class CartItemsRowMapper implements RowMapper<CartItems> {

	@Override
	public CartItems mapRow(ResultSet rs, int arg1) throws SQLException {
		CartItems cartItems = new CartItems();
		cartItems.setCart_id(rs.getInt("cart_id"));
		cartItems.setUser_id(rs.getInt("user_id"));
		cartItems.setProduct_id(rs.getInt("product_id"));
		cartItems.setQuantity(rs.getInt("quantity"));
		cartItems.setPrice(rs.getBigDecimal("price"));
		return cartItems;
	}

}
