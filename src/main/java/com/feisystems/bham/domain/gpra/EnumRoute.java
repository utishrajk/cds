package com.feisystems.bham.domain.gpra;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(Integer.class)
public enum EnumRoute {
   
    @XmlEnumValue("1") ORAL(1),
        
    @XmlEnumValue("2") NASAL(2),

    @XmlEnumValue("3") SMOKING(3),
    
    @XmlEnumValue("4") NON_IV_INJECTION(4),

    @XmlEnumValue("5") IV(5),
    
    @XmlEnumValue("-1") NOT_APPLICABLE(-1),
    
    @XmlEnumValue("-7") REFUSED(-7),
    
    @XmlEnumValue("-8") DONT_KNOW(-8),
    
    @XmlEnumValue("-9") MISSING(-9);
    
	private int value;
    
    private EnumRoute(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}
