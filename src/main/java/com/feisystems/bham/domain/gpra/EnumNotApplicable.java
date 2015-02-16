package com.feisystems.bham.domain.gpra;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(Integer.class)
public enum EnumNotApplicable {
	
    @XmlEnumValue("-1") NOT_APPLICABLE(-1);
    
	private int value;
    
    private EnumNotApplicable(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
