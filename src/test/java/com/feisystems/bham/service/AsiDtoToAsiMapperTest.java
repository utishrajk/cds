package com.feisystems.bham.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.feisystems.bham.domain.Asi;
import com.feisystems.bham.domain.AuditRecommendations;
import com.feisystems.bham.domain.reference.ASIRouteCode;
import com.feisystems.bham.domain.reference.ASIRouteCodeRepository;
import com.feisystems.bham.domain.reference.AdministrativeGenderCode;
import com.feisystems.bham.domain.reference.AdministrativeGenderCodeRepository;
import com.feisystems.bham.domain.reference.EpisodeCode;
import com.feisystems.bham.domain.reference.EpisodeCodeRepository;
import com.feisystems.bham.domain.reference.PatientRatingCode;
import com.feisystems.bham.domain.reference.PatientRatingCodeRepository;
import com.feisystems.bham.domain.reference.ResponseCode;
import com.feisystems.bham.domain.reference.ResponseCodeRepository;


@RunWith(MockitoJUnitRunner.class)
public class AsiDtoToAsiMapperTest {
	
	private static final String FEMALE = "FE";

	private static final String ONE = "1";

	private static final String OR = "OR";

	private static final String YES = "YES";

	@InjectMocks
	private AsiDtoToAsiMapper mapper;
	
	@Mock
	private ResponseCodeRepository responseCodeRepository;

	@Mock
	private ASIRouteCodeRepository asiRouteCodeRepository;

	@Mock
	private EpisodeCodeRepository episodeCodeRepository;

	@Mock
	private PatientRatingCodeRepository patientRatingCodeRepository;

	@Mock
	private AdministrativeGenderCodeRepository genderCodeRepository;
	
