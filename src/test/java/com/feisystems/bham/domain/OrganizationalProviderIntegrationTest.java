package com.feisystems.bham.domain;
import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.feisystems.bham.service.OrganizationalProviderDto;
import com.feisystems.bham.service.OrganizationalProviderService;

public class OrganizationalProviderIntegrationTest extends Base {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    OrganizationalProviderDataOnDemand dod;

	@Autowired
    OrganizationalProviderService organizationalProviderService;

	@Autowired
    OrganizationalProviderRepository organizationalProviderRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Test
    public void testCountAllOrganizationalProviders() {
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to initialize correctly", dod.getRandomOrganizationalProvider());
        long count = organizationalProviderService.countAllOrganizationalProviders();
        Assert.assertTrue("Counter for 'OrganizationalProvider' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindOrganizationalProvider() {
        OrganizationalProvider obj = dod.getRandomOrganizationalProvider();
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to provide an identifier", id);
        OrganizationalProviderDto objDto = organizationalProviderService.findOrganizationalProvider();
        Assert.assertNotNull("Find method for 'OrganizationalProvider' illegally returned null for id '" + id + "'", objDto);
        Assert.assertEquals("Find method for 'OrganizationalProvider' returned the incorrect identifier", id, objDto.getId());
    }

	@Test
    public void testFindAllOrganizationalProviders() {
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to initialize correctly", dod.getRandomOrganizationalProvider());
        long count = organizationalProviderService.countAllOrganizationalProviders();
        Assert.assertTrue("Too expensive to perform a find all test for 'OrganizationalProvider', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<OrganizationalProviderDto> result = organizationalProviderService.findAllOrganizationalProviders();
        Assert.assertNotNull("Find all method for 'OrganizationalProvider' illegally returned null", result);
        Assert.assertTrue("Find all method for 'OrganizationalProvider' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindOrganizationalProviderEntries() {
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to initialize correctly", dod.getRandomOrganizationalProvider());
        long count = organizationalProviderService.countAllOrganizationalProviders();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<OrganizationalProvider> result = organizationalProviderService.findOrganizationalProviderEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'OrganizationalProvider' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'OrganizationalProvider' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        OrganizationalProvider obj = dod.getRandomOrganizationalProvider();
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to provide an identifier", id);
        OrganizationalProviderDto objDto = organizationalProviderService.findOrganizationalProvider();
        Assert.assertNotNull("Find method for 'OrganizationalProvider' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyOrganizationalProvider(obj);
        Integer currentVersion = obj.getVersion();
        organizationalProviderRepository.flush();
        Assert.assertTrue("Version for 'OrganizationalProvider' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateOrganizationalProviderUpdate() {
        OrganizationalProvider obj = dod.getRandomOrganizationalProvider();
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to provide an identifier", id);
        OrganizationalProviderDto objDto = organizationalProviderService.findOrganizationalProvider();
        boolean modified =  dod.modifyOrganizationalProvider(obj);
        Integer currentVersion = obj.getVersion();
        OrganizationalProvider merged = (OrganizationalProvider)organizationalProviderService.updateOrganizationalProvider(objDto);
        organizationalProviderRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'OrganizationalProvider' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	
    public void testSaveOrganizationalProvider() {
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to initialize correctly", dod.getRandomOrganizationalProvider());
        OrganizationalProvider obj = dod.getNewTransientOrganizationalProvider(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'OrganizationalProvider' identifier to be null", obj.getId());
        try {
        	OrganizationalProviderDto organizationalProviderDto = modelMapper.map(obj, OrganizationalProviderDto.class);
            organizationalProviderService.saveOrganizationalProvider(organizationalProviderDto);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        organizationalProviderRepository.flush();
        Assert.assertNotNull("Expected 'OrganizationalProvider' identifier to no longer be null", obj.getId());
    }

	
    public void testDeleteOrganizationalProvider() {
        OrganizationalProvider obj = dod.getRandomOrganizationalProvider();
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'OrganizationalProvider' failed to provide an identifier", id);
        OrganizationalProviderDto objDto = organizationalProviderService.findOrganizationalProvider();
        organizationalProviderService.deleteOrganizationalProvider(obj);
        organizationalProviderRepository.flush();
        Assert.assertNull("Failed to remove 'OrganizationalProvider' with identifier '" + id + "'", organizationalProviderService.findOrganizationalProvider());
    }
}
