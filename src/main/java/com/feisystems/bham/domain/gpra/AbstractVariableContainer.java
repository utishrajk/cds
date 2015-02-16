package com.feisystems.bham.domain.gpra;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.feisystems.bham.domain.asi.AsiVariableContainer;

@XmlRootElement(name = "Variables")
public class AbstractVariableContainer {

	@XmlElements(value = {
			@XmlElement(name = "ASI",
					type = AsiVariableContainer.class),
			@XmlElement(name = "GPRA",
					type = GpraVariableContainer.class)
	})
	public Object variableContainer;

	@XmlElement
	public String requestId;

	@XmlElement
	public String staffId;

	@XmlElement
	public String uniqueClientNumber;
	
}
