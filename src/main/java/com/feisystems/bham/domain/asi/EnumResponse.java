package com.feisystems.bham.domain.asi;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(String.class)
public enum EnumResponse {

	@XmlEnumValue("YES")
	YES("YES"),

	@XmlEnumValue("NO")
	NO("NO"),

	@XmlEnumValue("NOT APPLICABLE")
	NOT_APPLICABLE("NOT APPLICABLE"),

	@XmlEnumValue("NOT ANSWERED")
	NOT_ANSWERED("NOT ANSWERED");

	private String value;

	private EnumResponse(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
