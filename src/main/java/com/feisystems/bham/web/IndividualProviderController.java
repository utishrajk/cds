package com.feisystems.bham.web;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.feisystems.bham.service.IndividualProviderDto;
import com.feisystems.bham.service.IndividualProviderService;
import com.feisystems.bham.service.IndividualProviderTrainingDto;
import com.feisystems.bham.service.reference.AdministrativeGenderCodeService;
import com.feisystems.bham.util.Constant;

@Controller
@RequestMapping("/individualproviders")
public class IndividualProviderController {

    @Autowired
    IndividualProviderService individualProviderService;

    @Autowired
    AdministrativeGenderCodeService administrativeGenderCodeService;

    @Autowired
	private MessageSource messageSource;
    
    @RequestMapping(method = RequestMethod.GET, headers = Constant.ACCEPT_APPLICATION_JSON)
    @ResponseBody
    public IndividualProviderDto showJson() {
    	IndividualProviderDto individualProviderDto = individualProviderService.findByLoggedInUsername();
    	
        individualProviderDto.setCredential("");
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON_CHARSET_UTF8);
        return individualProviderDto;
    }

    @RequestMapping(method = RequestMethod.POST, headers = Constant.ACCEPT_APPLICATION_JSON)
    public ResponseEntity<String> updateFromJson(@RequestBody IndividualProviderDto individualProviderDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON);
        isValidRequest(individualProviderDto);
        if (individualProviderService.updateByLoggedInUser(individualProviderDto) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

    public void isValidRequest(IndividualProviderDto dto) {
        if (dto.getFirstName() == null || dto.getLastName() == null) {
            throw new IllegalArgumentException("Both FirstName and LastName are Required");
        }
    }
    
    /**
	 * POST /rest/account -> update the current user information.
	 */
	@RequestMapping(value = "/training", method = RequestMethod.POST, produces = Constant.APPLICATION_JSON)
	public  ResponseEntity<String> updateTrainingVideoChoice(@RequestBody IndividualProviderTrainingDto individualProviderTrainingDto) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON);
        
        if (individualProviderService.updateTrainingVideoChoice(individualProviderTrainingDto) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);        
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorInfo handleIllegalArgumentException(HttpServletRequest req, IllegalArgumentException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON_CHARSET_UTF8);
		Locale locale = LocaleContextHolder.getLocale();
 
		String errorMessage = messageSource.getMessage("error_resourcenotfound_title", null, locale);
		errorMessage += ex.getMessage();

		String errorURL = req.getRequestURL().toString();

		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
        return errorInfo;
	}


}
