package com.feisystems.bham.domain.asi;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(String.class)
public enum EnumAsiRoute {

	@XmlEnumValue("IJ")
	IJ("IJ"),

	@XmlEnumValue("IT")
	IT("IT"),

	@XmlEnumValue("N")
	N("N"),

	@XmlEnumValue("NJ")
	NJ("NJ"),

	@XmlEnumValue("OR")
	OR("OR"),

	@XmlEnumValue("SM")
	SM("SM"),

	@XmlEnumValue("X")
	X("X");

	private String value;

	private EnumAsiRoute(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
