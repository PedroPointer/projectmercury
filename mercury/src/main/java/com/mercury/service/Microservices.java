package com.mercury.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercury.common.Parse;
import com.mercury.common.RequestHttp;
import com.mercury.common.TimeFlghts;
import com.mercury.models.Routes;
import com.mercury.models.Schedules;

@Component
public class Microservices extends RequestHttp {

	@Autowired
	private Parse parse;
	
	@Autowired
	private TimeFlghts timeFlghts;
	
	private static String ROUTES_API = "https://api.ryanair.com/core/3/routes";
	private static String SCHEDULES_API ="https://api.ryanair.com/timetable/3/schedules/";
	private static String TIMEZONE_API="https://airports-api.s3-us-west-2.amazonaws.com/iata/" ; //atl.json";
	
	public  List<Routes[]>  getRoutesAPI(String departure, String arrival) {
//		System.out.println("getRoutesAPI");
		String reponseRotesApi =null;
		try {
			 reponseRotesApi = this.callURL(ROUTES_API);
		} catch (IOException e) {
//			e.printStackTrace();
		}
		return parse.loadRoutesApiByJSON(reponseRotesApi, departure,arrival);
	}
	
	public ArrayList<Schedules> getSchedulesAPI(String departure,String arrival, String departureDateTime, String arrivalDateTime ) {

		ArrayList<Schedules> Schedule = new ArrayList<Schedules>();
		Integer departureOffset = ( this.getTimezonoAPI(departure)).intValue();
		Integer arrivalOffset = this.getTimezonoAPI(arrival).intValue();
		///{departure}/{arrival}/years/{year}/months/{month}
		Date currentDate = timeFlghts.FromStringAddOffset(departureDateTime, departureOffset);
		Date arrivalDate = timeFlghts.FromStringAddOffset(arrivalDateTime, arrivalOffset);
		
		boolean forEnd = false;
		while (forEnd!=true) {
			currentDate=timeFlghts.addMonthDate(currentDate,1);	
			Integer month =timeFlghts.getMonth(currentDate);
			Integer year =timeFlghts.getYear(currentDate);
			
//			System.out.println("mes "+month+ " año "+year ); 
			Schedules schedulesMonth =this.callSchedulesApi(departure, arrival, month, year, departureOffset, arrivalOffset);
			Schedule.add(schedulesMonth);
			if(currentDate.compareTo(arrivalDate) > 0) {
				forEnd=true;
			}
		}
	return Schedule;
	}
	
	private Schedules callSchedulesApi (String departure,String arrival,Integer month,Integer year, Integer departureDateTime, Integer arrivalDateTime) {
		Schedules schedulesMonth = null;
		try {
//			System.out.println(SCHEDULES_API + departure+"/"+arrival+"/years/"+year+"/months/"+month );
			String dataImput = this.callURL(SCHEDULES_API +departure+"/"+arrival+"/years/"+year+"/months/"+month );
			schedulesMonth =parse.loadSchedulesApiByJSON(dataImput,departure,arrival,year, departureDateTime,  arrivalDateTime);

		} catch (IOException e) {

		}	
		return schedulesMonth;
	}
	
	public Long getTimezonoAPI(String IATA){
		Long utcOffset = null;
		try {
			String impt = this.callURL(TIMEZONE_API +IATA.toLowerCase()+".json");
			utcOffset =parse.get_utcOffsetByAPIJSON(impt);
		} catch (IOException e) {
//			e.printStackTrace();
		}		
		return utcOffset;
	}
}
