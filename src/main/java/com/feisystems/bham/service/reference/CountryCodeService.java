package com.feisystems.bham.service.reference;
import com.feisystems.bham.domain.reference.CountryCode;
import java.util.List;

public interface CountryCodeService {

	public abstract long countAllCountryCodes();


	public abstract void deleteCountryCode(CountryCode countryCode);


	public abstract CountryCode findCountryCode(Long id);


	public abstract List<CountryCode> findAllCountryCodes();


	public abstract List<CountryCode> findCountryCodeEntries(int firstResult, int maxResults);


	public abstract void saveCountryCode(CountryCode countryCode);


	public abstract CountryCode updateCountryCode(CountryCode countryCode);

}
