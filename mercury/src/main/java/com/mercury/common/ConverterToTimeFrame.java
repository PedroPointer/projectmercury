package com.mercury.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercury.models.Day;
import com.mercury.models.Flight;
import com.mercury.models.Legs;
import com.mercury.models.OutRest;
import com.mercury.models.Schedules;

@Component
public class ConverterToTimeFrame {
	
	@Autowired
	private TimeFlghts timeFlghts;
	
	public List<OutRest> converterAdFilter( ArrayList<Schedules> monthElement ,Integer stops ,Date dateDeparture, Date dateArrival ) {
		List <OutRest> listOutRest = new ArrayList<OutRest>();
		try {
			for (Schedules elemnt : monthElement) {

				Long month = elemnt.getMonth();
				Long year = elemnt.getYear();
				Integer departureOffset = elemnt.getDepartureOffset();
				Integer arrivalOffset = elemnt.getArrivalOffset();
				for(Day days :elemnt.getDays()) {
					
					Integer day = days.getDay().intValue();
					OutRest out = new OutRest();
		
					for(Flight flight :  days.getFlights()) {
						boolean save = true;
						List <Legs> listLegs =  new ArrayList<Legs>();
						Legs legs =new Legs();
						out.setStops(stops);
						legs.setArrivalAirport(flight.getArrival());
						legs.setDepartureAirport(flight.getDeparture());
						Date dateFlightDeparture =timeFlghts.setDateByFligth(year, month, day, flight.getDepartureTime(),departureOffset);
						Date dateFlightArrival = timeFlghts.setDateByFligth(year, month, day, flight.getArrivalTime(),arrivalOffset);
						legs.setDepartureDateTime(timeFlghts.setOutFormatDate(dateFlightDeparture));
						legs.setArrivalDateTime(timeFlghts.setOutFormatDate(dateFlightArrival));
						listLegs.add(legs);

						if ( dateDeparture.compareTo(dateFlightDeparture) >=0) {
								save = false;
							} 
						if (dateArrival.compareTo(dateFlightArrival) <=0) {
								save = false;
							} 
						if (save) {
							out.setLegs(listLegs);
						}
					}
					if (out.getLegs()!=null) {
						listOutRest.add(out);
					} 
				}
			}
		}catch(Exception e) {
//		e.printStackTrace();		
		}
		return listOutRest;
	}

	public List <OutRest> unionScaleAndOrder( List <OutRest> outRestEscale1,List <OutRest> outRestEscale2  ) {
		List <OutRest> outRest = new ArrayList<OutRest>();
			
		for (OutRest outRest1 : outRestEscale1) {
			for (Legs legs : outRest1.getLegs()) {
					Legs legs1 = new Legs();
					legs1.setArrivalAirport(legs.getArrivalAirport());
					legs1.setArrivalDateTime(legs.getArrivalDateTime());
					legs1.setDepartureAirport(legs.getDepartureAirport());
					legs1.setDepartureDateTime(legs.getDepartureDateTime());

					Legs legs2 =nextScaleFlight ( outRestEscale2 , legs.getArrivalDateTime());
					if (legs2!=null ){
						OutRest out = new OutRest();	
						List<Legs> listLegs = new ArrayList<Legs>();
		            	listLegs.add(legs1);
		            	listLegs.add(legs2);
						out.setStops(outRest1.getStops());
						out.setLegs(listLegs);
						outRest.add(out);
					}
				}
			}
		return outRest;
	}
	
	private Legs nextScaleFlight (List <OutRest> outRestEscale2 ,String dateFlightArrival1){

		for (OutRest outRest2 : outRestEscale2) {
			for (Legs legs : outRest2.getLegs()) {
				Legs legs2 = new Legs();
				legs2.setArrivalAirport(legs.getArrivalAirport());
				legs2.setArrivalDateTime(legs.getArrivalDateTime());
				legs2.setDepartureAirport(legs.getDepartureAirport());
				legs2.setDepartureDateTime(legs.getDepartureDateTime());
				
				if ( timeFlghts.inCorrecDiference(dateFlightArrival1, legs2.getDepartureDateTime())) {
					return legs2;
				}
			}
		}
		return null;
	}

}
