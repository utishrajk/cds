package com.feisystems.bham.domain.gpra;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(Integer.class)
public enum EnumYesNo {
	
    @XmlEnumValue("1") YES(1),
        
    @XmlEnumValue("0") NO(0),
    
    @XmlEnumValue("-7") REFUSED(-7),
    
    @XmlEnumValue("-8") DONT_KNOW(-8),
    
    @XmlEnumValue("-9") MISSING(-9);

    private final int value;
    private EnumYesNo(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
