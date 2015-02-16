package com.feisystems.bham.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.feisystems.bham.domain.DataElementsTestContext;
import com.feisystems.bham.domain.IndividualProvider;
import com.feisystems.bham.domain.IndividualProviderRepository;
import com.feisystems.bham.service.AsiDto;
import com.feisystems.bham.service.AsiFlagDto;
import com.feisystems.bham.service.DataElementsService;
import com.feisystems.bham.service.GpraDto;
import com.feisystems.bham.service.GpraFlagDto;
import com.feisystems.bham.service.UsernameService;
import com.feisystems.bham.service.recommendation.RecommendationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataElementsTestContext.class})
@WebAppConfiguration
public class DataElementsControllerTest {
	
	private static final String REQUEST_ID = "requestId";

	private static final String USERNAME = "username";

	private static final String STAFF_ID = "staffId";

	@Autowired
	DataElementsService dataElementsService;
	
	@Autowired
	RecommendationService recommendationService;
	
	@Autowired
	IndividualProviderRepository individualProviderRepository;
	
	@Autowired
	UsernameService usernameService;
	
	MockMvc mockMvc;
	
	@Resource
	private WebApplicationContext context;
	
	IndividualProvider user;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		
		user = new IndividualProvider();
		user.setStaffId(STAFF_ID);
		user.setUserName(USERNAME);

		when(usernameService.retrieveUsername()).thenReturn(USERNAME);
		when(individualProviderRepository.findByUserName(USERNAME)).thenReturn(user);
	}
	
	@Test
	public void testFindGpraDto() throws Exception {
		when(dataElementsService.findGpraDto(REQUEST_ID, STAFF_ID)).thenReturn(new GpraDto());
		mockMvc.perform(get("/dataelements/requestId")).andExpect(status().isOk());
	}
	
	@Test
	public void testFindAsiDto() throws Exception {
		when(dataElementsService.findGpraDto(REQUEST_ID, STAFF_ID)).thenReturn(null);
		when(dataElementsService.findAsiDto(REQUEST_ID, STAFF_ID)).thenReturn(new AsiDto());
		mockMvc.perform(get("/dataelements/requestId")).andExpect(status().isOk());
	}
	
	@Test
	public void testFindGpraFlagDto() throws Exception {
		when(dataElementsService.findGpraFlagDto(REQUEST_ID, STAFF_ID)).thenReturn(new GpraFlagDto());
		mockMvc.perform(get("/dataelements/flag/requestId")).andExpect(status().isOk());
	}
	
	@Test
	public void testFindAsiFlagDto() throws Exception {
		when(dataElementsService.findGpraFlagDto(REQUEST_ID, STAFF_ID)).thenReturn(null);
		when(dataElementsService.findAsiFlagDto(REQUEST_ID, STAFF_ID)).thenReturn(new AsiFlagDto());
		mockMvc.perform(get("/dataelements/flag/requestId")).andExpect(status().isOk());
	}
	
	@Test
	public void testCreateGpra() throws Exception {
		GpraDto dto = new GpraDto();
		mockMvc.perform(put("/dataelements/gpra/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dto)))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void testCreateAsi() throws Exception {
		AsiDto dto = new AsiDto();
		mockMvc.perform(put("/dataelements/asi/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dto)))
				.andExpect(status().isCreated());
	}

}
