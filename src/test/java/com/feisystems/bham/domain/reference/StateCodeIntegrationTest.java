package com.feisystems.bham.domain.reference;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.feisystems.bham.domain.Base;
import com.feisystems.bham.service.reference.StateCodeService;

public class StateCodeIntegrationTest extends Base {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    StateCodeDataOnDemand dod;

	@Autowired
    StateCodeService stateCodeService;

	@Autowired
    StateCodeRepository stateCodeRepository;

	@Test
    public void testFindAllStateCodes() {
        Assert.assertNotNull("Data on demand for 'StateCode' failed to initialize correctly", dod.getRandomStateCode());
        long count = stateCodeRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'StateCode', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<StateCode> result = stateCodeRepository.findAll();
        Assert.assertNotNull("Find all method for 'StateCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'StateCode' failed to return any data", result.size() > 0);
    }

}
