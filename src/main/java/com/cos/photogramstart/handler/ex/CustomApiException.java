package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomApiException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
//	private String message;
//	private Map<String, String>errorMap;
	
	public CustomApiException(String message) {
		super(message);
	}
	
//	public CustomApiException(String message, Map<String, String>errorMap) {
//		super(message);
////		this.message = message;
//		this.errorMap = errorMap;
//	}
//	public Map<String, String>getErrorMap() {
//		return errorMap;
//	}

}
