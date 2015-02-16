package com.feisystems.bham.domain;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class GpraIntegrationTest extends Base{
	
	@Autowired
	GpraRepository repo;
	
	@Test
	public void save() {
		Gpra gpra = getNewGpra();
		repo.save(gpra);
		
		Assert.assertNotNull("Expected Gpra identifider to be no longer null", gpra.getId());
	}
	
	@Test
	public void findByRequestIdAndStaffId() {
		Gpra gpra = repo.findAll().get(0);
		PageRequest pageRequest = new PageRequest(0, 2, Sort.Direction.DESC, "timeStamp");
		List<Gpra> pagedList = repo.findByRequestIdAndStaffId(gpra.getRequestId(), gpra.getStaffId(), pageRequest).getContent();
		
		Assert.assertTrue("Expected pagedlist size to be greater than zero", pagedList.size() > 0);
	}
	
	private Gpra getNewGpra() {
		Gpra gpra = new Gpra();
		gpra.setStaffId("222bbb");
		gpra.setRequestId("gpra001");
		gpra.setTimeStamp(new Date());
		
		return gpra;
	}

}
