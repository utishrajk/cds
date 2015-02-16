package com.feisystems.bham.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feisystems.bham.domain.reference.AdministrativeGenderCode;
import com.feisystems.bham.domain.reference.AdministrativeGenderCodeRepository;

@Component
public class GpraMapper {

	@Autowired
	AdministrativeGenderCodeRepository genderCodeRepo;

	public void map(final GpraDto dto, final PatientData patientData) {

		// overall_health_indicator
		if (dto.getHealthIndicatorCode() != null) {
			if (dto.getHealthIndicatorCode().equals("1") || dto.getHealthIndicatorCode().equals("2") || dto.getHealthIndicatorCode().equals("3")) {
				patientData.setOverall_health_indicator(0);
			} else {
				patientData.setOverall_health_indicator(1);
			}
		}

		// age
		if (dto.getAge() != null) {
			patientData.setAge_covariate(dto.getAge());
		}

		// gender
		if (dto.getGenderCode() != null) {
			AdministrativeGenderCode genderCode = genderCodeRepo.findByCode(dto.getGenderCode());
			if (genderCode.getCode().equals("FE")) {
				patientData.setGender_covariate(1);
			} else if (genderCode.getCode().equals("MA")) {
				patientData.setGender_covariate(0);
			}
		}

		// heroin
		if (dto.getHeroinDayCount() != null) {
			if (dto.getHeroinDayCount() > 0) {
				patientData.setHeroin_indicator(1);
			} else {
				patientData.setHeroin_indicator(0);
			}
		}

		// morphine
		if (dto.getMorphineCount() != null) {
			if (dto.getMorphineCount() == 0) {
				patientData.setMorphine_indicator(1);
			} else {
				patientData.setMorphine_indicator(0);
			}
		}

		// diduadid
		if (dto.getDiluadidCount() != null) {
			if (dto.getDiluadidCount() > 0) {
				patientData.setDiluadid_indicator(1);
			} else {
				patientData.setDiluadid_indicator(0);
			}
		}

		// demerol
		if (dto.getDemerolCount() != null) {
			if (dto.getDemerolCount() == 0) {
				patientData.setDemerol_indicator(1);
			} else {
				patientData.setDemerol_indicator(0);
			}
		}

		// percocet
		if (dto.getPercocetCount() != null) {
			if (dto.getPercocetCount() > 0) {
				patientData.setPercocet_indicator(1);
			} else {
				patientData.setPercocet_indicator(0);
			}
		}

		// darvon
		if (dto.getDarvonCount() != null) {
			if (dto.getDarvonCount() > 0) {
				patientData.setDarvon_indicator(1);
			} else {
				patientData.setDarvon_indicator(0);
			}
		}

		// codeine
		if (dto.getCodeineCount() != null) {
			if (dto.getCodeineCount() > 0) {
				patientData.setCodeine_indicator(1);
			} else {
				patientData.setCodeine_indicator(0);
			}
		}

		// tylenol
		if (dto.getTylenolCount() != null) {
			if (dto.getTylenolCount() > 0) {
				patientData.setTylenol_indicator(1);
			} else {
				patientData.setTylenol_indicator(0);
			}
		}

		// Oxyco
		if (dto.getOxycontinCount() != null) {
			if (dto.getOxycontinCount() > 0) {
				patientData.setOxyco_indicator(1);
			} else {
				patientData.setOxyco_indicator(0);
			}
		}

		// methadone
		if (dto.getMethadoneCount() != null) {
			if (dto.getMethadoneCount() > 0) {
				patientData.setMethadone_indicator(1);
			} else {
				patientData.setMethadone_indicator(0);
			}
		}

		// other_opioid
		if (eq(patientData.getMorphine_indicator(), 1)
				|| eq(patientData.getDiluadid_indicator(), 1)
				|| eq(patientData.getDemerol_indicator(), 1)
				|| eq(patientData.getPercocet_indicator(), 1)
				|| eq(patientData.getDarvon_indicator(), 1)
				|| eq(patientData.getCodeine_indicator(), 1)
				|| eq(patientData.getTylenol_indicator(), 1)
				|| eq(patientData.getOxyco_indicator(), 1)) {
			patientData.setOther_opioid_indicator(1);
		}

		if (eq(patientData.getDiluadid_indicator(), 0)
				&& eq(patientData.getDemerol_indicator(), 0)
				&& eq(patientData.getPercocet_indicator(), 0)
				&& eq(patientData.getDarvon_indicator(), 0)
				&& eq(patientData.getCodeine_indicator(), 0)
				&& eq(patientData.getTylenol_indicator(), 0)
				&& eq(patientData.getOxyco_indicator(), 0)) {
			patientData.setOther_opioid_indicator(0);
		}

		// rx_or_meth_no_heroin
		if (eq(patientData.getHeroin_indicator(), 1)) {
			patientData.setRx_or_meth_no_heroin_covariate(0);
		} else if ((eq(patientData.getOther_opioid_indicator(), 1) || eq(patientData.getMorphine_indicator(), 1))
				&& eq(patientData.getHeroin_indicator(), 0)) {
			patientData.setRx_or_meth_no_heroin_covariate(1);
		}

		// heroin_only_covariate
		if (greatOrEq(patientData.getOther_opioid_indicator(), 0)
				|| greatOrEq(patientData.getMethadone_indicator(), 0)
				|| greatOrEq(patientData.getHeroin_indicator(), 0)) {
			patientData.setHeroin_only_covariate(0);
		}

		if (eq(patientData.getOther_opioid_indicator(), 0) && eq(patientData.getMethadone_indicator(), 0) && eq(patientData.getHeroin_indicator(), 1)) {
			patientData.setHeroin_only_covariate(1);
		}

		int[] range1 = { 2, 4, 5 };
		int[] range2 = { 1, 3 };

		// gpra_route_indicator
		if ((eq(patientData.getHeroin_indicator(), 1)
				|| eq(patientData.getMorphine_indicator(), 1)
				|| eq(patientData.getDiluadid_indicator(), 1)
				|| eq(patientData.getDemerol_indicator(), 1)
				|| eq(patientData.getPercocet_indicator(), 1)
				|| eq(patientData.getDarvon_indicator(), 1)
				|| eq(patientData.getCodeine_indicator(), 1)
				|| eq(patientData.getTylenol_indicator(), 1)
				|| eq(patientData.getOxyco_indicator(), 1)
				|| eq(patientData.getMethadone_indicator(), 1))

				&& (checkRange(dto.getHeroinRouteCode(), range1)
						|| checkRange(dto.getMorphineRouteCode(), range1)
						|| checkRange(dto.getDiluadidRouteCode(), range1)
						|| checkRange(dto.getDemerolRouteCode(), range1)
						|| checkRange(dto.getPercocetRouteCode(), range1)
						|| checkRange(dto.getDarvonRouteCode(), range1)
						|| checkRange(dto.getCodeineRouteCode(), range1)
						|| checkRange(dto.getTylenolRouteCode(), range1)
						|| checkRange(dto.getMethadoneRouteCode(), range1)
						|| checkRange(dto.getOxycontinRouteCode(), range1))) {
			patientData.setGpra_route_indicator(1);
		}

		if ((eq(patientData.getHeroin_indicator(), 1)
				|| eq(patientData.getMorphine_indicator(), 1)
				|| eq(patientData.getDiluadid_indicator(), 1)
				|| eq(patientData.getDemerol_indicator(), 1)
				|| eq(patientData.getPercocet_indicator(), 1)
				|| eq(patientData.getDarvon_indicator(), 1)
				|| eq(patientData.getCodeine_indicator(), 1)
				|| eq(patientData.getTylenol_indicator(), 1)
				|| eq(patientData.getTylenol_indicator(), 1)
				|| eq(patientData.getOxyco_indicator(), 1)
				|| eq(patientData.getMethadone_indicator(), 1))

				&& (checkRange2(dto.getHeroinRouteCode(), range2)
						|| checkRange2(dto.getMorphineRouteCode(), range2)
						|| checkRange2(dto.getDiluadidRouteCode(), range2)
						|| checkRange2(dto.getDemerolRouteCode(), range2)
						|| checkRange2(dto.getPercocetRouteCode(), range2)
						|| checkRange2(dto.getDarvonRouteCode(), range2)
						|| checkRange2(dto.getCodeineRouteCode(), range2)
						|| checkRange2(dto.getTylenolRouteCode(), range2)
						|| checkRange2(dto.getMethadoneRouteCode(), range2)
						|| checkRange2(dto.getOxycontinRouteCode(), range2))) {
			patientData.setGpra_route_indicator(0);
		}

		if (eq(patientData.getHeroin_indicator(), 0)
				&& eq(patientData.getMorphine_indicator(), 0)
				&& eq(patientData.getDiluadid_indicator(), 0)
				&& eq(patientData.getDemerol_indicator(), 0)
				&& eq(patientData.getPercocet_indicator(), 0)
				&& eq(patientData.getDarvon_indicator(), 0)
				&& eq(patientData.getCodeine_indicator(), 0)
				&& eq(patientData.getTylenol_indicator(), 0)
				&& eq(patientData.getOxyco_indicator(), 0)
				&& eq(patientData.getMethadone_indicator(), 0)) {
			patientData.setGpra_route_indicator(0);
		}

		// psych_indicator
		if (dto.getEmotionalProblemsCode() != null) {
			if (dto.getEmotionalProblemsCode().equals("4") || dto.getEmotionalProblemsCode().equals("5")) {
				patientData.setPsych_indicator(1);
			} else {
				patientData.setPsych_indicator(0);
			}
		}

		// anxiety_indicator
		if (dto.getAnxietyCount() != null) {
			if (dto.getAnxietyCount() > 0) {
				patientData.setAnxiety_indicator(1);
			} else {
				patientData.setAnxiety_indicator(0);
			}
		}

		// depression_indicator
		if (dto.getDepressionCount() != null) {
			if (dto.getDepressionCount() > 0) {
				patientData.setDepression_indicator(1);
			} else {
				patientData.setDepression_indicator(0);
			}
		}

		// violent_behavior_indicator
		if (dto.getViolentCount() != null) {
			if (dto.getViolentCount() > 0) {
				patientData.setViolent_behavior_indicator(1);
			} else {
				patientData.setViolent_behavior_indicator(0);
			}
		}

		// psych_med_indicator
		if (dto.getPsychMedicationCount() != null) {
			if (dto.getPsychMedicationCount() > 0) {
				patientData.setPsych_med_indicator(1);
			} else {
				patientData.setPsych_med_indicator(0);
			}
		}

		// psych_any_indicator
		if (greatOrEq(patientData.getPsych_any_indicator(), 0)
				|| greatOrEq(patientData.getAnxiety_indicator(), 0)
				|| greatOrEq(patientData.getDepression_indicator(), 0)) {
			patientData.setPsych_any_indicator(0);
		}

		if (eq(patientData.getPsych_any_indicator(), 1)
				|| eq(patientData.getAnxiety_indicator(), 1)
				|| eq(patientData.getDepression_indicator(), 1)) {
			patientData.setPsych_any_indicator(1);
		}

	}

	private static boolean eq(Integer var, Integer value) {
		return (var != null && var == value);
	}

	private static boolean greatOrEq(Integer var, Integer value) {
		return (var != null && var >= value);
	}

	private static boolean checkRange(String routeCode, int[] range) {
		if (routeCode != null) {
			int value = Integer.parseInt(routeCode);
			return value == range[0] || value == range[1] || value == range[2];
		}
		return false;
	}

	private static boolean checkRange2(String routeCode, int[] range2) {
		if (routeCode != null) {
			int value = Integer.parseInt(routeCode);
			return value == range2[0] || value == range2[1];
		}
		return false;
	}

}
