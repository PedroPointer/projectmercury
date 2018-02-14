package com.mercury.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.mercury.models.Day;
import com.mercury.models.Flight;
import com.mercury.models.Routes;
import com.mercury.models.Schedules;

@Component
public class Parse {

	@Autowired
	private TimeFlghts timeFlghts;
	
	 public  Schedules loadSchedulesApiByJSON(String dataImput,String departure,String arrival,Integer year,Integer departureOffset,Integer arrivalOffset) {
	    	Schedules schedulesMonth = new Schedules();
	    	
	    
	        try {
	        	JSONParser jsonParser = new JSONParser();
	        	JSONObject jsonObject = (JSONObject) jsonParser.parse(dataImput);
	        	Long month = (Long) jsonObject.get("month");
	        	List <Day> days = new ArrayList<Day>();
	        	
	        	JSONArray jsonArray = (JSONArray)  jsonObject.get("days");
	            Iterator i = jsonArray.iterator();
	            while (i.hasNext()) {
	            	JSONObject element = (JSONObject) i.next();
	            	Long dia =(Long) element.get("day");
	            
		            	Day day = new Day();
		            	List <Flight> flights = new  ArrayList<Flight>();
	
		                JSONArray jsonFlightsArray = (JSONArray) element.get("flights");
		                Iterator j = jsonFlightsArray.iterator();
		                while (j.hasNext()) {
		                	Flight flight = new Flight();
		                	JSONObject flightJson = (JSONObject) j.next();
			                String number = (String) flightJson.get("number");
			                String departureTime = (String) flightJson.get("departureTime");
			                String arrivalTime = (String) flightJson.get("arrivalTime");
	
			                flight.setArrival(arrival);
			                flight.setDeparture(departure);
			                flight.setNumber( new Integer(number)) ;
			                flight.setDepartureTime( departureTime); 
			                flight.setArrivalTime( arrivalTime); 
//			            	if (departure == null ||            			
//			            			( departure == true && (timeFlghts.compareHora(arrivalTime, timeFilter)>0) ) 
//			            			|| (departure == false && (timeFlghts.compareHora(arrivalTime, timeFilter)>0) ) ){
//				               
//			            	}
			            	 flights.add(flight);
			               // System.out.println( number+"----"+departureTime+"----"+arrivalTime); //-06:10----09:55
		                }
		            	day.setDay(dia);
		            	day.setFlights(flights);
		            	days.add(day);

	            }
	            schedulesMonth.setDepartureOffset(departureOffset);
	            schedulesMonth.setArrivalOffset(arrivalOffset);
	            schedulesMonth.setYear(new Long(year));
	            schedulesMonth.setMonth(month);
	            schedulesMonth.setDays(days);
	            
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
			return schedulesMonth;
	 }

     public  List<Routes[]> loadRoutesApiByJSON(String dataImput,String departure,String arrival) {
//    	List<Routes> listRoutes = new ArrayList<Routes>();
    	List<Routes> listDepartures = new ArrayList<Routes>();
    	List<Routes> listarrivals = new ArrayList<Routes>();

        try {
        	JSONParser jsonParser = new JSONParser();
        //	JSONObject jsonObject = (JSONObject) jsonParser.parse(dataImput);
        	JSONArray jsonArray = (JSONArray) jsonParser.parse(dataImput);

            Iterator i = jsonArray.iterator();
            while (i.hasNext()) {
                JSONObject element = (JSONObject) i.next();

                String airportFrom = (String) element.get("airportFrom");
                String airportTo = (String) element.get("airportTo");
                String connectingAirport = (String) element.get("connectingAirport");
                Boolean newRoute = (Boolean) element.get("newRoute");
                Boolean seasonalRoute = (Boolean) element.get("seasonalRoute");
                String group = (String) element.get("group");
                
                if ( ( airportTo.equalsIgnoreCase(arrival) ) && connectingAirport==null ) {
//                	if ( ( airportTo.equalsIgnoreCase(arrival) )  && ( connectingAirport!=null && connectingAirport.equalsIgnoreCase(NULL))) {
                    Routes routes = new Routes();
                    routes.setAirportFrom(airportFrom);
                    routes.setAirportTo(airportTo);
                    routes.setConnectingAirport(connectingAirport);
                    routes.setNewRoute(newRoute);
                    routes.setSeasonalRoute(seasonalRoute);
                    routes.setGroup(group);
//                    System.out.println( "listarrivals :" +airportFrom +" to "+airportTo +", conect " + connectingAirport +", seasonalRoute "+seasonalRoute) ;
                    listarrivals.add(routes);
                }
                if ( ( airportFrom.equalsIgnoreCase(departure) )  && connectingAirport==null) {
//                if ( ( airportFrom.equalsIgnoreCase(departure) )  && connectingAirport!=null && connectingAirport.equalsIgnoreCase(NULL)		) {
                    Routes routes = new Routes();
                    routes.setAirportFrom(airportFrom);
                    routes.setAirportTo(airportTo);
                    routes.setConnectingAirport(connectingAirport);
                    routes.setNewRoute(newRoute);
                    routes.setSeasonalRoute(seasonalRoute);
                    routes.setGroup(group);
//                    System.out.println(airportFrom +" to "+airportTo +", conect " + connectingAirport +", seasonalRoute "+seasonalRoute) ;
                    listDepartures.add(routes);
                }
            }
        } catch (Exception e) {
            return null;
        }
        System.out.println("____________________________");
        List<Routes[]> listRoutes = new ArrayList<Routes[]>();
        
        for ( Routes routeDep : listDepartures ) {
            for ( Routes rutaArri : listarrivals ) {
            	if (routeDep.getAirportTo().equals(rutaArri.getAirportFrom()) ) {
            		Routes[] element = new Routes[2] ;
            		 System.out.println( routeDep.getAirportFrom() +" to "+ routeDep.getAirportTo() +", conect " +  rutaArri.getAirportFrom()+" to "+ rutaArri.getAirportTo()  +" + 1 connectingAirport="+routeDep.getConnectingAirport()) ;
            		 element[0] = routeDep;
            		 element[1]= rutaArri;
                	listRoutes.add(element);
            	} else if(rutaArri.getAirportTo().equals(routeDep.getAirportTo())
            					&& rutaArri.getAirportFrom().equals(routeDep.getAirportFrom())) { 
            		Routes[] element = new Routes[2] ;
            		System.out.println(routeDep.getAirportFrom() +" to "+ routeDep.getAirportTo() +", conect " + routeDep.getConnectingAirport() ) ;
	           		 element[0] = routeDep;
	           		 element[1]= null;
	           		listRoutes.add(element);
            	}	 
            }
        }
        return listRoutes;
    }

	  public Long  get_utcOffsetByAPIJSON(String dataImput) {
		  Long utcOffset;
	        try {
	        	JSONParser jsonParser = new JSONParser();
	        	JSONObject jsonObject = (JSONObject) jsonParser.parse(dataImput);
	        	utcOffset = (Long) jsonObject.get("utc_offset");
       
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	return null;
	        }
	        return utcOffset;
	  }
	
}
