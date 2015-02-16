package com.feisystems.bham.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.feisystems.bham.domain.reference.ProviderEntityType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class AbstractProvider {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	/**
     */
    @Size(min = 1, max = 30)
    @NotNull
    private String npi;
    
    /**
     */
    
    @Size(max = 30)
    private String orgName;

	/**
     */
    @Enumerated
    private ProviderEntityType providerEntityType;

    /**
     */
    @NotNull
    @Size(max = 30)
    private String firstLineMailingAddress;

    /**
     */
    @Size(max = 30)
    private String secondLineMailingAddress;

    /**
     */
    @Size(max = 30)
    @NotNull
    private String mailingAddressCityName;

    /**
     */
    @Size(max = 30)
    private String mailingAddressStateName;
  
    /**
     */
    @NotNull
    @Size(max = 30)
    private String mailingAddressPostalCode;

    /**
     */
    @NotNull
    @Size(max = 30)
    private String mailingAddressCountryCode;

    /**
     */
    @Size(max = 30)
    private String mailingAddressTelephoneNumber;

    /**
     */
    @Size(max = 30)
    private String mailingAddressFaxNumber;

    /**
     */
    @Size(max = 30)
    private String firstLinePracticeLocationAddress;

    /**
     */
    @Size(max = 30)
    private String secondLinePracticeLocationAddress;

    /**
     */
    @Size(max = 30)
    private String practiceLocationAddressCityName;

    /**
     */
    @Size(max = 30)
    private String practiceLocationAddressStateName;

    /**
     */
    @Size(max = 30)
    private String practiceLocationAddressPostalCode;

    /**
     */
    @Size(max = 30)
    private String practiceLocationAddressCountryCode;
    
    
    @Pattern(regexp = "[a-zA-Z0-9.]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+")
    @Column(unique=true)
    private String email;
    

    @Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
    private String website;
    
	/**
     */
    @Size(max = 30)
    private String practiceLocationAddressTelephoneNumber;

    /**
     */
    @Size(max = 30)
    private String practiceLocationAddressFaxNumber;

    /**
     */
    @Size(max = 30)
    private String enumerationDate;

    /**
     */
    @Size(max = 30)
    private String lastUpdateDate;
    
    @Size(max = 30)
	private String nonUSState;
    
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }
	
	public String getOrgName() {
        return this.orgName;
    }

	public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }
	
    public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getNpi() {
        return this.npi;
    }

	public void setNpi(String npi) {
        this.npi = npi;
    }

	public ProviderEntityType getProviderEntityType() {
        return this.providerEntityType;
    }

	public void setProviderEntityType(ProviderEntityType providerEntityType) {
        this.providerEntityType = providerEntityType;
    }

	public String getFirstLineMailingAddress() {
        return this.firstLineMailingAddress;
    }

	public void setFirstLineMailingAddress(String firstLineMailingAddress) {
        this.firstLineMailingAddress = firstLineMailingAddress;
    }

	public String getSecondLineMailingAddress() {
        return this.secondLineMailingAddress;
    }

	public void setSecondLineMailingAddress(String secondLineMailingAddress) {
        this.secondLineMailingAddress = secondLineMailingAddress;
    }

	public String getMailingAddressCityName() {
        return this.mailingAddressCityName;
    }

	public void setMailingAddressCityName(String mailingAddressCityName) {
        this.mailingAddressCityName = mailingAddressCityName;
    }

	public String getMailingAddressStateName() {
        return this.mailingAddressStateName;
    }

	public void setMailingAddressStateName(String mailingAddressStateName) {
        this.mailingAddressStateName = mailingAddressStateName;	
    }

	public String getMailingAddressPostalCode() {
        return this.mailingAddressPostalCode;
    }

	public void setMailingAddressPostalCode(String mailingAddressPostalCode) {
        this.mailingAddressPostalCode = mailingAddressPostalCode;
    }

	public String getMailingAddressCountryCode() {
        return this.mailingAddressCountryCode;
    }

	public void setMailingAddressCountryCode(String mailingAddressCountryCode) {
        this.mailingAddressCountryCode = mailingAddressCountryCode;
    }

	public String getMailingAddressTelephoneNumber() {
        return this.mailingAddressTelephoneNumber;
    }

	public void setMailingAddressTelephoneNumber(String mailingAddressTelephoneNumber) {
        this.mailingAddressTelephoneNumber = mailingAddressTelephoneNumber;
    }

	public String getMailingAddressFaxNumber() {
        return this.mailingAddressFaxNumber;
    }

	public void setMailingAddressFaxNumber(String mailingAddressFaxNumber) {
        this.mailingAddressFaxNumber = mailingAddressFaxNumber;
    }

	public String getFirstLinePracticeLocationAddress() {
        return this.firstLinePracticeLocationAddress;
    }

	public void setFirstLinePracticeLocationAddress(String firstLinePracticeLocationAddress) {
        this.firstLinePracticeLocationAddress = firstLinePracticeLocationAddress;
    }

	public String getSecondLinePracticeLocationAddress() {
        return this.secondLinePracticeLocationAddress;
    }

	public void setSecondLinePracticeLocationAddress(String secondLinePracticeLocationAddress) {
        this.secondLinePracticeLocationAddress = secondLinePracticeLocationAddress;
    }

	public String getPracticeLocationAddressCityName() {
        return this.practiceLocationAddressCityName;
    }

	public void setPracticeLocationAddressCityName(String practiceLocationAddressCityName) {
        this.practiceLocationAddressCityName = practiceLocationAddressCityName;
    }

	public String getPracticeLocationAddressStateName() {
        return this.practiceLocationAddressStateName;
    }

	public void setPracticeLocationAddressStateName(String practiceLocationAddressStateName) {
        this.practiceLocationAddressStateName = practiceLocationAddressStateName;
    }

	public String getPracticeLocationAddressPostalCode() {
        return this.practiceLocationAddressPostalCode;
    }

	public void setPracticeLocationAddressPostalCode(String practiceLocationAddressPostalCode) {
        this.practiceLocationAddressPostalCode = practiceLocationAddressPostalCode;
    }

	public String getPracticeLocationAddressCountryCode() {
        return this.practiceLocationAddressCountryCode;
    }

	public void setPracticeLocationAddressCountryCode(String practiceLocationAddressCountryCode) {
        this.practiceLocationAddressCountryCode = practiceLocationAddressCountryCode;
    }

	public String getPracticeLocationAddressTelephoneNumber() {
        return this.practiceLocationAddressTelephoneNumber;
    }

	public void setPracticeLocationAddressTelephoneNumber(String practiceLocationAddressTelephoneNumber) {
        this.practiceLocationAddressTelephoneNumber = practiceLocationAddressTelephoneNumber;
    }

	public String getPracticeLocationAddressFaxNumber() {
        return this.practiceLocationAddressFaxNumber;
    }

	public void setPracticeLocationAddressFaxNumber(String practiceLocationAddressFaxNumber) {
        this.practiceLocationAddressFaxNumber = practiceLocationAddressFaxNumber;
    }

	public String getEnumerationDate() {
        return this.enumerationDate;
    }

	public void setEnumerationDate(String enumerationDate) {
        this.enumerationDate = enumerationDate;
    }

	public String getLastUpdateDate() {
        return this.lastUpdateDate;
    }

	public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((npi == null) ? 0 : npi.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractProvider other = (AbstractProvider) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (npi == null) {
			if (other.npi != null)
				return false;
		} else if (!npi.equals(other.npi))
			return false;
		return true;
	}

	public String getNonUSState() {
		return nonUSState;
	}

	public void setNonUSState(String nonUSState) {
		this.nonUSState = nonUSState;
	}
	

}
