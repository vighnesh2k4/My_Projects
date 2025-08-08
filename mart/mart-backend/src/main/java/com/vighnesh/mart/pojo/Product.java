package com.vighnesh.mart.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
	public enum Status{
		ACTIVE,
		INACTIVE
	}
	private int product_id;
	private String name;
	private String description;
	private BigDecimal price;
	private Status status;
	private Timestamp created_at;
}
