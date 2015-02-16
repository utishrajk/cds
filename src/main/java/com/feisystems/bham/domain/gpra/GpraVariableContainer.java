package com.feisystems.bham.domain.gpra;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author himalay.majumdar
 *
 */
public final class GpraVariableContainer {
	
	@XmlElement
	public Anxiety_day_count anxiety_day_count;
	
	@XmlElement
	public Integer age;
	
	@XmlElement
	public Cd_gpra_ivw_gender_id cd_gpra_ivw_gender_id;
	
	@XmlElement
	public Cd_gpra_ivw_health_id cd_gpra_ivw_health_id;
	
	@XmlElement
	public Cd_gpra_ivw_psych_impact_id cd_gpra_ivw_psych_impact_id;
	
	@XmlElement
	public Codeine_cd_gpra_ivw_route_id codeine_cd_gpra_ivw_route_id;
	
	@XmlElement
	public Codeine_day_count codeine_day_count;
	
	@XmlElement
	public Crime_Count crime_Count;
	
	@XmlElement
	public Darvon_cd_gpra_ivw_route_id darvon_cd_gpra_ivw_route_id;

	@XmlElement
	public Darvon_day_count darvon_day_count;
	
	@XmlElement
	public Demerol_cd_gpra_ivw_route_id demerol_cd_gpra_ivw_route_id;
	
	@XmlElement
	public Demerol_day_count demerol_day_count;
	
	@XmlElement
	public Depression_day_count depression_day_count;
	
	@XmlElement
	public Diluadid_cd_gpra_ivw_route_id diluadid_cd_gpra_ivw_route_id;
	
	@XmlElement
	public Diluadid_day_count diluadid_day_count;
	
	@XmlElement
	public Er_physical_ind er_physical_ind;
	
	@XmlElement
	public Heroin_cd_gpra_ivw_route_id heroin_cd_gpra_ivw_route_id;
	
	@XmlElement
	public Heroin_day_count heroin_day_count;
	
	@XmlElement
	public Inpat_physical_ind_1 inpat_physical_ind_1;
	
	@XmlElement
	public Methadone_cd_gpra_ivw_route_id methadone_cd_gpra_ivw_route_id;
	
	@XmlElement
	public Methadone_day_count methadone_day_count;
	
	@XmlElement
	public Morphine_cd_gpra_ivw_route_id morphine_cd_gpra_ivw_route_id;
	
	@XmlElement
	public Morphine_day_count morphine_day_count;
	
	@XmlElement
	public Outpat_physical_ind outpat_physical_ind;
	 
	@XmlElement
	public Oxyco_cd_gpra_ivw_route_id oxyco_cd_gpra_ivw_route_id;
	
	@XmlElement
	public Oxyco_day_count oxyco_day_count;
	
	@XmlElement
	public Percocet_cd_gpra_ivw_route_id percocet_cd_gpra_ivw_route_id;
	
	@XmlElement
	public Percocet_day_count percocet_day_count;
	
	@XmlElement
	public Pregnant_ind1 pregnant_ind1;
	
	@XmlElement
	public Psychol_emot_med_day_count psychol_emot_med_day_count;
	
	@XmlElement
	public Suicide_day_count suicide_day_count;
	
	@XmlElement
	public Tylenol_cd_gpra_ivw_route_id tylenol_cd_gpra_ivw_route_id;
	
	@XmlElement
	public Tylenol_day_count tylenol_day_count;
	
	@XmlElement
	public Use_alc_day_count use_alc_day_count;
	
	@XmlElement
	public Violent_behavior_day_count violent_behavior_day_count;
	
	@XmlElement
	public GpraVariableContainer witsVariableContainer;
	
	@XmlElement
	public PsycholEmotMedication emotMedication;
}
