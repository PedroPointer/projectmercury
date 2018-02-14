package com.mercury.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mercury.models.OutRest;
import com.mercury.models.Schedules;
import com.mercury.service.InterconectionsService;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	
	@Autowired	
	private InterconectionsService interconectionsService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@ResponseBody
	@RequestMapping(value = "/home", method = RequestMethod.GET, produces = "application/json" ) 
	public List <OutRest> home(HttpServletRequest request) {

		//http://localhost:8080/somevalidcontext/interconnections?departure=DUB&arrival=WRO&departureDateTime=2018-03-01T07:00&arrivalDateTime=2018-03-03T21:00
		
		String departure = request.getParameter("departure");
		String arrival = request.getParameter("arrival");
		String departureDateTime = request.getParameter("departureDateTime");
		String arrivalDateTime = request.getParameter("arrivalDateTime");
		
//		departureDateTime="2018-02-18T07:00";
//		arrivalDateTime="2018-10-04T07:00";
//		departure="lis";
//		arrival="LUX";
		
		List <OutRest> out =interconectionsService.getInterconectionsRoutes(departure, arrival, departureDateTime, arrivalDateTime);
		
		//https://github.com/opentraveldata/opentraveldata/tree/master/opentraveldata/por_in_schedule
		
		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
	
		
		return out;
	}
	
	
	
	@RequestMapping(value ="/hola", method = RequestMethod.GET) 
	@ResponseBody
	public    Object hola(HttpServletRequest request,@RequestParam(value="name", defaultValue="World") String name){
		
		return "tyrtytyryrtryryryyrtrtyryt";
		
	}
	
	
	
	
}
