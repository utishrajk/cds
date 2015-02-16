
package com.feisystems.bham.domain.rules;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class FactModel.
 */
@XmlRootElement(name="FactModel")
@XmlAccessorType(XmlAccessType.FIELD)
public class FactModel {
	
	/** The membershipClass. */
	private MembershipClass membershipClass;
	/**
	 * Sets the membershipClass fact .
	 *
	 * @param membershipClass the new membershipClass fact
	 */
	public void setMembershipClass(MembershipClass membershipClass) {
		this.membershipClass = membershipClass;
	}
	/**
	 * Gets the MembershipClass references.
	 *
	 * @return the MembershipClass references
	 */
	public MembershipClass getMembershipClass() {
		return membershipClass;
	}
}
