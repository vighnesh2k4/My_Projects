package com.vighnesh.mart.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.vighnesh.mart.helper.OrderItemsRowMapper;
import com.vighnesh.mart.helper.OrderRowMapper;
import com.vighnesh.mart.pojo.Order;
import com.vighnesh.mart.pojo.OrderItems;
import com.vighnesh.mart.utility.DbQueries;

@Repository
public class OrderRepository {
	
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
    public OrderRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
	
	public int addOrder(Order order) {
		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("user_id", order.getUser_id())
			.addValue("total_amount", order.getTotal_amount())
			.addValue("status", order.getStatus().name());
		KeyHolder keyHolder = new GeneratedKeyHolder();
	    namedParameterJdbcTemplate.update(DbQueries.INSERT_ORDER, params, keyHolder, new String[]{"order_id"});
	    
	    return keyHolder.getKey().intValue();
	}

	public void updateOrder(Order order){
		int statusFlag= order.getStatus()!=null?1:0;
		
		StringBuilder sql = new StringBuilder("UPDATE orders SET ");
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		List<String> setClauses = new ArrayList<>();
		if (statusFlag == 1) {
		    setClauses.add("status = :status");
		    params.addValue("status", order.getStatus().name());
		}
		if (setClauses.isEmpty()) {
		    throw new IllegalArgumentException("No fields to update.");
		}

		sql.append(String.join(", ", setClauses));
		sql.append(" WHERE order_id = :order_id");
		params.addValue("order_id", order.getOrder_id());

		namedParameterJdbcTemplate.update(sql.toString(), params);
	}

	public List<Order> getOrder(Order order) {
		int orderIdFlag=order.getOrder_id()>0?1:0;
		int userIdFlag=order.getUser_id()>0?1:0;
		int statusFlag=order.getStatus()!=null?1:0;
		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("order_id", order.getOrder_id())
			.addValue("user_id", order.getUser_id())
			.addValue("status", order.getStatus())
			.addValue("orderIdFlag", orderIdFlag)
			.addValue("userIdFlag", userIdFlag)
			.addValue("statusFlag", statusFlag);
		return namedParameterJdbcTemplate.query(DbQueries.GET_ORDER, params, new OrderRowMapper());
	}
	
	public void addOrderItem(OrderItems orderItems) {
		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("order_id", orderItems.getOrder_id())
			.addValue("product_id", orderItems.getProduct_id())
			.addValue("quantity", orderItems.getQuantity())
			.addValue("price", orderItems.getPrice());
		namedParameterJdbcTemplate.update(DbQueries.INSERT_ORDERITEM, params);
	}

	public List<OrderItems> getOrderItem(OrderItems orderItems) {
		int orderItemsIdFlag=orderItems.getOrder_items_id()>0?1:0;
		int orderIdFlag=orderItems.getOrder_id()>0?1:0;
		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("order_items_id", orderItems.getOrder_items_id())
			.addValue("order_id", orderItems.getOrder_id())
			.addValue("orderItemsIdFlag", orderItemsIdFlag)
			.addValue("orderIdFlag", orderIdFlag);
		return namedParameterJdbcTemplate.query(DbQueries.GET_ORDERITEM, params, new OrderItemsRowMapper());
	}
	
}
