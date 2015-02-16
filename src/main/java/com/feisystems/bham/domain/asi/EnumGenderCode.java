package com.feisystems.bham.domain.asi;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(String.class)
public enum EnumGenderCode {

	@XmlEnumValue("FE")
	FE("FE"),

	@XmlEnumValue("MA")
	MA("MA"),

	@XmlEnumValue("TG")
	TG("TG"),

	@XmlEnumValue("UN")
	UN("UN");

	private String value;

	private EnumGenderCode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
