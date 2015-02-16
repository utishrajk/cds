package com.feisystems.bham.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;



/**
 * @author tomson.ngassa
 *
 */
@Entity
public class OrganizationalProvider extends AbstractProvider implements Serializable {

    /**
     */
    @Size(max = 30)
    private String otherOrgName;

    /**
     */
    @Size(max = 30)
    private String authorizedOfficialLastName;

    /**
     */
    @Size(max = 30)
    private String authorizedOfficialFirstName;

    /**
     */
    @Size(max = 30)
    private String authorizedOfficialTitle;

    /**
     */
    @Size(max = 30)
    private String authorizedOfficialNamePrefix;

    /**
     */
    @Size(max = 30)
    private String authorizedOfficialTelephoneNumber;
    
    private boolean asi = false;
    
    private boolean gpra = false;    
    
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


	private static final long serialVersionUID = 1L;

	public String getOtherOrgName() {
        return this.otherOrgName;
    }

	public void setOtherOrgName(String otherOrgName) {
        this.otherOrgName = otherOrgName;
    }

	public String getAuthorizedOfficialLastName() {
        return this.authorizedOfficialLastName;
    }

	public void setAuthorizedOfficialLastName(String authorizedOfficialLastName) {
        this.authorizedOfficialLastName = authorizedOfficialLastName;
    }

	public String getAuthorizedOfficialFirstName() {
        return this.authorizedOfficialFirstName;
    }

	public void setAuthorizedOfficialFirstName(String authorizedOfficialFirstName) {
        this.authorizedOfficialFirstName = authorizedOfficialFirstName;
    }

	public String getAuthorizedOfficialTitle() {
        return this.authorizedOfficialTitle;
    }

	public void setAuthorizedOfficialTitle(String authorizedOfficialTitle) {
        this.authorizedOfficialTitle = authorizedOfficialTitle;
    }

	public String getAuthorizedOfficialNamePrefix() {
        return this.authorizedOfficialNamePrefix;
    }

	public void setAuthorizedOfficialNamePrefix(String authorizedOfficialNamePrefix) {
        this.authorizedOfficialNamePrefix = authorizedOfficialNamePrefix;
    }

	public String getAuthorizedOfficialTelephoneNumber() {
        return this.authorizedOfficialTelephoneNumber;
    }

	public void setAuthorizedOfficialTelephoneNumber(String authorizedOfficialTelephoneNumber) {
        this.authorizedOfficialTelephoneNumber = authorizedOfficialTelephoneNumber;
    }

	public boolean isAsi() {
		return asi;
	}

	public void setAsi(boolean asi) {
		this.asi = asi;
	}

	public boolean isGpra() {
		return gpra;
	}

	public void setGpra(boolean gpra) {
		this.gpra = gpra;
	}
	
	

	
}
