package com.feisystems.bham.service;

import java.util.List;

public interface IndividualProviderActivityService {
	
	public abstract void saveActivity(ActivityDto activityDto);
	
	public List<ActivityDto> findActivitiesByIndividualProviderId(Long id);

}
