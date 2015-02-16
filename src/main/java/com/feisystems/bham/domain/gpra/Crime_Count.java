package com.feisystems.bham.domain.gpra;

import javax.xml.bind.annotation.XmlElement;

public class Crime_Count {

	@XmlElement
	public Integer count;
	
	@XmlElement
	public EnumRefusedDknowMissing refusedDknowMissing;
	
}
