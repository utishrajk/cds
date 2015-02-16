package com.feisystems.bham.domain.rules;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Assessment {
	@XmlEnumValue("ASI")
	ASI("ASI"),
	@XmlEnumValue("GPRA")
	GPRA("GPRA");
	
	private final String name;

	Assessment(String n) {
		name = n;
	}
	
   public static Assessment fromValue(String n) {
        return valueOf(n);
    }
	 
    public String getName() {return name;}
}
