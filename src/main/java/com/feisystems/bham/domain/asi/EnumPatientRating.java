package com.feisystems.bham.domain.asi;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(Integer.class)
public enum EnumPatientRating {
	
    @XmlEnumValue("1") NOT_AT_ALL(1),
    
    @XmlEnumValue("2") SLIGHTLY(2),
    
    @XmlEnumValue("3") MODERATELY(3),
    
    @XmlEnumValue("4") CONSIDERABLY(4),

    @XmlEnumValue("5") EXTREMELY(5),
    
    @XmlEnumValue("6") NOT_ANSWERED(6);
    
	private int value;
    
    private EnumPatientRating(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
}