package com.vighnesh.mart.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObject {

	public enum Status{
		SUCCESS,
		FAILURE
	}
	private Status status;
	private Object dataObject;
	private String message;
}