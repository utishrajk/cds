package com.feisystems.bham.service;

import org.springframework.stereotype.Component;

import com.feisystems.bham.util.Constant;

@Component
public class ASIMapper {

	private static final String INDIFFERENT = "INDIFFERENT";
	private static final String NO = "NO";
	private static final String YES = "YES";
	private static final int UNKNOWN = -7;
	private static final int NOT_COLLECTED = -8;

	public void map(final AsiDto dto, final PatientData patientData) {
		// HI State=1
		// TN State=2

		if (dto.getState() == null) {
			return;
		}

		// prior Episode
		if (dto.getEpisodeCode() != null) {
			if (Integer.parseInt(dto.getEpisodeCode()) != UNKNOWN && Integer.parseInt(dto.getEpisodeCode()) != NOT_COLLECTED) {
				patientData.setPriorEpisode(Integer.parseInt(dto.getEpisodeCode()));
			}
		}

		// Legal_4c
		// If legal_score 0 then Legal_4c= 1;
		// if legal_score >0 and <= 0.14 then Legal_4c= 2
		// if legal_score >0.14 and <= 0.39 then Legal_4c= 3
		// if legal_score >0.39 then Legal_4c= 4
		double A = 0;

		// TODO: What are the values for Yes and No?
		if (dto.getL24_is_waiting_trial_Code().equals(YES)) {
			A = 1;
		} else if (dto.getL24_is_waiting_trial_Code().equals(NO)) {
			A = 0;
		}

		double B = dto.getL27_days_illegal();

		double C = Double.parseDouble(dto.getL28_patient_rating_Code());

		double D = Double.parseDouble(dto.getL29_patient_rating_Code());

		double E = dto.getE17_income_illegal();

		double legalScore = (A / 5) + (B / 150) + (C / 20) + (D / 20) + (Math.log(E) / 46);

		if (legalScore == 0.0) {
			patientData.setLegal_4c(1);

		} else if (legalScore > 0.0 && legalScore <= 0.14) {
			patientData.setLegal_4c(2);

		} else if (legalScore > 0.14 && legalScore <= 0.39) {
			patientData.setLegal_4c(3);

		} else if (legalScore > 0.39) {
			patientData.setLegal_4c(4);

		}

		// Medical_score
		// If Medical_score=0 then Medical_3c = 1
		// If Medical_score>0 and <= 0.57 then Medical_3c = 2
		// If Medical_score> 0.57 then Medical_3c = 3
		// TODO: Calculate this
		double medicalScore = (dto.getM6_days_medical_problem() / 60.0) + (Double.parseDouble(dto.getM7_patient_rating_Code()) / 12.0)
				+ (Double.parseDouble(dto.getM8_patient_rating_Code()) / 12.0);

		if (medicalScore == 0.0) {
			patientData.setMedical_3c(1);

		} else if (medicalScore > 0 && medicalScore <= 0.57) {
			patientData.setMedical_3c(2);

		} else if (medicalScore > 0.57) {
			patientData.setMedical_3c(3);

		}

		// Family_score
		double a = 0;
		if (dto.getF3_is_satisfied_marital_Code() != null) {
			if (dto.getF3_is_satisfied_marital_Code().equals(YES)) {
				a = 0;
			} else if (dto.getF3_is_satisfied_marital_Code().equals(NO)) {
				a = 2;
			} else if (dto.getF3_is_satisfied_marital_Code().equals(INDIFFERENT)) {
				a = 1;
			}
		}

		double b = dto.getF30_days_conflict_family();

		double c = Double.parseDouble(dto.getF32_patient_rating_Code());

		double d = Double.parseDouble(dto.getF34_patient_rating_Code());

		double numberOfSeriousProblems = 0;
		double totalResponses = 0;

		if (equalsYES(dto.getF18a_is_mother_30_Code())) {
			numberOfSeriousProblems++;
			totalResponses++;
		} else if (equalsNO(dto.getF18a_is_mother_30_Code())) {
			totalResponses++;
		}

		if (equalsYES(dto.getF18a_is_mother_30_Code())) {
			numberOfSeriousProblems++;
			totalResponses++;
		} else if (equalsNO(dto.getF18a_is_mother_30_Code())) {
			totalResponses++;
		}

		if (equalsYES(dto.getF20a_is_sibling_30_Code())) {
			numberOfSeriousProblems++;
			totalResponses++;
		} else if (equalsNO(dto.getF20a_is_sibling_30_Code())) {
			totalResponses++;
		}

		if (equalsYES(dto.getF21a_is_spouse_30_Code())) {
			numberOfSeriousProblems++;
			totalResponses++;
		} else if (equalsNO(dto.getF21a_is_spouse_30_Code())) {
			totalResponses++;
		}

		if (equalsYES(dto.getF22a_is_children_30_Code())) {
			numberOfSeriousProblems++;
			totalResponses++;
		} else if (equalsNO(dto.getF22a_is_children_30_Code())) {
			totalResponses++;
		}

		if (equalsYES(dto.getF23a_is_other_30_Code())) {
			numberOfSeriousProblems++;
			totalResponses++;
		} else if (equals(dto.getF23a_is_other_30_Code())) {
			totalResponses++;
		}

		if (equalsYES(dto.getF24a_is_friends_30_Code())) {
			numberOfSeriousProblems++;
			totalResponses++;
		} else if (equalsNO(dto.getF24a_is_friends_30_Code())) {
			totalResponses++;
		}

		if (equalsYES(dto.getF25a_is_neighbor_30_Code())) {
			numberOfSeriousProblems++;
			totalResponses++;
		} else if (equalsNO(dto.getF25a_is_neighbor_30_Code())) {
			totalResponses++;
		}

		if (equalsYES(dto.getF26a_is_co_worker_30_Code())) {
			numberOfSeriousProblems++;
			totalResponses++;
		} else if (equalsNO(dto.getF26a_is_co_worker_30_Code())) {
			totalResponses++;
		}

		double ratio = (numberOfSeriousProblems / totalResponses);

		double familyScore = (a / 10.0) + (b / 150.0) + (c / 20.0) + (d / 20.0) + (ratio / 5.0);
		// If Family_score= 0 then Family_4c = 1;
		// If Family_score> 0 and <= 0.19 then Family_4c = 2;
		// If Family_score > 0.19 and <= 0.42 then Family_4c = 3;
		// If Family_score > 0.42 then Family_4c = 4;
		if (familyScore == 0.0) {
			patientData.setFamily_4c(1);

		} else if (familyScore > 0 && familyScore <= 0.19) {
			patientData.setFamily_4c(2);

		} else if (familyScore > 0.19 && familyScore <= 0.42) {
			patientData.setFamily_4c(3);

		} else if (familyScore > 0.42) {
			patientData.setFamily_4c(4);
		}

		// phyabuse
		// if f28b_is_abused_p_lifetime=""NULL"" then phyabuse=.;
		// else if f28b_is_abused_p_lifetime=""NO"" then phyabuse=0;
		// else if f28b_is_abused_p_lifetime=""Not"" then phyabuse=0;
		// else if f28b_is_abused_p_lifetime=""YES"" then phyabuse=1;
		patientData.setPhyabuse(checkResponse(dto.getF28b_is_abused_p_lifetime_Code()));

		// sexabuse
		// if f29b_is_abused_s_lifetime=""NULL"" then sexabuse=.;
		// else if f29b_is_abused_s_lifetime=""NO"" then sexabuse=0;
		// else if f29b_is_abused_s_lifetime=""Not"" then sexabuse=0;
		// else if f29b_is_abused_s_lifetime=""YES"" then sexabuse=1;
		patientData.setSexabuse(checkResponse(dto.getF29b_is_abused_s_lifetime_Code()));

		// abuse
		// if phyabuse =1 or sexabuse=1 then abuse=1;
		// else if phyabuse =0 and sexabuse=0 then abuse=0;
		// else abuse=.;
		if (patientData.getPhyabuse() == 1 || patientData.getSexabuse() == 1) {
			patientData.setAbuse(1);
		} else if (patientData.getPhyabuse() == 0 && patientData.getSexabuse() == 0) {
			patientData.setAbuse(0);
		}

		// suicidal
		// if p9a_is_suicidal_30=""YES"" then suicidal=1;
		// else if p9a_is_suicidal_30=""NO"" then suicidal=0;
		// else if p9a_is_suicidal_30=""NUL"" then suicidal=.;
		if (dto.getP9a_is_suicidal_30_Code() != null) {
			if (dto.getP9a_is_suicidal_30_Code().equals(YES)) {
				patientData.setSuicidal(1);
			} else if (dto.getP9a_is_suicidal_30_Code().equals(NO)) {
				patientData.setSuicidal(0);
			}
		}

		// anxiety
		// if p5a_is_anxiety_30=""YES"" then anxiety=1;
		// else if p5a_is_anxiety_30=""NO"" then anxiety=0;
		// else if p5a_is_anxiety_30=""NUL"" then anxiety=.;
		if (dto.getP5a_is_anxiety_30_Code() != null) {
			if (dto.getP5a_is_anxiety_30_Code().equals(YES)) {
				patientData.setAnxiety(1);
			} else if (dto.getP5a_is_anxiety_30_Code().equals(NO)) {
				patientData.setAnxiety(0);
			}
		}

		// if p4a_is_depression_30=""Yes"" then depression=1;
		// else if p4a_is_depression_30=""No"" then depression=0;
		// else if p4a_is_depression_30=""NUL"" then depression=.;
		if (dto.getP4a_is_depression_30_Code() != null) {
			if (dto.getP4a_is_depression_30_Code().equals(YES)) {
				patientData.setDepression(1);
			} else if (dto.getP4a_is_depression_30_Code().equals(NO)) {
				patientData.setDepression(0);
			}
		}

		// anxdep
		// if anxiety==1 or depression==1 then anxdep=1;
		// if anxiety==0 and depression==0 then anxdep=0;
		if (patientData.getAnxiety() == 1 || patientData.getDepression() == 1) {
			patientData.setAnxdep(1);
		} else if (patientData.getAnxiety() == 0 || patientData.getDepression() == 0) {
			patientData.setAnxdep(0);
		}

		// violent
		// if p8a_is_violent_30=""Yes"" then violent=1;
		// else if p8a_is_violent_30=""No"" then violent=0;
		// else if p8a_is_violent_30=""NUL"" then violent=.;
		if (dto.getP8a_is_violent_30_Code() != null) {
			if (dto.getP8a_is_violent_30_Code().equals(YES)) {
				patientData.setViolent(1);
			} else if (dto.getP8a_is_violent_30_Code().equals(NO)) {
				patientData.setViolent(0);
			}
		}

		// prescribed
		// if p11a_is_prescribed_30=""YES"" then prescribed=1;
		// else if p11a_is_prescribed_30=""NO"" then prescribed=0;
		// else if p11a_is_prescribed_30=""NUL"" then prescribed=.;
		if (dto.getP11a_is_prescribed_30_Code() != null) {
			if (dto.getP11a_is_prescribed_30_Code().equals(YES)) {
				patientData.setPrescribed(1);
			} else if (dto.getP11a_is_prescribed_30_Code().equals(NO)) {
				patientData.setPrescribed(0);
			}
		}

		// gender
		// if gender_code=""FE"" then gender=1;
		// else if gender_code=""MA"" then gender=0;
		if (dto.getGenderCode() != null) {
			if (dto.getGenderCode().equals("FE")) {
				patientData.setGender_covariate(1);
			} else if (dto.getGenderCode().equals("MA")) {
				patientData.setGender_covariate(0);
			}
		}

		// Only for Hawaii
		if (dto.getState() != null &&  dto.getState().equals("HI")) {
			// if e19_days_employment_problem=0 then employmentprob=0
			// else if e19_days_employment_problem>=1 then employmentprob=1
			// else
			// employmentprob=.;
			// employment problem
			if (dto.getE19_days_employment_problem() != null) {
				if (dto.getE19_days_employment_problem() == 0) {
					patientData.setEmploymentprob(0);
				} else if (dto.getE19_days_employment_problem() >= 1) {
					patientData.setEmploymentprob(1);
				}
			}

			// intoxication
			// if d2a_intoxication_30=0 then intoxication30=0;
			// else if d2a_intoxication_30>0 then intoxication30=1;
			// else intoxication30=.;
			if (dto.getD2a_intoxication_30() != null) {
				if (dto.getD2a_intoxication_30() == 0) {
					patientData.setIntoxication30(0);
				} else if (dto.getD2a_intoxication_30() > 0) {
					patientData.setIntoxication30(1);
				}
			}

			// heroin30any
			// if d3a_heroin_30=0 then heroin30any=0;
			// else if d3a_heroin_30>0 then heroin30any=1;
			// else heroin30any=.;
			if (dto.getD3a_heroin_30() != null) {
				if (dto.getD3a_heroin_30() == 0) {
					patientData.setHeroin30any(0);
				} else if (dto.getD3a_heroin_30() > 0) {
					patientData.setHeroin30any(1);
				}
			}

			// methadone30any
			// if d4a_methadone_30=0 then methadone30any=0;
			// else if d4a_methadone_30>0 then methadone30any=1;
			// else methadone30any=.;
			if (dto.getD4a_methadone_30() != null) {
				if (dto.getD4a_methadone_30() == 0) {
					patientData.setMethadone30any(0);
				} else if (dto.getD4a_methadone_30() > 0) {
					patientData.setMethadone30any(1);
				}
			}

			// opiates30any
			// if d5a_opiates_30=0 then opiates30any=0;
			// else if d5a_opiates_30>0 then opiates30any=1;
			// else opiates30any=.;
			if (dto.getD5a_opiates_30() != null) {
				if (dto.getD5a_opiates_30() == 0) {
					patientData.setOpiates30any(0);
				} else if (dto.getD5a_opiates_30() > 0) {
					patientData.setOpiates30any(1);
				}
			}

			// if heroin30any=1 and methadone30any=0 and opiates30any=0 then
			// opicovd1=1;
			// if heroin30any=1 and methadone30any=0 and opiates30any=0 then
			// opicovd2=0;
			// if heroin30any=1 and methadone30any=0 and opiates30any=0 then
			// opicovd3=0;
			if (patientData.getHeroin30any() != null && patientData.getMethadone30any() != null && patientData.getOpiates30any() != null) {
				if (patientData.getHeroin30any() == 1 && patientData.getMethadone30any() == 0 && patientData.getOpiates30any() == 0) {
					patientData.setOpicovd1(1);
					patientData.setOpicovd2(0);
					patientData.setOpicovd3(0);
				}

				// if heroin30any=0 and (methadone30any=1 or opiates30any=1)
				// then
				// opicovd1=0;
				// if heroin30any=0 and (methadone30any=1 or opiates30any=1)
				// then
				// opicovd2=1;
				// if heroin30any=0 and (methadone30any=1 or opiates30any=1)
				// then
				// opicovd3=0;
				if (patientData.getHeroin30any() == 0 && (patientData.getMethadone30any() == 1 || patientData.getOpiates30any() == 1)) {
					patientData.setOpicovd1(0);
					patientData.setOpicovd2(1);
					patientData.setOpicovd3(0);
				}

				// if heroin30any=1 and (methadone30any=1 or opiates30any=1)
				// then
				// opicovd1=0;
				// if heroin30any=1 and (methadone30any=1 or opiates30any=1)
				// then
				// opicovd2=0;
				// if heroin30any=1 and (methadone30any=1 or opiates30any=1)
				// then
				// opicovd3=1;
				if (patientData.getHeroin30any() == 1 && (patientData.getMethadone30any() == 1 || patientData.getOpiates30any() == 1)) {
					patientData.setOpicovd1(0);
					patientData.setOpicovd2(0);
					patientData.setOpicovd3(1);
				}
			}
		}

		// only for TN
		if (dto.getState() != null &&  dto.getState().equals("TN")) {

			// if e19_days_employment_problem=""0"" then employmentprob=0;
			// else if e19_days_employment_problem=""NULL"" then
			// employmentprob=.;
			// else if e19_days_employment_problem=""-98"" then
			// employmentprob=.;
			// else if e19_days_employment_problem=""-99"" then
			// employmentprob=.;
			// else employmentprob=1;

			if (dto.getE19_days_employment_problem() != null) {
				if (dto.getE19_days_employment_problem() == 0) {
					patientData.setEmploymentprob(0);
				} else if (dto.getE19_days_employment_problem() == -98 || dto.getE19_days_employment_problem() == -99) {
					patientData.setEmploymentprob(Constant.NOT_AVAILABLE);
				} else {
					patientData.setEmploymentprob(1);
				}
			}

			// if d2a_intoxication_30=""0"" then intoxication30=0;
			// else if d2a_intoxication_30=""NULL"" then intoxication30=.;
			// else if d2a_intoxication_30=""-99"" then intoxication30=.;
			// else if d2a_intoxication_30=""-98"" then intoxication30=.;
			// else intoxication30=1;
			if (dto.getD2a_intoxication_30() != null) {
				if (dto.getD2a_intoxication_30() == 0) {
					patientData.setIntoxication30(0);
				} else if (dto.getD2a_intoxication_30() == null || dto.getD2a_intoxication_30() == -99 || dto.getD2a_intoxication_30() == -98) {
					patientData.setIntoxication30(Constant.NOT_AVAILABLE);
				} else {
					patientData.setIntoxication30(1);
				}
			}

			// heroin30r
			// if d3a_heroin_30=""0"" then heroin30r=0;
			// else if d3a_heroin_30=""NULL"" then heroin30r=.;
			// else if d3a_heroin_30=""-99"" then heroin30r=.;
			// else if d3a_heroin_30=""-98"" then heroin30r=.;
			// else heroin30r=1;
			if (dto.getD3a_heroin_30() != null) {
				if (dto.getD3a_heroin_30() == 0) {
					patientData.setHeroin30r(0);
				} else if (dto.getD3a_heroin_30() == null || dto.getD3a_heroin_30() == -99 || dto.getD3a_heroin_30() == -98) {
					patientData.setHeroin30r(Constant.NOT_AVAILABLE);
				} else {
					patientData.setHeroin30r(1);
				}
			}

			// methadone30r
			// if d4a_methadone_30=""0"" then methadone30r=0;
			// else if d4a_methadone_30=""NULL"" then methadone30r=.;
			// else if d4a_methadone_30=""-99"" then methadone30r=.;
			// else if d4a_methadone_30=""-98"" then methadone30r=.;
			// else methadone30r=1;
			if (dto.getD4a_methadone_30() != null) {
				if (dto.getD4a_methadone_30() == 0) {
					patientData.setMethadone30r(0);
				} else if (dto.getD4a_methadone_30() == null || dto.getD4a_methadone_30() == -99 || dto.getD4a_methadone_30() == -98) {
					patientData.setMethadone30r(Constant.NOT_AVAILABLE);
				} else {
					patientData.setMethadone30r(1);
				}
			}

			// opiates30r
			// if d5a_opiates_30=""0"" then opiates30r=0;
			// else if d5a_opiates_30=""NULL"" then opiates30r=.;
			// else if d5a_opiates_30=""-99"" then opiates30r=.;
			// else if d5a_opiates_30=""-98"" then opiates30r=.;
			// else opiates30r=1;
			if (dto.getD5a_opiates_30() != null) {
				if (dto.getD5a_opiates_30() == 0) {
					patientData.setOpiates30r(0);
				} else if (dto.getD5a_opiates_30() == null || dto.getD5a_opiates_30() == -99 || dto.getD5a_opiates_30() == -98) {
					patientData.setOpiates30r(Constant.NOT_AVAILABLE);
				} else {
					patientData.setOpiates30r(1);
				}
			}

			// if heroin30r=0 then heroin30=0;
			// else if heroin30r>0 and (d3c_route_of_intake_type_code="IJ" or
			// d3c_route_of_intake_type_code="IT"
			// or d3c_route_of_intake_type_code="NJ") then heroin30=1;
			// else if heroin30r>0 then heroin30=0;
			// else heroin30=.;
			//
			// if methadone30r=0 then methadone30=0;
			// else if methadone30r>0 and (d4c_route_of_intake_type_code="IJ" or
			// d4c_route_of_intake_type_code="IT"
			// or d4c_route_of_intake_type_code="NJ") then methadone30=1;
			// else if methadone30r>0 then methadone30=0;
			// else methadone30=.;
			//
			// if opiates30r=0 then opiates30=0;
			// else if opiates30r>0 and (d5c_route_of_intake_type_code="IJ" or
			// d5c_route_of_intake_type_code="IT"
			// or d5c_route_of_intake_type_code="NJ") then opiates30=1;
			// else if opiates30r>0 then opiates30=0;
			// else opiates30=.;
			//
			// if heroin30r=1 and methadone30r=0 and opiates30r=0 then
			// opicovd1=1;
			// if heroin30r=1 and methadone30r=0 and opiates30r=0 then
			// opicovd2=0;
			// if heroin30r=1 and methadone30r=0 and opiates30r=0 then
			// opicovd3=0;
			if (patientData.getHeroin30r() != null && patientData.getMethadone30r() != null && patientData.getOpiates30r() != null) {
				if (patientData.getHeroin30r() == 1 && patientData.getMethadone30r() == 0 && patientData.getOpiates30r() == 0) {
					patientData.setOpicovd1(1);
					patientData.setOpicovd2(0);
					patientData.setOpicovd3(0);
				}

				// if heroin30r=0 and (methadone30r=1 or opiates30r=1) then
				// opicovd1=0;
				// if heroin30r=0 and (methadone30r=1 or opiates30r=1) then
				// opicovd2=1;
				// if heroin30r=0 and (methadone30r=1 or opiates30r=1) then
				// opicovd3=0;
				if (patientData.getHeroin30r() == 0 && (patientData.getMethadone30r() == 1 || patientData.getOpiates30r() == 1)) {
					patientData.setOpicovd1(0);
					patientData.setOpicovd2(1);
					patientData.setOpicovd3(0);
				}

				// if heroin30r=1 and (methadone30r=1 or opiates30r=1) then
				// opicovd1=0;
				// if heroin30r=1 and (methadone30r=1 or opiates30r=1) then
				// opicovd2=0;
				// if heroin30r=1 and (methadone30r=1 or opiates30r=1) then
				// opicovd3=1;
				if (patientData.getHeroin30r() == 1 && (patientData.getMethadone30r() == 1 || patientData.getOpiates30r() == 1)) {
					patientData.setOpicovd1(0);
					patientData.setOpicovd2(1);
					patientData.setOpicovd3(0);
				}
			}

		}

		// if (d3c_route_of_intake_type_code=""IJ"" or
		// d3c_route_of_intake_type_code=""IT"" or
		// d3c_route_of_intake_type_code=""NJ"") or
		// (d4c_route_of_intake_type_code=""IJ"" or
		// d4c_route_of_intake_type_code=""IT"" or
		// d4c_route_of_intake_type_code=""NJ"") or
		// (d5c_route_of_intake_type_code=""IJ"" or
		// d5c_route_of_intake_type_code=""IT"" or
		// d5c_route_of_intake_type_code=""NJ"") then route=1;
		// else route=0;

		if (dto.getD3c_route_of_intake_type_Code() != null || dto.getD4c_route_of_intake_type_Code() != null
				|| dto.getD5c_route_of_intake_type_Code() != null) {
			if (ifEqualsIJ_IT_NJ(dto.getD3c_route_of_intake_type_Code())
					|| ifEqualsIJ_IT_NJ(dto.getD4c_route_of_intake_type_Code())
					|| ifEqualsIJ_IT_NJ(dto.getD5c_route_of_intake_type_Code())) {
				patientData.setRoute(1);
			} else {
				patientData.setRoute(0);
			}
		}

		// age
		if (dto.getAge() != null) {
			patientData.setAge(dto.getAge());
		}

		// state
		if (dto.getState() != null) {
			patientData.setState(dto.getState());
		}

	}

	private static boolean equalsYES(String str) {
		return str != null && str.equals(YES);
	}

	private static boolean equalsNO(String str) {
		return str != null && str.equals(NO);
	}

	private boolean ifEqualsIJ_IT_NJ(String source) {
		return source != null && source.equals("IJ") || source.equals("IT") || source.equals("NJ");
	}

	private Integer checkResponse(String response) {
		Integer value = Constant.NOT_AVAILABLE;
		if (response != null) {
			if (response.equals(NO) || response.contains("NOT")) {
				value = 0;
			} else if (response.contains(YES)) {
				value = 1;
			}
		}
		return value;
	}

}
