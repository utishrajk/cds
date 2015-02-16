package com.feisystems.bham.web;
//package com.feisystems.bham.domain.controller;
//
//import com.feisystems.bham.domain.patient.PatientTestContext;
//import com.feisystems.bham.service.dto.LookupDto;
//import com.feisystems.bham.service.reference.PrefixCodeService;
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {PatientTestContext.class})
//@WebAppConfiguration
//public class PrefixCodeControllerTest {
//
//    @Autowired
//	PrefixCodeService prefixCodeServiceMock;
//
//	LookupDto mockPrefixCode1, mockPrefixCode2;
//	
//	private MockMvc mockMvc;
//	
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Before
//    public void setUp() {
//
//    	Mockito.reset(prefixCodeServiceMock);
//
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//
//        mockPrefixCode1 = new LookupDtoBuilder()
//        						.displayName("Mr.")
//    							.code("1")
//        						.build();
//
//        mockPrefixCode2 = new LookupDtoBuilder()
//								.displayName("Mrs.")
//								.code("2")
//								.build();
//
//    }
// 
//	@Test
//    public void testFindAll_ShouldReturnFoundPrefixEntries() throws Exception {
//		
//        when(prefixCodeServiceMock.findAllPrefixCodes()).thenReturn(Arrays.asList(mockPrefixCode1, mockPrefixCode2));
//
//		mockMvc.perform(get("/prefixes"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].code", is("1")))
//				.andExpect(jsonPath("$[0].displayName", is("Mr.")))
//                .andExpect(jsonPath("$[1].code", is("2")))
//				.andExpect(jsonPath("$[1].displayName", is("Mrs.")));
//                
//        verify(prefixCodeServiceMock, times(1)).findAllPrefixCodes();
//        verifyNoMoreInteractions(prefixCodeServiceMock);
//        
//    }
//	
//}
