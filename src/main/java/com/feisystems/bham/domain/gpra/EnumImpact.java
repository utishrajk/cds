package com.feisystems.bham.domain.gpra;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(Integer.class)
public enum EnumImpact {
	
    @XmlEnumValue("1") NOT_AT_ALL(1),
    
    @XmlEnumValue("2") SOMEWHAT(2),
    
    @XmlEnumValue("3") CONSIDERABLY(3),
    
    @XmlEnumValue("4") EXTREMELY(4),

    @XmlEnumValue("5") NOT_APPLICABLE(5);
    
	private int value;
    
    private EnumImpact(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    
}