package com.feisystems.bham.domain.reference;

import java.io.Serializable;

import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class SecurityQuestionsCode extends AbstractCodedConcept implements Serializable {

	private static final long serialVersionUID = 9008395030240574478L;

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
