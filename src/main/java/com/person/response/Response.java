package com.person.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Response<T> {

	Boolean status;
	String message;
	T data;
	
	public Response(Boolean status, String message) {
		
		this.status = status;
		this.message = message;
	}
	
	public Response(Boolean status, String message, T data) {
		
		this.status = status;
		this.message = message;
		this.data = data;
	}
}
