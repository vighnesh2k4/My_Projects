package com.kbc.KbcApp.pojos;


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
	private String message;
	private Object dataObject;
	private Status status;

	
}