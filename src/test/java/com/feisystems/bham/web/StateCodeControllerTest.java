package com.feisystems.bham.web;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.feisystems.bham.domain.PatientTestContext;
import com.feisystems.bham.service.LookupDto;
import com.feisystems.bham.service.reference.StateCodeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PatientTestContext.class})
@WebAppConfiguration
public class StateCodeControllerTest {

    @Autowired
	StateCodeService stateCodeServiceMock;

	//Mocking only two states
	LookupDto mockMaryland, mockCalifornia;
	
	private MockMvc mockMvc;
	
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {

    	Mockito.reset(stateCodeServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMaryland = new LookupDtoBuilder()
    						.displayName("Maryland")
							.code("MD")
    						.build();

        mockCalifornia = new LookupDtoBuilder()
							.displayName("California")
							.code("CA")
							.build();
    }
 
	@Test
    public void testFindAll_ShouldReturnFoundStateEntries() throws Exception {
		
		// Just return 2 states for testing purpose
        when(stateCodeServiceMock.findAllStateCodes()).thenReturn(Arrays.asList(mockMaryland, mockCalifornia));

		mockMvc.perform(get("/statecodes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code", is("MD")))
				.andExpect(jsonPath("$[0].displayName", is("Maryland")))
                .andExpect(jsonPath("$[1].code", is("CA")))
				.andExpect(jsonPath("$[1].displayName", is("California")));
                
        verify(stateCodeServiceMock, times(1)).findAllStateCodes();
        verifyNoMoreInteractions(stateCodeServiceMock);
        
    }
	
}
