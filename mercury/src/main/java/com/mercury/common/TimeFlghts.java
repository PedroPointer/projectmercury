package com.mercury.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


import org.springframework.stereotype.Component;


@Component
public class TimeFlghts {
	
	private String years;
	private String months;
	private Integer iMonths;

	 public int compareHora(String hour1, String hour2) {
		 int out= 0;
		try {	
		 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		 Date date1 = sdf.parse(hour1);
		 Date date2 = sdf.parse(hour2);
		 out=  date1.compareTo(date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return out;
		}
		return out;
	 }
	
	public Date FromString (String time, Integer offset ) {
		try {	
		//time = time+"-0"+SSSZ;					 2018-04-04T07:00:00
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	    TimeZone timezone = TimeZone.getDefault();
	    timezone.setRawOffset(offset*60*60*100);
		sdf.setTimeZone(timezone );
		Date result = sdf.parse(time);
		return result;
	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Integer [] getDiferenceDate ( Date inicio, Date fin ) {
			Integer monthYear [] = new Integer [2];
			Calendar cInicio = Calendar.getInstance();
			cInicio.setTime(inicio);
			Calendar cFin = Calendar.getInstance();
			cInicio.setTime(inicio);
            
			int difM = cFin.get(Calendar.YEAR) - cInicio.get(Calendar.YEAR);
			int difA = difM * 12 + cFin.get(Calendar.MONTH) - cInicio.get(Calendar.MONTH);
			
			monthYear[0]=  difM;
			monthYear[1]=  difA;

            return monthYear;
	}
	
	 public Integer getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	 }
	 
	 public Integer getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	 }
	 
	 public String getHora(Date date) {
		 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return sdf.format(date);
	 }
	 public String setDateTimeFormat(Long year,Long month,Long day,String hour, Integer offset) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
		SimpleDateFormat sdfout = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		String time = year.toString()+"-"+String.format("%02d", (month))+"-"+String.format("%02d", (day))
		+ "-"+hour;
		
	    TimeZone timezone = TimeZone.getDefault();
	    timezone.setRawOffset(offset*60*60*100);
	    sdf.setTimeZone(timezone );

		Date date = null;
		try {
			date = sdf.parse(time);
			return sdfout.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
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
	 
	
    /** Transform Calendar to ISO 8601 string. */
    public static String fromCalendar(final Calendar calendar) {
        Date date = calendar.getTime();
        String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .format(date);
        return formatted.substring(0, 22) + ":" + formatted.substring(22);
    }

    /** Get current date and time formatted as ISO 8601 string. */
    public static String now() {
        return fromCalendar(GregorianCalendar.getInstance());
    }

    /** Transform ISO 8601 string to Calendar. */
    public static Calendar toCalendar(final String iso8601string)
            throws ParseException {
        Calendar calendar = GregorianCalendar.getInstance();
        String s = iso8601string.replace("Z", "+00:00");
        try {
            s = s.substring(0, 22) + s.substring(23);  // to get rid of the ":"
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException("Invalid length", 0);
        }
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(s);
        calendar.setTime(date);
        return calendar;
    }
}
	

