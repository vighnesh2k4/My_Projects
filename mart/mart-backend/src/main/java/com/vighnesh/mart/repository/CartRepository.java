package com.vighnesh.mart.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vighnesh.mart.helper.CartItemsRowMapper;
import com.vighnesh.mart.pojo.CartItems;
import com.vighnesh.mart.utility.DbQueries;

@Repository
public class CartRepository {
	
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
    public CartRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
	
	public void addCartItem(CartItems cartItems) {
		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("user_id", cartItems.getUser_id())
			.addValue("product_id", cartItems.getProduct_id())
			.addValue("quantity", cartItems.getQuantity())
			.addValue("price", cartItems.getPrice());
		namedParameterJdbcTemplate.update(DbQueries.INSERT_CART, params);
	}

	public void updateCartItem(CartItems cartItems){		
		StringBuilder sql = new StringBuilder("UPDATE cart_items SET quantity = :quantity, price=:price WHERE cart_id = :cart_id");
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("quantity", cartItems.getQuantity())
				.addValue("price", cartItems.getPrice())
				.addValue("cart_id", cartItems.getCart_id());
		namedParameterJdbcTemplate.update(sql.toString(), params);
	}

	public List<CartItems> getCartItem(CartItems cartItems) {
		int cartIdFlag=cartItems.getCart_id()> 0?1:0;
		int userIdFlag=cartItems.getUser_id()> 0?1:0;
		int productIdFlag=cartItems.getProduct_id()> 0?1:0;
		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("cart_id", cartItems.getCart_id())
			.addValue("user_id", cartItems.getUser_id())
			.addValue("product_id", cartItems.getProduct_id())
			.addValue("cartIdFlag", cartIdFlag)
			.addValue("userIdFlag", userIdFlag)
			.addValue("productIdFlag", productIdFlag);
		return namedParameterJdbcTemplate.query(DbQueries.GET_CART, params, new CartItemsRowMapper());
	}

	public void deleteCartItem(CartItems cartItems){
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("cart_id", cartItems.getCart_id());
		namedParameterJdbcTemplate.update(DbQueries.DELETE_CARTITEM, params);
	}
	
	public void deleteCartItems(CartItems cartItems){
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("user_id", cartItems.getUser_id());
		namedParameterJdbcTemplate.update(DbQueries.DELETE_CART, params);
	}
}
