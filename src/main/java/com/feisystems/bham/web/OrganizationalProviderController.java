package com.feisystems.bham.web;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feisystems.bham.service.AssessmentDto;
import com.feisystems.bham.service.OrganizationalProviderDto;
import com.feisystems.bham.service.OrganizationalProviderService;
import com.feisystems.bham.util.Constant;

@Controller
@RequestMapping("/organizationalproviders")
public class OrganizationalProviderController {
	
	@Autowired
    OrganizationalProviderService organizationalProviderService;

	@RequestMapping(method = RequestMethod.GET, headers = Constant.ACCEPT_APPLICATION_JSON)
    @ResponseBody
    public OrganizationalProviderDto showJson() {
        OrganizationalProviderDto organizationalProviderDto = organizationalProviderService.findOrganizationalProvider();
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON_CHARSET_UTF8);
        return organizationalProviderDto;
    }

	@RequestMapping(headers = Constant.ACCEPT_APPLICATION_JSON)
    @ResponseBody
    public List<OrganizationalProviderDto> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON_CHARSET_UTF8);
        List<OrganizationalProviderDto> result = organizationalProviderService.findAllOrganizationalProviders();
        return result;
    }
	
	@RequestMapping(value = "/assessment", method = RequestMethod.GET, produces = Constant.APPLICATION_JSON)
	@ResponseBody
	public AssessmentDto retrieveAssessment() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON_CHARSET_UTF8);
        AssessmentDto dto = new AssessmentDto();
		dto.setAssessment(organizationalProviderService.findOrganizationalProvider().getAssessment());
		return dto;
	}

	@RequestMapping(method = RequestMethod.POST, headers = Constant.ACCEPT_APPLICATION_JSON)
    public ResponseEntity<String> updateFromJson(@RequestBody OrganizationalProviderDto organizationalProviderDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON);
        if (organizationalProviderService.updateOrganizationalProvider(organizationalProviderDto) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

}
