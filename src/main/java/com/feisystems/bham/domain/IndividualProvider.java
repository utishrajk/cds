package com.feisystems.bham.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.feisystems.bham.domain.reference.AdministrativeGenderCode;
import com.feisystems.bham.domain.reference.SecurityQuestionsCode;

@Entity
public class IndividualProvider extends AbstractProvider implements Serializable {

	@Size(min = 1, max = 50)
	@Column(nullable = false)
	private String staffId;

	@Size(min = 1, max = 50)
	@Column(unique = true, nullable = false)
	private String userName;

	@Column(nullable = false)
	@Size(min = 1, max = 50)
	private String lastName;

	@Column(nullable = false)
	@Size(min = 1, max = 50)
	private String firstName;

	@Size(max = 30)
	private String middleName;

	@Size(max = 30)
	private String namePrefixCode;

	@Size(max = 30)
	private String nameSuffix;

	private Boolean showTrainingVideo = false;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date dateOfBirth;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_AUTHORITY", joinColumns = { @JoinColumn(name = "id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "name", referencedColumnName = "name") })
	private Set<Authority> authorities;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	private Set<VerificationToken> verificationTokens = new HashSet<VerificationToken>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	@Sort(type = SortType.COMPARATOR, comparator = Credential.CredentialComparator.class)
	private SortedSet<Credential> credentials = new TreeSet<>();

	@ManyToOne
	@JoinColumn(nullable = false)
	private SecurityQuestionsCode question1;

	@Column(nullable = false)
	@Size(min = 1)
	private String answer1;

	@ManyToOne
	@JoinColumn(nullable = false)
	private SecurityQuestionsCode question2;

	@Column(nullable = false)
	@Size(min = 1)
	private String answer2;

	private boolean isVerified;

	@ManyToOne
	private AdministrativeGenderCode administrativeGenderCode;

	private boolean accountNonLocked;

	@Inject
	private static PasswordEncoder passwordEncoder;

	private boolean acceptTermsOfUse;

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getNamePrefixCode() {
		return this.namePrefixCode;
	}

	public void setNamePrefixCode(String namePrefix) {
		this.namePrefixCode = namePrefix;
	}

	public String getNameSuffix() {
		return this.nameSuffix;
	}

	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}

	public String getCredential() {
		return getCredentials().last().getPassword();
	}

	public AdministrativeGenderCode getAdministrativeGenderCode() {
		return this.administrativeGenderCode;
	}

	public void setAdministrativeGenderCode(AdministrativeGenderCode administrativeGenderCode) {
		this.administrativeGenderCode = administrativeGenderCode;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	private static final long serialVersionUID = 1L;

	public void addVerificationToken(VerificationToken token) {
		verificationTokens.add(token);
	}

	public Set<VerificationToken> getVerificationTokens() {
		return this.verificationTokens;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public SecurityQuestionsCode getQuestion1() {
		return question1;
	}

	public void setQuestion1(SecurityQuestionsCode question1) {
		this.question1 = question1;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public SecurityQuestionsCode getQuestion2() {
		return question2;
	}

	public void setQuestion2(SecurityQuestionsCode question2) {
		this.question2 = question2;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public VerificationToken getActiveToken(VerificationToken.Type tokenType) {
		VerificationToken activeToken = null;
		for (VerificationToken token : getVerificationTokens()) {
			if (token.getTokenType().equals(tokenType) && !token.hasExpired() && !token.isVerified()) {
				activeToken = token;
				break;
			}
		}
		return activeToken;
	}

	public SortedSet<Credential> getCredentials() {
		return credentials;
	}

	public void addCredential(Credential credential) {
		this.credentials.add(credential);
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setShowTrainingVideo(Boolean showTrainingVideo) {
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

	@Override
	public String toString() {
		return "IndividualProvider [userName=" + userName + ", lastName=" + lastName + ", firstName=" + firstName + ", middleName=" + middleName
				+ ", namePrefixCode=" + namePrefixCode
				+ ", nameSuffix=" + nameSuffix + ", authorities=" + authorities + ", administrativeGenderCode=" + administrativeGenderCode + "]";
	}

}
