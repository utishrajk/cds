package com.feisystems.bham.service;

/**
 * The Class LookupDto.
 */
public class LookupDto {

	/** The code. */
	private String code;
	
	/** The code system. */
	private String codeSystem;
	
	/** The display name. */
	private String displayName;
	
	/** The code system name. */
	private String codeSystemName;
	
	/** The original text. */
	private String originalText;

	/**
	 * Gets the code.
	 * @return the code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Sets the code.
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the code system.
	 * @return the code system
	 */
	public String getCodeSystem() {
		return this.codeSystem;
	}

	/**
	 * Sets the code system.
	 * @param codeSystem the new code system
	 */
	public void setCodeSystem(String codeSystem) {
		this.codeSystem = codeSystem;
	}

	/**
	 * Gets the display name.
	 * @return the display name
	 */
	public String getDisplayName() {
		return this.displayName;
	}

	/**
	 * Sets the display name.
	 * @param displayName the new display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Gets the code system name.
	 * @return the code system name
	 */
	public String getCodeSystemName() {
		return this.codeSystemName;
	}

	/**
	 * Sets the code system name.
	 * @param codeSystemName the new code system name
	 */
	public void setCodeSystemName(String codeSystemName) {
		this.codeSystemName = codeSystemName;
	}

	/**
	 * Gets the original text.
	 * @return the original text
	 */
	public String getOriginalText() {
		return this.originalText;
	}

	/**
	 * Sets the original text.
	 * @param originalText the new original text
	 */
	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}
}
