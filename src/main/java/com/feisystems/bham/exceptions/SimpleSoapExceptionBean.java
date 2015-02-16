package com.feisystems.bham.exceptions;

public class SimpleSoapExceptionBean {
   
	private String message;
 
    public SimpleSoapExceptionBean() {
    }
    public SimpleSoapExceptionBean(String message) {
        this.message = message;
    }
 
    public String getMessage() {
        return message;
    }
 
}