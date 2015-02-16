package com.feisystems.bham.config;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.feisystems.bham.security.SecurityUtils;
import com.feisystems.bham.service.ActivityDto;
import com.feisystems.bham.service.IndividualProviderActivityService;


/**
 * Aspect for persisting user activity execution of controller Spring components into database 
 */
@Aspect
@PropertySource({"classpath:/properties/useractivity.properties"})
@Configuration
public class IndividualProviderActivityAspect {
	
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(com.feisystems.bham.web.*)")
    public void activityPointcut() {}
    
    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controllerBean() {}
    
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerRestBean() {}

    @Pointcut("execution(* *(..))")
    public void methodPointcut() {}
    
    @Pointcut("!within(com.feisystems.bham.web..*CodeController)")
    public void notInLookups() {}
    
    @Autowired
    protected Environment environment;
    
	@Autowired
	IndividualProviderActivityService activityService;

    @AfterReturning("(controllerBean() || controllerRestBean()) && methodPointcut() && notInLookups()")
    public void activityAfter(JoinPoint joinPoint) throws Throwable {
    	
    	ActivityDto activityDto = new ActivityDto();
    	try {

            	log.debug("Individual Provider Activity Aspect");
            	
            	activityDto.setTimestamp(new Date());
            	activityDto.setMethodName(joinPoint.getSignature().toShortString());
            	if (SecurityUtils.isAuthenticated()) {
            		activityDto.setUsername(SecurityUtils.getCurrentLogin());
            	} else {
            		activityDto.setUsername("Anonymous");
            	}
            	
            	activityDto.setIpAddress(SecurityUtils.getIpAddress());
            	activityDto.setDescription(environment.getProperty(joinPoint.getSignature().toShortString()));
            	activityService.saveActivity(activityDto);

//     			Using Java Properties we can have Dynamic Place holders in Properties file filled using MessageFormat     	
//            	Properties prop = new Properties();
//            	InputStream input = null;
    //
//              String filename = "/properties/useractivity.properties";
//        		input = getClass().getClassLoader().getResourceAsStream(filename);
    //
//                //load a properties file from class path, inside static method
//          		prop.load(input);
//          		System.out.println(prop.getProperty(joinPoint.getSignature().toShortString()));
//          		System.out.println(MessageFormat.format((String) prop.get(joinPoint.getSignature().toShortString()), "First", "Last"));
//     				Ex: PatientController.listJson()=Get list of all Patients {0} {1}        	

        } catch (IllegalArgumentException e) {
            throw e;
        }

    }
}
