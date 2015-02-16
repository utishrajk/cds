package com.feisystems.bham.domain.reference;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.feisystems.bham.domain.Base;
import com.feisystems.bham.service.reference.AdministrativeGenderCodeService;

public class AdministrativeGenderCodeIntegrationTest extends Base {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    AdministrativeGenderCodeDataOnDemand dod;

	@Autowired
    AdministrativeGenderCodeService administrativeGenderCodeService;

	@Autowired
    AdministrativeGenderCodeRepository administrativeGenderCodeRepository;

	@Test
    public void testFindAllAdministrativeGenderCodes() {
        Assert.assertNotNull("Data on demand for 'AdministrativeGenderCode' failed to initialize correctly", dod.getRandomAdministrativeGenderCode());
        List<AdministrativeGenderCode> result = administrativeGenderCodeService.findAllAdministrativeGenderCodes();
        Assert.assertNotNull("Find all method for 'AdministrativeGenderCode' illegally returned null", result);
        Assert.assertTrue("Find all method for 'AdministrativeGenderCode' failed to return any data", result.size() > 0);
    }

}
