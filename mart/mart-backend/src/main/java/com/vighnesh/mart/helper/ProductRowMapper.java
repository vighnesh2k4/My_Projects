package com.vighnesh.mart.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vighnesh.mart.pojo.Product;

public class ProductRowMapper implements RowMapper<Product>{

	@Override
	public Product mapRow(ResultSet rs, int arg1) throws SQLException {
		Product product = new Product();
		product.setProduct_id(rs.getInt("product_id"));
		product.setName(rs.getString("name"));
		product.setDescription(rs.getString("description"));
		product.setPrice(rs.getBigDecimal("price"));
		product.setStatus(Product.Status.valueOf(rs.getString("status")));
		product.setCreated_at(rs.getTimestamp("created_at"));
		return product;
	}
	
}
