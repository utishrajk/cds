package com.feisystems.bham.domain.reference;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import com.feisystems.bham.domain.Base;

public class PrefixCodeIntegrationTest extends Base {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    PrefixCodeDataOnDemand dod;

	@Autowired
    PrefixCodeRepository prefixCodeRepository;

	@Test
    public void testCountAllPrefixCodes() {
        Assert.assertNotNull("Data on demand for 'PrefixCode' failed to initialize correctly", dod.getRandomPrefixCode());
        long count = prefixCodeRepository.count();
        Assert.assertTrue("Counter for 'PrefixCode' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindRaceCode() {
        PrefixCode obj = dod.getRandomPrefixCode();
        Assert.assertNotNull("Data on demand for 'PrefixCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PrefixCode' failed to provide an identifier", id);
        obj = prefixCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'RaceCode' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'RaceCode' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllPrefixCodes() {
        Assert.assertNotNull("Data on demand for 'PrefixCode' failed to initialize correctly", dod.getRandomPrefixCode());
        long count = prefixCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'PrefixCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<PrefixCode> result = prefixCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'PrefixCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'PrefixCode' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindRaceCodeEntries() {
        Assert.assertNotNull("Data on demand for 'PrefixCode' failed to initialize correctly", dod.getRandomPrefixCode());
        long count = prefixCodeRepository.count();
        if (count > 20) count = 20;
		int pageNumber = 0;
        int pageSize = 10;
        List<PrefixCode> result = prefixCodeRepository.findAll(
				new PageRequest(pageNumber, pageSize)).getContent();
        Assert.assertNotNull("Find entries method for 'PrefixCode' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'PrefixCode' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
		PrefixCode obj = dod.getRandomPrefixCode();
        Assert.assertNotNull("Data on demand for 'PrefixCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PrefixCode' failed to provide an identifier", id);
        obj = prefixCodeRepository.findOne(id);
        Assert.assertNotNull("Find method for 'PrefixCode' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod .modifyPrefixCode(obj);
        Integer currentVersion = obj.getVersion();
        prefixCodeRepository.flush();
        Assert.assertTrue("Version for 'RaceCode' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateRaceCodeUpdate() {
		PrefixCode obj = dod.getRandomPrefixCode();
        Assert.assertNotNull("Data on demand for 'PrefixCode' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PrefixCode' failed to provide an identifier", id);
        obj = prefixCodeRepository.findOne(id);
        boolean modified =  dod.modifyPrefixCode(obj);
        Integer currentVersion = obj.getVersion();
        PrefixCode merged = (PrefixCode)prefixCodeRepository.save(obj);
        prefixCodeRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'PrefixCode' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveRaceCode() {
        Assert.assertNotNull("Data on demand for 'PrefixCode' failed to initialize correctly", dod.getRandomPrefixCode());
        PrefixCode obj = dod.getNewTransientPrefixCode(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'PrefixCode' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'PrefixCode' identifier to be null", obj.getId());
        prefixCodeRepository.save(obj);
        prefixCodeRepository.flush();
        Assert.assertNotNull("Expected 'PrefixCode' identifier to no longer be null", obj.getId());
    }

}
