package com.feisystems.bham.service;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.feisystems.bham.util.Constant;

public class IndividualProviderDto extends AbstractProviderDto {

	private String staffId;

	private String userName;

	private String credential = "credential";

	private String confirmPassword;

	private String lastName;

	private String firstName;

	private String middleName;

	private String namePrefixCode;

	private String nameSuffix;

	private String administrativeGenderCode;

	private String providerTaxonomyCode;

	private String providerTaxonomyCodeDisplayName;

	private List<String> roles;

	private String securityAnswer1;

	private String securityAnswer2;

	private String securityQuestion1Code;

	private String securityQuestion2Code;

	private String token;

	private CaptchaDto captcha;

	private boolean showTrainingVideo;

	private boolean acceptTermsOfUse;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy", timezone = Constant.NEWYORK_TIMEZONE)
	private Date dateOfBirth;

	public IndividualProviderDto() {
		super();
	}

	public IndividualProviderDto(Long id, String userName, String firstName, String lastName, String email, List<String> roles) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.setId(id);
		this.setEmail(email);
		this.roles = roles;
	}

	public String getSecurityQuestion1Code() {
		return securityQuestion1Code;
	}

	public void setSecurityQuestion1Code(String securityQuestion1Code) {
		this.securityQuestion1Code = securityQuestion1Code;
	}

	public String getSecurityQuestion2Code() {
		return securityQuestion2Code;
	}

	public void setSecurityQuestion2Code(String securityQuestion2Code) {
		this.securityQuestion2Code = securityQuestion2Code;
	}

	public String getProviderTaxonomyCode() {
		return providerTaxonomyCode;
	}

	public void setProviderTaxonomyCode(String providerTaxonomyCode) {
		this.providerTaxonomyCode = providerTaxonomyCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getNamePrefixCode() {
		return namePrefixCode;
	}

	public void setNamePrefixCode(String namePrefixCode) {
		this.namePrefixCode = namePrefixCode;
	}

	public String getNameSuffix() {
		return nameSuffix;
	}

	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}

	public String getAdministrativeGenderCode() {
		return administrativeGenderCode;
	}

	public void setAdministrativeGenderCode(String administrativeGenderCode) {
		this.administrativeGenderCode = administrativeGenderCode;
	}

	public String getProviderTaxonomyCodeDisplayName() {
		return providerTaxonomyCodeDisplayName;
	}

	public void setProviderTaxonomyCodeDisplayName(String providerTaxonomyCodeDisplayName) {
		this.providerTaxonomyCodeDisplayName = providerTaxonomyCodeDisplayName;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getSecurityAnswer1() {
		return securityAnswer1;
	}

	public void setSecurityAnswer1(String securityAnswer1) {
		this.securityAnswer1 = securityAnswer1;
	}

	public String getSecurityAnswer2() {
		return securityAnswer2;
	}

	public void setSecurityAnswer2(String securityAnswer2) {
		this.securityAnswer2 = securityAnswer2;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public CaptchaDto getCaptcha() {
		return captcha;
	}

	public void setCaptcha(CaptchaDto captcha) {
		this.captcha = captcha;
	}

	public void setShowTrainingVideo(boolean showTrainingVideo) {
		this.showTrainingVideo = showTrainingVideo;
	}

	public Boolean getShowTrainingVideo() {
		return showTrainingVideo;
	}

	public boolean isAcceptTermsOfUse() {
		return acceptTermsOfUse;
	}

	public void setAcceptTermsOfUse(boolean acceptTermsOfUse) {
		this.acceptTermsOfUse = acceptTermsOfUse;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	@Override
	public String toString() {
		return "IndividualProviderDto [userName=" + userName + ", credential=" + credential + ", confirmPassword=" + confirmPassword + ", lastName="
				+ lastName + ", firstName=" + firstName
				+ ", middleName=" + middleName + ", namePrefixCode=" + namePrefixCode + ", nameSuffix=" + nameSuffix + ", administrativeGenderCode="
				+ administrativeGenderCode
				+ ", providerTaxonomyCode=" + providerTaxonomyCode + ", providerTaxonomyCodeDisplayName=" + providerTaxonomyCodeDisplayName
				+ ", roles=" + roles + ", securityAnswer1="
				+ securityAnswer1 + ", securityAnswer2=" + securityAnswer2 + ", securityQuestion1Code=" + securityQuestion1Code
				+ ", securityQuestion2Code=" + securityQuestion2Code + ", token="
				+ token + ", captcha=" + captcha + ", showTrainingVideo=" + showTrainingVideo + ", acceptTermsOfUse=" + acceptTermsOfUse
				+ ", dateOfBirth=" + dateOfBirth + "]";
	}

}
