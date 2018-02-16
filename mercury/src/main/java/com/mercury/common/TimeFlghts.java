package com.mercury.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.stereotype.Component;


@Component
public class TimeFlghts {
	
	public boolean inCorrecDateimput( String departureDateTime2 , String dateFlightArrival1) {
		try {
		 Date arrival1 = this.FromString(dateFlightArrival1);
		 Date departure2 = this.FromString(departureDateTime2);
		 if (arrival1.compareTo(departure2) > 0) {
			 return true;
		 }
		}catch (Exception e) {
			return false;
		}
		return false;
	}
	
	public boolean inCorrecDiference(String dateFlightArrival1, String departureDateTime2) {
		 Date arrival1 = this.FromString(dateFlightArrival1);
		 Date departure2 = this.FromString(departureDateTime2);

	     Calendar calendar = Calendar.getInstance();
         calendar.setTime(arrival1);
	     calendar.add(Calendar.HOUR, 2);  
	     arrival1= calendar.getTime();
	     calendar.add(Calendar.HOUR, 6);
	     Date endDate = calendar.getTime();
		 
	     if (arrival1.compareTo(departure2) * departure2.compareTo(endDate) >= 0) {
		    	return true;
		}
		 return false; 
	 }
	 
	public Date FromStringAddOffset (String time, Integer offset ) {
		try {	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		String timeZone =String.format("%02d",offset)+"00";
		if (offset>0) {
			timeZone= "+"+timeZone; 
		}else {
			timeZone= "-"+timeZone;
		}
		Date result = sdf.parse(time +":00"+timeZone);
		return result;
	
		} catch (ParseException e) {
//			e.printStackTrace();
			return null;
		}
	}
	
	public Date FromString (String time) {
		try {	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		return sdf.parse(time);
		} catch (ParseException e) {
//			e.printStackTrace();
			return null;
		}
	}
	
	public Integer getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	 }
	 
    public String setOutFormatDate(Date date) {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return sdf.format(date);
	 }
	 
	public Date setDateByFligth(Long year,Long month,Integer day,String hour, Integer offset) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
		String time = year.toString()+"-"+String.format("%02d", (month))+"-"+String.format("%02d", (day))
		+ "-"+hour;
		
	    TimeZone timezone = TimeZone.getDefault();
	    timezone.setRawOffset(offset*60*60*100);
	    sdf.setTimeZone(timezone );

		try {
			return sdf.parse(time);
		} catch (ParseException e) {
//			e.printStackTrace();
		}
		return null;
	 }
	
	public Integer getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
	 
	public Date addMonthDate (Date date, int mouth){
	       Calendar calendar = Calendar.getInstance();
	       calendar.setTime(date); 
	       calendar.add(Calendar.MONTH , mouth); 
	       return calendar.getTime(); 
	}
	 
}
	

