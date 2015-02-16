package com.feisystems.bham.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsernameServiceImpl implements UsernameService {

	@Override
	public String retrieveUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
