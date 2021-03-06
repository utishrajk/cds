package com.feisystems.bham.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feisystems.bham.service.LookupDto;
import com.feisystems.bham.service.reference.EpisodeCodeService;
import com.feisystems.bham.util.Constant;

@Controller
@RequestMapping("/episodecodes")
public class EpisodeCodeController {
	@Autowired
    EpisodeCodeService episodeCodeService;

	@RequestMapping(headers = Constant.ACCEPT_APPLICATION_JSON)
    @ResponseBody
    public List<LookupDto> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON_CHARSET_UTF8);
        List<LookupDto> result = episodeCodeService.findAllEpisodeCodes();
        return result;
    }
}
