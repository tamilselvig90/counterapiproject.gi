package com.counterapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.counterapi.model.Word;
import com.counterapi.service.CounterApiService;

@RestController
@RequestMapping("/counter-api")
public class CounterApiController {
    private static Logger logger = LoggerFactory.getLogger( CounterApiController.class );

    @Autowired
    private CounterApiService counterApiService;

    @RequestMapping(method = RequestMethod.POST, value = "/search")
    public Map getCount(@RequestBody Word word) {
        logger.info( "SearchText request received" );
        Map<String, List> countResult = new HashMap<String, List>();
        countResult.put( "counts", counterApiService.getCount( word.getSearchText() ) );
        logger.info( "SearchText request complete" );
        return countResult;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/top/{count}")
    public String getTopCount(@PathVariable int count, HttpServletResponse response) {
        logger.info( "GetTopCount request received" );
        response.setContentType( "text/csv" );
        logger.info( "GetTopCount request complete" );
        return counterApiService.getTopCount( count );
    }

    public void setCounterApiService(CounterApiService counterApiService) {
        this.counterApiService = counterApiService;
    }

}
