package com.feisystems.bham.web;

import com.feisystems.bham.domain.reference.AbstractCodedConcept;

public class CodeDataBuilder<T extends AbstractCodedConcept> {
	  private T codeData;

	  public CodeDataBuilder(T codeData) {
	    this.codeData = codeData;
	  }

	  public CodeDataBuilder<T> setCode(String code) {
	    codeData.setCode(code);
	    return this;
	  }

	  public CodeDataBuilder<T> setDisplayName(String displayName) {
	    codeData.setDisplayName(displayName);
	    return this;
	  }

	  public T build() {
	    return codeData;
	  }
}