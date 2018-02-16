package com.mercury.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercury.common.ConverterToTimeFrame;
import com.mercury.common.TimeFlghts;
import com.mercury.models.OutRest;
import com.mercury.models.Routes;
import com.mercury.models.Schedules;

@Service
public class InterconectionsService  {
	
	@Autowired
	private Microservices microservices;
	
	@Autowired
	private TimeFlghts timeFlghts;
	
	@Autowired
	private ConverterToTimeFrame converterToTimeFrame;

	
	public List <OutRest> getInterconectionsRoutes (String departure,String arrival ,String departureDateTime, String arrivalDateTime  ) {

		List<Routes[]> listRoutes =microservices.getRoutesAPI(departure,arrival);
		List <OutRest> list=this.procesRoute(listRoutes, departureDateTime, arrivalDateTime);
		return list;
	}
	
	private List <OutRest> procesRoute( List<Routes[]> listRoutes,String departureDateTime, String arrivalDateTime ) {

		ArrayList<ArrayList<ArrayList<Schedules>>> schedulesFlight = new ArrayList<ArrayList<ArrayList<Schedules>>>();

		for(Routes[] element : listRoutes) {
			ArrayList<ArrayList<Schedules>> scheduleScale = new ArrayList<ArrayList<Schedules>>();
			if (element[1]== null) {
				scheduleScale.add(microservices.getSchedulesAPI(element[0].getAirportFrom(), element[0].getAirportTo(), departureDateTime, arrivalDateTime));

			} else {
				scheduleScale.add(microservices.getSchedulesAPI(element[0].getAirportFrom(), element[0].getAirportTo(), departureDateTime, arrivalDateTime));
				scheduleScale.add(microservices.getSchedulesAPI(element[1].getAirportFrom(), element[1].getAirportTo(), departureDateTime, arrivalDateTime));
			}
			schedulesFlight.add(scheduleScale);
		}
	
		List <OutRest> outRest =  formatDataOutput(schedulesFlight, departureDateTime, arrivalDateTime );
		return outRest;
	}
	
	private List <OutRest> formatDataOutput (ArrayList<ArrayList<ArrayList<Schedules>>> schedulesFlight,String departureDateTime, String arrivalDateTime ) {
		List <OutRest> outRest = new ArrayList<OutRest>();
		
		for (ArrayList<ArrayList<Schedules>> scheduleScale : schedulesFlight ) {
			Date dateDeparture= timeFlghts.FromString(departureDateTime);
			Date dateArrival= timeFlghts.FromString(arrivalDateTime);
			
//			System.out.println("scheduleScale.size()" +scheduleScale.size());
			Integer stops = scheduleScale.size();
			if (stops<2) {
				stops=0;
				outRest.addAll(converterToTimeFrame.converterAdFilter(scheduleScale.get(0), stops, dateDeparture, dateArrival) );
			} else {
				stops=1;
				List <OutRest> outRestEscale1 = converterToTimeFrame.converterAdFilter(scheduleScale.get(0), stops, dateDeparture, dateArrival);
				List <OutRest> outRestEscale2 = converterToTimeFrame.converterAdFilter(scheduleScale.get(1), stops, dateDeparture, dateArrival);
				outRest.addAll(converterToTimeFrame.unionScaleAndOrder(outRestEscale1, outRestEscale2));
			}
		}
		return outRest;
	}

}
