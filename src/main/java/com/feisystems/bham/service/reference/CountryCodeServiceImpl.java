package com.feisystems.bham.service.reference;

import com.feisystems.bham.domain.reference.CountryCode;
import com.feisystems.bham.domain.reference.CountryCodeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CountryCodeServiceImpl implements CountryCodeService {

	@Autowired
    CountryCodeRepository countryCodeRepository;

	public long countAllCountryCodes() {
        return countryCodeRepository.count();
    }

	public void deleteCountryCode(CountryCode countryCode) {
        countryCodeRepository.delete(countryCode);
    }

	public CountryCode findCountryCode(Long id) {
        return countryCodeRepository.findOne(id);
    }

	public List<CountryCode> findAllCountryCodes() {
        return countryCodeRepository.findAll();
    }

	public List<CountryCode> findCountryCodeEntries(int firstResult, int maxResults) {
        return countryCodeRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveCountryCode(CountryCode countryCode) {
        countryCodeRepository.save(countryCode);
    }

	public CountryCode updateCountryCode(CountryCode countryCode) {
        return countryCodeRepository.save(countryCode);
    }
}
