package com.example.supplier;

public class ErrorResponse {
	private String code;
	private String message;

	public ErrorResponse(String code, String message) {
		this.code = code;
		this.message = message;
		system.out.println("ErrorResponse created with code: " + code + " and message: " + message);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
