package com.feisystems.bham.domain.gpra;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(Integer.class)
public enum EnumHealthStatus {
	
    @XmlEnumValue("1") EXCELLENT(1),
    
    @XmlEnumValue("2") VERY_GOOD(2),
    
    @XmlEnumValue("3") GOOD(3),
    
    @XmlEnumValue("4") FAIR(4),

    @XmlEnumValue("5") POOR(5),
    
    @XmlEnumValue("-7") REFUSED(-7),
    
    @XmlEnumValue("-8") DONT_KNOW(-8),
    
    @XmlEnumValue("-9") MISSING(-9);
    
	private int value;
    
    private EnumHealthStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
}