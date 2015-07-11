package com.epam.interviews;

public class TextMessage extends Message {

	private String message = null;
	
	public TextMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.message;
	}

	@Override
	public void setMessage(Object message) {
		// TODO Auto-generated method stub
		this.message = (String) message;
	}
	
	public String toString() {
		return this.message;
	}
	
}
