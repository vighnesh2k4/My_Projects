package com.vighnesh.mart.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vighnesh.mart.helper.ProductRowMapper;
import com.vighnesh.mart.pojo.Product;
import com.vighnesh.mart.utility.DbQueries;

@Repository
public class ProductRepository {
	
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
    public ProductRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
	
	public void addProduct(Product product) {
		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("name", product.getName())
			.addValue("description", product.getDescription())
			.addValue("price", product.getPrice());
		namedParameterJdbcTemplate.update(DbQueries.INSERT_PRODUCT, params);
	}

	public void updateProduct(Product product){
		int descriptionFlag=product.getDescription()!=null?1:0;
		int priceFlag=product.getPrice()!=null?1:0;
		int statusFlag=product.getStatus()!=null?1:0;
		
		StringBuilder sql = new StringBuilder("UPDATE products SET ");
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		List<String> setClauses = new ArrayList<>();
		if (descriptionFlag == 1) {
		    setClauses.add("description = :description");
		    params.addValue("description", product.getDescription());
		}
		if (priceFlag == 1) {
		    setClauses.add("price = :price");
		    params.addValue("price", product.getPrice());
		}
		if (statusFlag == 1) {
		    setClauses.add("status = :status");
		    params.addValue("status", product.getStatus().name());
		}
		if (setClauses.isEmpty()) {
		    throw new IllegalArgumentException("No fields to update.");
		}

		sql.append(String.join(", ", setClauses));
		sql.append(" WHERE product_id = :product_id");
		params.addValue("product_id", product.getProduct_id());

		namedParameterJdbcTemplate.update(sql.toString(), params);
	}

	public List<Product> getProduct(Product product) {
		int productIdFlag=(product.getProduct_id() > 0) ?1:0;
		int productNameFlag=product.getName()!=null?1:0;
		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("product_id", product.getProduct_id())
			.addValue("name", product.getName())
			.addValue("productIdFlag", productIdFlag)
			.addValue("productNameFlag", productNameFlag);
		return namedParameterJdbcTemplate.query(DbQueries.GET_PRODUCT, params, new ProductRowMapper());
	}
	
}
