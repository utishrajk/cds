package com.feisystems.bham.domain.gpra;

import javax.xml.bind.annotation.XmlElement;

public class Er_physical_ind {

	@XmlElement
	public EnumYesNo yesNo;
	
	@XmlElement
	public EnumRefusedDknowMissing refusedDknowMissing;
	
}