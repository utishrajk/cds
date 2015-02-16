package com.feisystems.bham.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feisystems.bham.domain.reference.CountryCode;
import com.feisystems.bham.service.reference.CountryCodeService;
import com.feisystems.bham.util.Constant;

@Controller
@RequestMapping("/countrycodes")
public class CountryCodeController {

	@Autowired
    CountryCodeService countryCodeService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = Constant.ACCEPT_APPLICATION_JSON)
    @ResponseBody
    public CountryCode showJson(@PathVariable("id") Long id) {
        CountryCode countryCode = countryCodeService.findCountryCode(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON_CHARSET_UTF8);
        return countryCode;
    }

	@RequestMapping(headers = Constant.ACCEPT_APPLICATION_JSON)
    @ResponseBody
    public List<CountryCode> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON_CHARSET_UTF8);
        List<CountryCode> result = countryCodeService.findAllCountryCodes();
        return result;
    }

	@RequestMapping(method = RequestMethod.POST, headers = Constant.ACCEPT_APPLICATION_JSON)
    public ResponseEntity<String> createFromJson(@RequestBody CountryCode countryCode) {
        countryCodeService.saveCountryCode(countryCode);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON);
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = Constant.ACCEPT_APPLICATION_JSON)
    public ResponseEntity<String> updateFromJson(@RequestBody CountryCode countryCode, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON);
        if (countryCodeService.updateCountryCode(countryCode) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = Constant.ACCEPT_APPLICATION_JSON)
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        CountryCode countryCode = countryCodeService.findCountryCode(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON);
        if (countryCode == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        countryCodeService.deleteCountryCode(countryCode);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
