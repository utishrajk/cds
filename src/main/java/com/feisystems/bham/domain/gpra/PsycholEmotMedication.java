package com.feisystems.bham.domain.gpra;

import javax.xml.bind.annotation.XmlElement;

public class PsycholEmotMedication {
	
	@XmlElement
	public EnumDays days;
	
	@XmlElement
	public EnumRefusedDknowMissing refusedDknowMissing;
	
}
