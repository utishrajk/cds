package com.feisystems.bham.domain;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;



public class AsiIntegrationTest extends Base {
	
	@Autowired
	AsiRepository repo;
	
	@Test
	public void save() {
		Asi asi = getNewAsi();
		repo.save(asi);
		
		Assert.assertNotNull("Expected Asi identifier to be no longer null", asi.getId());
	}
	
	@Test
	public void findByRequestIdAndStaffId() {
		Asi asi = repo.findAll().get(0);
		PageRequest pageRequest = new PageRequest(0, 2, Sort.Direction.DESC, "timeStamp");
		List<Asi> pagedList = repo.findByRequestIdAndStaffId(asi.getRequestId(), asi.getStaffId(), pageRequest).getContent();
		
		Assert.assertTrue("Expected pagedlist size to be greater than zero", pagedList.size() > 0);
	}
	
	private Asi getNewAsi() {
		Asi asi = new Asi();
		asi.setStaffId("222bbb");
		asi.setRequestId("asi001");
		asi.setTimeStamp(new Date());
		return asi;
	}
	

}
