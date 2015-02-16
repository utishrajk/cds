package com.feisystems.bham.domain.reference;

import java.io.Serializable;
import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class RaceCode extends AbstractCodedConcept implements Serializable {

	private static final long serialVersionUID = 153249085734L;

	// public boolean equals(Object obj) {
	// if (!(obj instanceof RaceCode)) {
	// return false;
	// }
	// if (this == obj) {
	// return true;
	// }
	// RaceCode rhs = (RaceCode) obj;
	// return new EqualsBuilder().append(code, rhs.code).append(codeSystem,
	// rhs.codeSystem).append(codeSystemName,
	// rhs.codeSystemName).append(codeSystemVersion,
	// rhs.codeSystemVersion).append(displayName, rhs.displayName).append(id,
	// rhs.id).append(originalText, rhs.originalText).isEquals();
	// }
	//
	// public int hashCode() {
	// return new
	// HashCodeBuilder().append(code).append(codeSystem).append(codeSystemName).append(codeSystemVersion).append(displayName).append(id).append(originalText).toHashCode();
	// }

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
