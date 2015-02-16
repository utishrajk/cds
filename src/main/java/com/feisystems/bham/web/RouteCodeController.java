package com.feisystems.bham.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feisystems.bham.service.LookupDto;
import com.feisystems.bham.service.reference.RouteCodeService;
import com.feisystems.bham.util.Constant;

@Controller
@RequestMapping("/routecodes")
public class RouteCodeController {

	@Autowired
	RouteCodeService routeCodeService;

	@RequestMapping(headers = Constant.ACCEPT_APPLICATION_JSON)
	@ResponseBody
	public List<LookupDto> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON_CHARSET_UTF8);
		List<LookupDto> result = routeCodeService.findAllRouteCodes();
		return result;
	}

}
