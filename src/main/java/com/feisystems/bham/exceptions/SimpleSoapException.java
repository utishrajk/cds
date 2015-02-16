package com.feisystems.bham.exceptions;

import javax.xml.ws.WebFault;

@WebFault(name="SimpleSoapException")
public class SimpleSoapException extends Exception{

	private static final long serialVersionUID = -8706125687462556146L;
	
	private SimpleSoapExceptionBean faultBean;
 
    public SimpleSoapException(String message, SimpleSoapExceptionBean faultInfo){
        super(message);
        faultBean = faultInfo;
    }
 
    public SimpleSoapException(String message, SimpleSoapExceptionBean faultInfo, Throwable cause) {
        super(message, cause);
        faultBean = faultInfo;
    }
 
    public SimpleSoapExceptionBean getFaultInfo(){
        return faultBean;
    }
}