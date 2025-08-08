package com.vighnesh.mart.pojo;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class CartItems {
	private int cart_id;
	private int user_id;
	private int product_id;
	private int quantity;
	private BigDecimal price;
}
