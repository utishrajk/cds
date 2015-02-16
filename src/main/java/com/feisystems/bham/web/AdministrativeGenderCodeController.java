package com.feisystems.bham.web;

import com.feisystems.bham.domain.reference.AdministrativeGenderCode;
import com.feisystems.bham.service.reference.AdministrativeGenderCodeService;
import com.feisystems.bham.util.Constant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/administrativegendercodes")
public class AdministrativeGenderCodeController {

	@Autowired
    AdministrativeGenderCodeService administrativeGenderCodeService;

	@RequestMapping(headers = Constant.ACCEPT_APPLICATION_JSON)
    @ResponseBody
    public List<AdministrativeGenderCode> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON_CHARSET_UTF8);
        List<AdministrativeGenderCode> result = administrativeGenderCodeService.findAllAdministrativeGenderCodes();
        return result;
    }

}
