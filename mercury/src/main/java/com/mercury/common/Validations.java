package com.mercury.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validations {

	@Autowired
	TimeFlghts timeFlghts;	

	public  boolean isIATACode(String str)
	{
		if (str ==null || str.length()!=3 ) {
			return false;
		}
	    Pattern p = Pattern.compile("[a-zA-Z]{3}");
	    Matcher m = p.matcher(str);
	    return m.find();
	}
	
	public  boolean isCorrectDate( String departureDateTime,String dateFlightArrival ){
		return timeFlghts.inCorrecDateimput( departureDateTime,dateFlightArrival);
	}
	
}
