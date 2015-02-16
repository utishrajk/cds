package com.feisystems.bham.domain.gpra;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(Integer.class)
public enum EnumRefusedMissing {
	
    @XmlEnumValue("-7") REFUSED(-7),
        
    @XmlEnumValue("-9") MISSING(-9);
    
	private int value;
    
    private EnumRefusedMissing(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}
