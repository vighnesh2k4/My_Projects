package com.vighnesh.mart.pojo;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItems {
	private int order_items_id;
	private int order_id;
	private int product_id;
	private int quantity;
	private BigDecimal price;
}
