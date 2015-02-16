package com.feisystems.bham.service;

import org.springframework.stereotype.Component;

import com.feisystems.bham.domain.Activity;
import com.feisystems.bham.infrastructure.DtoToDomainEntityMapper;

@Component
public class ActivityDtoToActivityMapper implements
		DtoToDomainEntityMapper<ActivityDto, Activity> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.feisystems.bham.infrastructure.DtoToDomainEntityMapper#map(java.lang
	 * .Object)
	 */
	@Override
	public Activity map(ActivityDto activityDto) {
		Activity activity = new Activity();

		if(activityDto != null){
			activity.setAction(activityDto.getMethodName());
			activity.setDescription(activityDto.getDescription());
			activity.setTimestamp(activityDto.getTimestamp());
			activity.setUsername(activityDto.getUsername());
			activity.setIpAddress(activityDto.getIpAddress());
		}

		return activity;
	}

}
