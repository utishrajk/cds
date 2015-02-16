package com.feisystems.bham.domain;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;

public class IndividualProviderIntegrationTest extends Base {

	@Autowired
	IndividualProviderDataOnDemand dataOnDemand;

	@Autowired
	IndividualProviderRepository repository;
	
	final static String DUPLICATE_EMAIL = "xxx@mailinator.com";

	@Test
	public void findAll() {
		List<IndividualProvider> result = repository.findAll();
		Assert.assertNotNull("Find all method for 'IndividualProvider' illegally returned null", result);
	}

	@Test
	public void add() {
		IndividualProvider individualProvider = dataOnDemand.getNewIndividualProvider();

		System.out.println(individualProvider);
		repository.save(individualProvider);
		repository.flush();

		Assert.assertNotNull("Expected 'IndividualProvider' identifider to be no longer null", individualProvider.getId());
	}

	@Test(expected = JpaSystemException.class)
	public void addDuplicate() {
		IndividualProvider individualProvider = dataOnDemand.getNewIndividualProvider();

		individualProvider.setEmail(DUPLICATE_EMAIL);

		repository.save(individualProvider);
		repository.flush();

		IndividualProvider individualProvider2 = dataOnDemand.getNewIndividualProvider();

		individualProvider2.setEmail(DUPLICATE_EMAIL);

		repository.save(individualProvider2);
		repository.flush();

		Assert.assertEquals("Second object is not persisteed", individualProvider2.getId(), null);
	}

	@Test
	public void update() {
		IndividualProvider individualProvider = dataOnDemand.getRandomIndividualProvider();

		Integer currentVersion = individualProvider.getVersion();
		individualProvider.setAnswer1("kkkk");
		individualProvider = repository.save(individualProvider);
		repository.flush();

		Assert.assertTrue("Version of IndividualProver failed to increment", individualProvider.getVersion() > currentVersion);
	}

	@Test
	public void findOne() {
		IndividualProvider individualProvider = dataOnDemand.getRandomIndividualProvider();

		Long id = individualProvider.getId();
		individualProvider = repository.findOne(individualProvider.getId());

		Assert.assertTrue("Individual Provider has a different id", individualProvider.getId() == id);
	}

	@Test
	public void findByUsername() {
		IndividualProvider individualProvider = dataOnDemand.getRandomIndividualProvider();

		String username = individualProvider.getUserName();
		individualProvider = repository.findByUserName(username);

		Assert.assertEquals("Individual Provider has a different username", individualProvider.getUserName(), username);
	}

	@Test
	public void findByUsernameAndDateOfBirth() {
		IndividualProvider individualProvider = dataOnDemand.getRandomIndividualProvider();

		Long id = individualProvider.getId();
		String username = individualProvider.getUserName();
		Date dateOfBirth = individualProvider.getDateOfBirth();
		individualProvider = repository.findByUserNameAndDateOfBirth(username, dateOfBirth);

		Assert.assertTrue("Individual Provider has a different id", individualProvider.getId() == id);
	}

}
