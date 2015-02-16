package com.feisystems.bham.domain.gpra;

import javax.xml.bind.annotation.XmlElement;

import com.feisystems.bham.domain.asi.EnumGenderCode;

public class Cd_gpra_ivw_gender_id {

	@XmlElement
	public EnumGenderCode genderCode;

	@XmlElement
	public EnumRefusedMissing refusedMissing;

}
