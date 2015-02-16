package com.feisystems.bham.domain.reference;
import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.feisystems.bham.domain.Base;
import com.feisystems.bham.service.reference.CountryCodeService;

public class CountryCodeIntegrationTest extends Base {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    CountryCodeDataOnDemand dod;

	@Autowired
    CountryCodeService countryCodeService;

	@Autowired
    CountryCodeRepository countryCodeRepository;

	@Test
    public void testCountAllCountryCodes() {
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to initialize correctly", dod.getRandomCountryCode());
        long count = countryCodeService.countAllCountryCodes();
        Assert.assertTrue("Counter for 'CountryCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindCountryCode() {
        CountryCode obj = dod.getRandomCountryCode();
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to provide an identifier", id);
        obj = countryCodeService.findCountryCode(id);
        Assert.assertNotNull("Find method for 'CountryCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'CountryCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllCountryCodes() {
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to initialize correctly", dod.getRandomCountryCode());
        long count = countryCodeService.countAllCountryCodes();
        Assert.assertTrue("Too expensive to perform a find all test for 'CountryCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<CountryCode> result = countryCodeService.findAllCountryCodes();
        Assert.assertNotNull("Find all method for 'CountryCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'CountryCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindCountryCodeEntries() {
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to initialize correctly", dod.getRandomCountryCode());
        long count = countryCodeService.countAllCountryCodes();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<CountryCode> result = countryCodeService.findCountryCodeEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'CountryCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'CountryCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        CountryCode obj = dod.getRandomCountryCode();
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to provide an identifier", id);
        obj = countryCodeService.findCountryCode(id);
        Assert.assertNotNull("Find method for 'CountryCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyCountryCode(obj);
        Integer currentVersion = obj.getVersion();
        countryCodeRepository.flush();
        Assert.assertTrue("Version for 'CountryCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateCountryCodeUpdate() {
        CountryCode obj = dod.getRandomCountryCode();
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to provide an identifier", id);
        obj = countryCodeService.findCountryCode(id);
        boolean modified =  dod.modifyCountryCode(obj);
        Integer currentVersion = obj.getVersion();
        CountryCode merged = (CountryCode)countryCodeService.updateCountryCode(obj);
        countryCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'CountryCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveCountryCode() {
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to initialize correctly", dod.getRandomCountryCode());
        CountryCode obj = dod.getNewTransientCountryCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'CountryCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'CountryCode' identifier to be null", obj.getId());
        try {
            countryCodeService.saveCountryCode(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        countryCodeRepository.flush();
        Assert.assertNotNull("Expected 'CountryCode' identifier to no longer be null", obj.getId());
    }

}
