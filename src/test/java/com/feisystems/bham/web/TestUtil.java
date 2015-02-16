package com.feisystems.bham.web;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class TestUtil {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    private static final String CHARACTER = "a";

	/**
	* Convert object to JSON String 
	* @param object
	* @return
	* @throws JsonGenerationException
	* @throws JsonMappingException
	* @throws IOException
	*/
	public static byte[] convertObjectToJsonBytes(Object object) 
		throws JsonGenerationException, JsonMappingException, IOException {
	    ObjectMapper jsonMapper = new ObjectMapper();
	    return jsonMapper.writeValueAsString(object).getBytes();
	}

    public static String createStringWithLength(int length) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < length; index++) {
            builder.append(CHARACTER);
        }
        return builder.toString();
    }
}