	@Test
	public void testMap() {
		ResponseCode responseCode = mock(ResponseCode.class);
		when(responseCode.getCode()).thenReturn(YES);
		when(responseCodeRepository.findByCode(YES)).thenReturn(responseCode);
		
		ASIRouteCode asiCode = mock(ASIRouteCode.class);
		when(asiCode.getCode()).thenReturn(OR);
		when(asiRouteCodeRepository.findByCode(OR)).thenReturn(asiCode);
		
		EpisodeCode episodeCode = mock(EpisodeCode.class);
		when(episodeCode.getCode()).thenReturn(ONE);
		when(episodeCodeRepository.findByCode(ONE)).thenReturn(episodeCode);
		
		PatientRatingCode ratingCode = mock(PatientRatingCode.class);
		when(ratingCode.getCode()).thenReturn(ONE);
		when(patientRatingCodeRepository.findByCode(ONE)).thenReturn(ratingCode);
		
		AdministrativeGenderCode genderCode = mock(AdministrativeGenderCode.class);
		when(genderCode.getCode()).thenReturn(FEMALE);
		when(genderCodeRepository.findByCode(FEMALE)).thenReturn(genderCode);
		
		AsiDto dto = new AsiDto();
		dto.setAsi(true);
		dto.setRequestId("requestId");
		dto.setStaffId("staffId");
		dto.setNote1("note1");
		dto.setNote2("note2");
		dto.setAge(22);
		dto.setGenderCode(FEMALE);
		
		dto.setE19_days_employment_problem(1);
		
		dto.setD2a_intoxication_30(1);
		
		dto.setF28b_is_abused_p_lifetime_Code(YES);
		
		dto.setF29b_is_abused_s_lifetime_Code(YES);
		
		dto.setP9a_is_suicidal_30_Code(YES);
		
		dto.setP5a_is_anxiety_30_Code(YES);
		
		dto.setP4a_is_depression_30_Code(YES);
		
		dto.setP8a_is_violent_30_Code(YES);
		
		dto.setP11a_is_prescribed_30_Code(YES);
		
		dto.setD3a_heroin_30(1);
		
		dto.setD4a_methadone_30(1);
		
		dto.setD5a_opiates_30(1);
		
		dto.setD3c_route_of_intake_type_Code(OR);
		
		dto.setD4c_route_of_intake_type_Code(OR);
		
		dto.setD5c_route_of_intake_type_Code(OR);
		
		dto.setState(1);
		
		dto.setEpisodeCode(ONE);
		
		dto.setM6_days_medical_problem(1);
		
		dto.setM7_patient_rating_Code(ONE);
		
		dto.setM8_patient_rating_Code(ONE);
		
		dto.setF3_is_satisfied_marital_Code(YES);
		
		dto.setF30_days_conflict_family(1);
		
		dto.setF32_patient_rating_Code(ONE);
		
		dto.setF34_patient_rating_Code(ONE);
		
		dto.setF18a_is_mother_30_Code(YES);
		
		dto.setF19a_is_father_30_Code(YES);
		
		dto.setF20a_is_sibling_30_Code(YES);
		
		dto.setF21a_is_spouse_30_Code(YES);
		
		dto.setF22a_is_children_30_Code(YES);
		
		dto.setF23a_is_other_30_Code(YES);
		
		dto.setF24a_is_friends_30_Code(YES);
		
		dto.setF25a_is_neighbor_30_Code(YES);
		
		dto.setF26a_is_co_worker_30_Code(YES);
		
		dto.setL24_is_waiting_trial_Code(YES);
		
		dto.setL27_days_illegal(1);
		
		dto.setL28_patient_rating_Code(ONE);
		
		dto.setL29_patient_rating_Code(ONE);
		
		dto.setE17_income_illegal(1);
		
		
		//Act
		Asi asi = mapper.map(dto);
		
		//Assert
		assertEquals(asi.isAsi(), true);
		assertEquals(dto.getRequestId(), asi.getRequestId());
		assertEquals(dto.getStaffId(), asi.getStaffId());
		assertEquals(dto.getNote1(), asi.getNote1());
		assertEquals(dto.getNote2(), asi.getNote2());
		assertEquals(dto.getAge(), asi.getAge());
		assertEquals(dto.getGenderCode(), asi.getGenderCode().getCode());
		
		assertEquals(dto.getE19_days_employment_problem(), asi.getE19_days_employment_problem());
		
		assertEquals(dto.getD2a_intoxication_30(), asi.getD2a_intoxication_30());
		
		assertEquals(dto.getF28b_is_abused_p_lifetime_Code(), asi.getF28b_is_abused_p_lifetime_Code().getCode());
		
		assertEquals(dto.getF29b_is_abused_s_lifetime_Code(), asi.getF29b_is_abused_s_lifetime_Code().getCode());
		
		assertEquals(dto.getP9a_is_suicidal_30_Code(), asi.getP9a_is_suicidal_30_Code().getCode());
		
		assertEquals(dto.getP5a_is_anxiety_30_Code(), asi.getP5a_is_anxiety_30_Code().getCode());
		
		assertEquals(dto.getP4a_is_depression_30_Code(), asi.getP4a_is_depression_30_Code().getCode());
		
		assertEquals(dto.getP8a_is_violent_30_Code(), asi.getP8a_is_violent_30_Code().getCode());
		
		assertEquals(dto.getP11a_is_prescribed_30_Code(), asi.getP11a_is_prescribed_30_Code().getCode());
		
		assertEquals(dto.getD3a_heroin_30(), asi.getD3a_heroin_30());
		
		assertEquals(dto.getD4a_methadone_30(), asi.getD4a_methadone_30());
		
		assertEquals(dto.getD5a_opiates_30(), asi.getD5a_opiates_30());
		
		assertEquals(dto.getD3c_route_of_intake_type_Code(), asi.getD3c_route_of_intake_type_Code().getCode());
		
		assertEquals(dto.getD4c_route_of_intake_type_Code(), asi.getD4c_route_of_intake_type_Code().getCode());
		
		assertEquals(dto.getD5c_route_of_intake_type_Code(), asi.getD5c_route_of_intake_type_Code().getCode());
		
		assertEquals(dto.getEpisodeCode(), asi.getEpisodeCode().getCode());
		
		assertEquals(dto.getM6_days_medical_problem(), asi.getM6_days_medical_problem());
		
		assertEquals(dto.getM7_patient_rating_Code(), asi.getM7_patient_rating_Code().getCode());
		
		assertEquals(dto.getM8_patient_rating_Code(), asi.getM8_patient_rating_Code().getCode());
		
		assertEquals(dto.getF3_is_satisfied_marital_Code(), asi.getF3_is_satisfied_marital_Code().getCode());
		
		assertEquals(dto.getF30_days_conflict_family(), asi.getF30_days_conflict_family());
		
		assertEquals(dto.getF32_patient_rating_Code(), asi.getF32_patient_rating_Code().getCode());
		
		assertEquals(dto.getF34_patient_rating_Code(), asi.getF34_patient_rating_Code().getCode());
		
		assertEquals(dto.getF18a_is_mother_30_Code(), asi.getF18a_is_mother_30_Code().getCode());
		
		assertEquals(dto.getF19a_is_father_30_Code(), asi.getF19a_is_father_30_Code().getCode());
		
		assertEquals(dto.getF20a_is_sibling_30_Code(), asi.getF20a_is_sibling_30_Code().getCode());
		
		assertEquals(dto.getF21a_is_spouse_30_Code(), asi.getF21a_is_spouse_30_Code().getCode());
		
		assertEquals(dto.getF22a_is_children_30_Code(), asi.getF22a_is_children_30_Code().getCode());
		
		assertEquals(dto.getF23a_is_other_30_Code(), asi.getF23a_is_other_30_Code().getCode());
		
		assertEquals(dto.getF24a_is_friends_30_Code(), asi.getF24a_is_friends_30_Code().getCode());
		
		assertEquals(dto.getF25a_is_neighbor_30_Code(), asi.getF25a_is_neighbor_30_Code().getCode());
		
		assertEquals(dto.getF26a_is_co_worker_30_Code(), asi.getF26a_is_co_worker_30_Code().getCode());
		
		assertEquals(dto.getL24_is_waiting_trial_Code(), asi.getL24_is_waiting_trial_Code().getCode());
		
		assertEquals(dto.getL27_days_illegal(), asi.getL27_days_illegal());
		
		assertEquals(dto.getL28_patient_rating_Code(), asi.getL28_patient_rating_Code().getCode());
		
		assertEquals(dto.getL29_patient_rating_Code(), asi.getL29_patient_rating_Code().getCode());
		
		assertEquals(dto.getE17_income_illegal(), asi.getE17_income_illegal());
		
		assertEquals(dto.getL28_patient_rating_Code(), asi.getL28_patient_rating_Code().getCode());
		
		assertEquals(dto.getL29_patient_rating_Code(), asi.getL29_patient_rating_Code().getCode());
		
		assertEquals(dto.getE17_income_illegal(), asi.getE17_income_illegal());
		
		for(AuditRecommendations rec : asi.getAuditRecommendations()) {
			assertEquals(dto.getRecommendationXml(), rec.getRecommendationXML());
		}	

		
	}

}
