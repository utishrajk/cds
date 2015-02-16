package com.feisystems.bham.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.feisystems.bham.service.AccessDto;
import com.feisystems.bham.service.reference.AccessService;
import com.feisystems.bham.util.Constant;


@RestController
@RequestMapping("/access")
public class AccessController {
	
	@Autowired
	AccessService accessService;
	
	@RequestMapping(method = RequestMethod.POST, headers = Constant.ACCEPT_APPLICATION_JSON)
    public ResponseEntity<String> save(@RequestBody AccessDto accessDto) {
		accessService.save(accessDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON);
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
}
