package com.feisystems.bham.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import com.feisystems.bham.infrastructure.DtoToDomainEntityMapper;

@Component
public class GpraDtoToGpraMapper implements DtoToDomainEntityMapper<GpraDto, Gpra> {

	@Autowired
	AdministrativeGenderCodeRepository genderCodeRepository;

	@Autowired
	RouteCodeRepository routeCodeRepository;

	@Autowired
	HealthIndicatorRepository healthIndicatorRepository;

	@Autowired
	EmotionalProblemsRepository emotionalProblemsRepository;
	
	@Autowired
	UsernameService usernameService;

	@Override
	public Gpra map(GpraDto dto) {

		// I am always creating a new record every time I save.
		Gpra gpra = new Gpra();

		gpra.setTimeStamp(new Date());

		gpra.setGpra(true);

		gpra.setRequestId(dto.getRequestId());

		gpra.setStaffId(dto.getStaffId());

		gpra.setUniqueClientNumber(dto.getUniqueClientNumber());

		gpra.setNote1(dto.getNote1());

		gpra.setNote2(dto.getNote2());

		gpra.setAge(dto.getAge());

		// gender
		AdministrativeGenderCode genderCode = genderCodeRepository.findByCode(dto.getGenderCode());
		gpra.setGenderCode(genderCode);

		// other description
		if (dto.getOtherDescription() != null) {
			gpra.setOtherDescription(dto.getOtherDescription());
		}

		// heroin
		gpra.setHeroinDayCount(dto.getHeroinDayCount());

		RouteCode heroinRoute = routeCodeRepository.findByCode(dto.getHeroinRouteCode());
		gpra.setHeroinRoute(heroinRoute);

		// morphine
		gpra.setMorphineCount(dto.getMorphineCount());

		RouteCode morphineRoute = routeCodeRepository.findByCode(dto.getMorphineRouteCode());
		gpra.setMorphineRoute(morphineRoute);

		// diluadid
		gpra.setDiluadidCount(dto.getDiluadidCount());

		RouteCode diluadidRoute = routeCodeRepository.findByCode(dto.getDiluadidRouteCode());
		gpra.setDiluadidRoute(diluadidRoute);

		// demerol
		gpra.setDemerolCount(dto.getDemerolCount());

		RouteCode demerolRoute = routeCodeRepository.findByCode(dto.getDemerolRouteCode());
		gpra.setDemerolRoute(demerolRoute);

		// percocet
		gpra.setPercocetCount(dto.getPercocetCount());

		RouteCode percocetRoute = routeCodeRepository.findByCode(dto.getPercocetRouteCode());
		gpra.setPercocetRoute(percocetRoute);

		// darvon
		gpra.setDarvonCount(dto.getDarvonCount());

		RouteCode darvonRoute = routeCodeRepository.findByCode(dto.getDarvonRouteCode());
		gpra.setDarvonRoute(darvonRoute);

		// codeine
		gpra.setCodeineCount(dto.getCodeineCount());

		RouteCode codeineRoute = routeCodeRepository.findByCode(dto.getCodeineRouteCode());
		gpra.setCodeineRoute(codeineRoute);

		// tylenol
		gpra.setTylenolCount(dto.getTylenolCount());

		RouteCode tylenolRoute = routeCodeRepository.findByCode(dto.getTylenolRouteCode());
		gpra.setTylenolRoute(tylenolRoute);

		// oxycontin
		gpra.setOxycontinCount(dto.getOxycontinCount());

		RouteCode oxycontinRoute = routeCodeRepository.findByCode(dto.getOxycontinRouteCode());
		gpra.setOxycontinRoute(oxycontinRoute);

		// methadone
		gpra.setMethadoneCount(dto.getMethadoneCount());

		RouteCode methadoneRoute = routeCodeRepository.findByCode(dto.getMethadoneRouteCode());
		gpra.setMethadoneRoute(methadoneRoute);

		// overall health
		HealthIndicatorCode healthCode = healthIndicatorRepository.findByCode(dto.getHealthIndicatorCode());
		gpra.setHealthIndicator(healthCode);

		// depression
		gpra.setDepressionCount(dto.getDepressionCount());

		// anxiety
		gpra.setAnxietyCount(dto.getAnxietyCount());

		// violent
		gpra.setViolentCount(dto.getViolentCount());

		// psychological
		gpra.setPsychMedicationCount(dto.getPsychMedicationCount());

		// gpra.setCd_gpra_ivw_psych_impact_id(dto.getCd_gpra_ivw_psych_impact_id());
		EmotionalProblemsCode problemsCode = emotionalProblemsRepository.findByCode(dto.getEmotionalProblemsCode());
		gpra.setEmotionalProblemsCode(problemsCode);

		Set<AuditRecommendations> auditRecommendationsSet = new HashSet<AuditRecommendations>();
		AuditRecommendations auditRecommendations = new AuditRecommendations();
		auditRecommendations.setRecommendationXML(dto.getRecommendationXml());
		auditRecommendations.setRequestId(dto.getRequestId());
		auditRecommendations.setTimestamp(new Date());
		auditRecommendations.setGpra(gpra);
		auditRecommendations.setUsername(usernameService.retrieveUsername());
		
		auditRecommendationsSet.add(auditRecommendations);
		gpra.setAuditRecommendations(auditRecommendationsSet);

		return gpra;
	}

}
