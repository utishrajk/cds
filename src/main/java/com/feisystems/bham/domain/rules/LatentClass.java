package com.feisystems.bham.domain.rules;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/*
 * The four Membership Probabilities classes
 * 
 * */
@XmlEnum
public enum LatentClass {
	
	@XmlEnumValue("CLASS1")
	CLASS1("CLASS1"),
	@XmlEnumValue("CLASS2")
	CLASS2("CLASS2"),
	@XmlEnumValue("CLASS3")
	CLASS3("CLASS3"),
	@XmlEnumValue("CLASS4")
	CLASS4("CLASS4");
	
	private final String name;

	LatentClass(String n) {
		name = n;
	}
	
   public static LatentClass fromValue(String n) {
        return valueOf(n);
    }
	 
    public String getName() {return name;}
}
