package com.feisystems.bham.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feisystems.bham.domain.Gpra;
import com.feisystems.bham.domain.gpra.GpraVariableContainer;
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
public class GpraVariableContainerToGpraMapper implements DtoToDomainEntityMapper<GpraVariableContainer, Gpra> {

	@Autowired
	private RouteCodeRepository routeCodeRepository;

	@Autowired
	private EmotionalProblemsRepository emotionalProblemsRepository;

	@Autowired
	private HealthIndicatorRepository healthIndicatorRepository;

	@Autowired
	private AdministrativeGenderCodeRepository genderCodeRepository;

	@Override
	public Gpra map(GpraVariableContainer container) {

		System.out.println("Saving gpra...");
		long startTime = System.currentTimeMillis();

		Gpra gpra = new Gpra();

		gpra.setGpra(true);

		gpra.setTimeStamp(new Date());

		// age
		if (container.age != null) {
			gpra.setAge(container.age);
		}

		// gender
		if (container.cd_gpra_ivw_gender_id != null && container.cd_gpra_ivw_gender_id.genderCode != null) {
			AdministrativeGenderCode genderCode = genderCodeRepository.findByCode(container.cd_gpra_ivw_gender_id.genderCode.getValue());
			gpra.setGenderCode(genderCode);
		}

		// heroin
		if (container.heroin_day_count != null) {
			if (container.heroin_day_count.days != null) {
				gpra.setHeroinDayCount(container.heroin_day_count.days.getValue());
			} 
		}

		if (container.heroin_cd_gpra_ivw_route_id != null) {
			if (container.heroin_cd_gpra_ivw_route_id.route != null) {
				gpra.setHeroinRoute(retrieveRouteCode(container.heroin_cd_gpra_ivw_route_id.route.getValue()));
			} 
		}

		// morphine
		if (container.morphine_day_count != null) {
			if (container.morphine_day_count.days != null) {
				gpra.setMorphineCount(container.morphine_day_count.days.getValue());
			} 
		}

		if (container.morphine_cd_gpra_ivw_route_id != null) {
			if (container.morphine_cd_gpra_ivw_route_id.route != null) {
				gpra.setMorphineRoute(retrieveRouteCode(container.morphine_cd_gpra_ivw_route_id.route.getValue()));
			} 
		}

		// Diluadid
		if (container.diluadid_day_count != null) {
			if (container.diluadid_day_count.days != null) {
				gpra.setDiluadidCount(container.diluadid_day_count.days.getValue());
			} 
		}

		if (container.diluadid_cd_gpra_ivw_route_id != null) {
			if (container.diluadid_cd_gpra_ivw_route_id.route != null) {
				gpra.setDiluadidRoute(retrieveRouteCode(container.diluadid_cd_gpra_ivw_route_id.route.getValue()));
			} 
		}

		// Demerol
		if (container.demerol_day_count != null) {
			if (container.demerol_day_count.days != null) {
				gpra.setDemerolCount(container.demerol_day_count.days.getValue());
			} 
		}

		if (container.demerol_cd_gpra_ivw_route_id != null) {
			if (container.demerol_cd_gpra_ivw_route_id.route != null) {
				gpra.setDemerolRoute(retrieveRouteCode(container.demerol_cd_gpra_ivw_route_id.route.getValue()));
			} 
		}

		// Percocet
		if (container.percocet_day_count != null) {
			if (container.percocet_day_count.days != null) {
				gpra.setPercocetCount(container.percocet_day_count.days.getValue());
			} 
		}

		if (container.percocet_cd_gpra_ivw_route_id != null) {
			if (container.percocet_cd_gpra_ivw_route_id.route != null) {
				gpra.setPercocetRoute(retrieveRouteCode(container.percocet_cd_gpra_ivw_route_id.route.getValue()));
			} 
		}

		// Darvon
		if (container.darvon_day_count != null) {
			if (container.darvon_day_count.days != null) {
				gpra.setDarvonCount(container.darvon_day_count.days.getValue());
			} 
		}

		if (container.darvon_cd_gpra_ivw_route_id != null) {
			if (container.darvon_cd_gpra_ivw_route_id.route != null) {
				gpra.setDarvonRoute(retrieveRouteCode(container.darvon_cd_gpra_ivw_route_id.route.getValue()));
			}
		}

		// Codeine
		if (container.codeine_day_count != null) {
			if (container.codeine_day_count.days != null) {
				gpra.setCodeineCount(container.codeine_day_count.days.getValue());
			}
		}

		if (container.codeine_cd_gpra_ivw_route_id != null) {
			if (container.codeine_cd_gpra_ivw_route_id.route != null) {
				gpra.setCodeineRoute(retrieveRouteCode(container.codeine_cd_gpra_ivw_route_id.route.getValue()));
			}
		}

		// Tylenol
		if (container.tylenol_day_count != null) {
			if (container.tylenol_day_count.days != null) {
				gpra.setTylenolCount(container.tylenol_day_count.days.getValue());
			}
		}

		if (container.tylenol_cd_gpra_ivw_route_id != null) {
			if (container.tylenol_cd_gpra_ivw_route_id.route != null) {
				gpra.setTylenolRoute(retrieveRouteCode(container.tylenol_cd_gpra_ivw_route_id.route.getValue()));
			}
		}

		// Oxycontin
		if (container.oxyco_day_count != null) {
			if (container.oxyco_day_count.days != null) {
				gpra.setOxycontinCount(container.oxyco_day_count.days.getValue());
			}
		}

		if (container.oxyco_cd_gpra_ivw_route_id != null) {
			if (container.oxyco_cd_gpra_ivw_route_id.route != null) {
				gpra.setOxycontinRoute(retrieveRouteCode(container.oxyco_cd_gpra_ivw_route_id.route.getValue()));
			}
		}

		// Methadone
		if (container.methadone_day_count != null) {
			if (container.methadone_day_count.days != null) {
				gpra.setMethadoneCount(container.methadone_day_count.days.getValue());
			}
		}

		if (container.methadone_cd_gpra_ivw_route_id != null) {
			if (container.methadone_cd_gpra_ivw_route_id.route != null) {
				gpra.setMethadoneRoute(retrieveRouteCode(container.methadone_cd_gpra_ivw_route_id.route.getValue()));
			}
		}

		// Overall health
		if (container.cd_gpra_ivw_health_id != null) {
			if (container.cd_gpra_ivw_health_id.healthStatus != null) {
				gpra.setHealthIndicator(retrieveHealthIndicatorCode(container.cd_gpra_ivw_health_id.healthStatus.getValue()));
			}
		}

		// Depression Count
		if (container.depression_day_count != null) {
			if (container.depression_day_count.days != null) {
				gpra.setDepressionCount(container.depression_day_count.days.getValue());
			}
		}

		// Anxiety Count
		if (container.anxiety_day_count != null) {
			if (container.anxiety_day_count.days != null) {
				gpra.setAnxietyCount(container.anxiety_day_count.days.getValue());
			}
		}

		// Violent Count
		if (container.violent_behavior_day_count != null) {
			if (container.violent_behavior_day_count.days != null) {
				gpra.setViolentCount(container.violent_behavior_day_count.days.getValue());
			}
		}

		// Medications for psych Count
		if (container.psychol_emot_med_day_count != null) {
			if (container.psychol_emot_med_day_count.days != null) {
				gpra.setPsychMedicationCount(container.psychol_emot_med_day_count.days.getValue());
			}
		}

		// private Integer cd_gpra_ivw_psych_impact_id;
		if (container.cd_gpra_ivw_psych_impact_id != null) {
			if (container.cd_gpra_ivw_psych_impact_id.psycholImpact != null) {
				gpra.setEmotionalProblemsCode(retrieveEmotionalProblemsCode(container.cd_gpra_ivw_psych_impact_id.psycholImpact.getValue()));
			}
		}

		long endTime = System.currentTimeMillis();

		System.out.println("Difference : " + (endTime - startTime));

		return gpra;
	}

	private HealthIndicatorCode retrieveHealthIndicatorCode(int code) {
		return healthIndicatorRepository.findByCode(Integer.toString(filterUnmappedValues(code)));
	}

	private RouteCode retrieveRouteCode(int code) {
		return routeCodeRepository.findByCode(Integer.toString(filterUnmappedValues(code)));
	}

	private EmotionalProblemsCode retrieveEmotionalProblemsCode(int code) {
		return emotionalProblemsRepository.findByCode(Integer.toString(filterUnmappedValues(code)));
	}

	// -1 and -9 does not map to UI. Applying the closest mapping
	private static int filterUnmappedValues(int code) {
		return (code == -1 || code == -9) ? -8 : code;
	}

}
