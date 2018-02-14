package com.mercury.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercury.models.Routes;
import com.mercury.models.Schedules;
import com.mercury.models.OutRest;
import com.mercury.models.Legs;
import com.mercury.common.TimeFlghts;
import com.mercury.models.Day;
import com.mercury.models.Flight;

@Service
public class InterconectionsService  {
	
	@Autowired
	private Microservices microservices;
	
	@Autowired
	private TimeFlghts timeFlghts;
	
	public List <OutRest> getInterconectionsRoutes (String departure,String arrival ,String departureDateTime, String arrivalDateTime  ) {

		List<Routes[]> listRoutes =microservices.getRoutesAPI(departure,arrival);
		List <OutRest> list=this.procesRoute(listRoutes, departureDateTime, arrivalDateTime);
		return list;
    	
	}
	

	private List <OutRest> procesRoute( List<Routes[]> listRoutes,String departureDateTime, String arrivalDateTime ) {
		List<Schedules> scheduleth = new ArrayList<Schedules>() ;
		ArrayList<ArrayList<ArrayList<Schedules>>> schedulesMonth = new ArrayList<ArrayList<ArrayList<Schedules>>>();

		for(Routes[] element : listRoutes) {
			ArrayList<ArrayList<Schedules>> scheduleConv = new ArrayList<ArrayList<Schedules>>();
			if (element[1]== null) {
				scheduleConv.add(microservices.getSchedulesAPI(element[0].getAirportFrom(), element[0].getAirportTo(), departureDateTime, arrivalDateTime));

			} else {
				scheduleConv.add(microservices.getSchedulesAPI(element[0].getAirportFrom(), element[0].getAirportTo(), departureDateTime, arrivalDateTime));
				scheduleConv.add(microservices.getSchedulesAPI(element[1].getAirportFrom(), element[1].getAirportTo(), departureDateTime, arrivalDateTime));
			}
			schedulesMonth.add(scheduleConv);
		}
	
		List <OutRest> outRest =  formatDataOutput(schedulesMonth);
		return outRest;
	}
	
	private List <OutRest> formatDataOutput (ArrayList<ArrayList<ArrayList<Schedules>>> schedulesMonth) {
		List <OutRest> outRest = new ArrayList<OutRest>();
	
		
		for (ArrayList<ArrayList<Schedules>> scheduleConv : schedulesMonth ) {
			
			System.out.println("scheduleConv.size()" +scheduleConv.size());
			Integer stops = scheduleConv.size();
			if (stops<2) {
				for (ArrayList<Schedules> monthElement :scheduleConv) {
					for (Schedules elemnt : monthElement) {
						
						Long month = elemnt.getMonth();
						Long year = elemnt.getYear();
						Integer departureOffset = elemnt.getDepartureOffset();
						Integer arrivalOffset = elemnt.getArrivalOffset();
						for(Day days :elemnt.getDays()) {
							Long day = days.getDay();
							OutRest out = new OutRest();
				
							for(Flight flight :  days.getFlights()) {
								List <Legs> listLegs =  new ArrayList<Legs>();
								Legs legs =new Legs();
								out.setStops(stops);
								legs.setArrivalAirport(flight.getArrival());
								legs.setDepartureAirport(flight.getDeparture());
								
								legs.setDepartureDateTime(timeFlghts.setDateTimeFormat(year, month, day, flight.getDepartureTime(),departureOffset));
								legs.setArrivalDateTime(timeFlghts.setDateTimeFormat(year, month, day, flight.getArrivalTime(),arrivalOffset));
								listLegs.add(legs);
								out.setLegs(listLegs);
							}
							outRest.add(out);
						}

		
					}

				}

			} else {
				ArrayList<Schedules> tramo1 =scheduleConv.get(0);
				ArrayList<Schedules> tramo2 =scheduleConv.get(1);
			}
		}
		return outRest;
	}

}
