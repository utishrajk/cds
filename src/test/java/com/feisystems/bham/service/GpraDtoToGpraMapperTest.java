package com.feisystems.bham.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.feisystems.bham.domain.AuditRecommendations;
import com.feisystems.bham.domain.Gpra;
import com.feisystems.bham.domain.reference.AdministrativeGenderCode;
import com.feisystems.bham.domain.reference.AdministrativeGenderCodeRepository;
import com.feisystems.bham.domain.reference.EmotionalProblemsCode;
import com.feisystems.bham.domain.reference.EmotionalProblemsRepository;
import com.feisystems.bham.domain.reference.HealthIndicatorCode;
import com.feisystems.bham.domain.reference.HealthIndicatorRepository;
import com.feisystems.bham.domain.reference.RouteCode;
import com.feisystems.bham.domain.reference.RouteCodeRepository;


@RunWith(MockitoJUnitRunner.class)
public class GpraDtoToGpraMapperTest {
	
	@InjectMocks
	private GpraDtoToGpraMapper mapper;
	
	@Mock
	AdministrativeGenderCodeRepository genderCodeRepository;

	@Mock
	RouteCodeRepository routeCodeRepository;

	@Mock
	HealthIndicatorRepository healthIndicatorRepository;

	@Mock
	EmotionalProblemsRepository emotionalProblemsRepository;
	
	@Test
	public void testMap() {
		//Arrange
		GpraDto dto = new GpraDto();
		dto.setRequestId("requestId");
		dto.setStaffId("staffId");
		dto.setNote1("note1");
		dto.setNote2("note2");
		dto.setAge(22);
		dto.setGenderCode("FE");
		
		dto.setHeroinDayCount(1);
		dto.setHeroinRouteCode("1");
		
		dto.setMorphineCount(1);
		dto.setMorphineRouteCode("1");
		
		dto.setDiluadidCount(1);
		dto.setDiluadidRouteCode("1");
		
		dto.setDemerolCount(1);
		dto.setDemerolRouteCode("1");
		
		dto.setPercocetCount(1);
		dto.setPercocetRouteCode("1");
		
		dto.setDarvonCount(1);
		dto.setDarvonRouteCode("1");
		
		dto.setCodeineCount(1);
		dto.setCodeineRouteCode("1");
		
		dto.setTylenolCount(1);
		dto.setTylenolRouteCode("1");
		
		dto.setOxycontinCount(1);
		dto.setOxycontinRouteCode("1");
		
		dto.setMethadoneCount(1);
		dto.setMethadoneRouteCode("1");
		
		dto.setHealthIndicatorCode("1");
		
		dto.setDepressionCount(1);
		
		dto.setAnxietyCount(1);
		
		dto.setViolentCount(1);
		
		dto.setPsychMedicationCount(1);
		
		dto.setEmotionalProblemsCode("1");
		
		dto.setRecommendationXml("<xml></xml>");
		
		RouteCode routeCode = mock(RouteCode.class);
		when(routeCode.getCode()).thenReturn("1");
		when(routeCodeRepository.findByCode("1")).thenReturn(routeCode);
		
		AdministrativeGenderCode genderCode = mock(AdministrativeGenderCode.class);
		when(genderCode.getCode()).thenReturn("FE");
		when(genderCodeRepository.findByCode("FE")).thenReturn(genderCode);
		
		HealthIndicatorCode healthCode = mock(HealthIndicatorCode.class);
		when(healthCode.getCode()).thenReturn("1");
		when(healthIndicatorRepository.findByCode("1")).thenReturn(healthCode);
		
		EmotionalProblemsCode problemsCode = mock(EmotionalProblemsCode.class);
		when(problemsCode.getCode()).thenReturn("1");
		when(emotionalProblemsRepository.findByCode("1")).thenReturn(problemsCode);
		
		//Act
		Gpra gpra1 = mapper.map(dto);
		
		//Assert
		assertEquals(gpra1.isGpra(), true);
		assertEquals(dto.getRequestId(), gpra1.getRequestId());
		assertEquals(dto.getStaffId(), gpra1.getStaffId());
		assertEquals(dto.getNote1(), gpra1.getNote1());
		assertEquals(dto.getNote2(), gpra1.getNote2());
		assertEquals(dto.getAge(), gpra1.getAge());
		assertEquals(dto.getGenderCode(), gpra1.getGenderCode().getCode());
		
		assertEquals(dto.getHeroinDayCount(), gpra1.getHeroinDayCount());
		assertEquals(dto.getHeroinRouteCode(), gpra1.getHeroinRoute().getCode());
		
		assertEquals(dto.getMorphineCount(), gpra1.getMorphineCount());
		assertEquals(dto.getMorphineRouteCode(), gpra1.getMorphineRoute().getCode());
		
		assertEquals(dto.getDiluadidCount(), gpra1.getDiluadidCount());
		assertEquals(dto.getDiluadidRouteCode(), gpra1.getDiluadidRoute().getCode());
		
		assertEquals(dto.getDemerolCount(), gpra1.getDemerolCount());
		assertEquals(dto.getDemerolRouteCode(), gpra1.getDemerolRoute().getCode());
		
		assertEquals(dto.getPercocetCount(), gpra1.getPercocetCount());
		assertEquals(dto.getPercocetRouteCode(), gpra1.getPercocetRoute().getCode());
		
		assertEquals(dto.getDarvonCount(), gpra1.getDarvonCount());
		assertEquals(dto.getDarvonRouteCode(), gpra1.getDarvonRoute().getCode());
		
		assertEquals(dto.getCodeineCount(), gpra1.getCodeineCount());
		assertEquals(dto.getCodeineRouteCode(), gpra1.getCodeineRoute().getCode());
		
		assertEquals(dto.getTylenolCount(), gpra1.getTylenolCount());
		assertEquals(dto.getTylenolRouteCode(), gpra1.getTylenolRoute().getCode());
		
		assertEquals(dto.getOxycontinCount(), gpra1.getOxycontinCount());
		assertEquals(dto.getOxycontinRouteCode(), gpra1.getOxycontinRoute().getCode());
		
		assertEquals(dto.getMethadoneCount(), gpra1.getMethadoneCount());
		assertEquals(dto.getMethadoneRouteCode(), gpra1.getMethadoneRoute().getCode());
		
		assertEquals(dto.getHealthIndicatorCode(), gpra1.getHealthIndicator().getCode());
		
		assertEquals(dto.getDepressionCount(), gpra1.getDepressionCount());
		
		assertEquals(dto.getAnxietyCount(), gpra1.getAnxietyCount());
		
		assertEquals(dto.getViolentCount(), gpra1.getViolentCount());
		
		assertEquals(dto.getPsychMedicationCount(), gpra1.getPsychMedicationCount());
		
		assertEquals(dto.getEmotionalProblemsCode(), gpra1.getEmotionalProblemsCode().getCode());
		
		for(AuditRecommendations rec : gpra1.getAuditRecommendations()) {
			assertEquals(dto.getRecommendationXml(), rec.getRecommendationXML());
		}
	}

}
