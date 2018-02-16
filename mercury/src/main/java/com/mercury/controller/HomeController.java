package com.mercury.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mercury.common.Validations;
import com.mercury.models.ErrorOut;
import com.mercury.models.OutRest;
import com.mercury.service.InterconectionsService;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired	
	private InterconectionsService interconectionsService;
	
	@Autowired	
	private Validations validations;
	
	@ResponseBody
	@RequestMapping(value = "/interconnections", method = RequestMethod.GET, produces = "application/json" ) 
	public Object home(HttpServletRequest request) {
		
		String departure = request.getParameter("departure");
		String arrival = request.getParameter("arrival");
		String departureDateTime = request.getParameter("departureDateTime");
		String arrivalDateTime = request.getParameter("arrivalDateTime");
			
		if ( !(validations.isIATACode(departure) && validations.isIATACode(arrival)	&& validations.isCorrectDate( departureDateTime,arrivalDateTime) ) ) {
			ErrorOut failure = new ErrorOut();
			failure.setError("Sorry, input parameter incorrect ");
			return failure;
		} 
		
		List <OutRest> out =interconectionsService.getInterconectionsRoutes(departure, arrival, departureDateTime, arrivalDateTime);
		if ( out.isEmpty()) {
			ErrorOut failure = new ErrorOut();
			failure.setError("Sorry, data not found");
			return failure;
		} 
		return out;
	}

}
