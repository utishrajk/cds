package com.feisystems.bham.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feisystems.bham.service.ActivityDto;
import com.feisystems.bham.service.IndividualProviderActivityService;
import com.feisystems.bham.util.Constant;

@Controller
@RequestMapping("/individualprovidersactivity")
public class IndividualProviderActivityController {
	
	@Autowired
	IndividualProviderActivityService activityService;


	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = Constant.ACCEPT_APPLICATION_JSON)
    @ResponseBody
    public  List<ActivityDto> listJson(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON_CHARSET_UTF8);
        List<ActivityDto> result = activityService.findActivitiesByIndividualProviderId(id);
        return result;
    }

}
