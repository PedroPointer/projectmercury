package com.mercury.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {

	
	/// validations 

		public  boolean isPolilineFormat(String str)
		{
		    Pattern p = Pattern.compile("-?\\d+(\\.\\d+)?( )-?\\d+(\\.\\d+)?(,|)");
		    Matcher m = p.matcher(str);
		    return m.find();
		}
		
		public  boolean isFormatCoordenadasGoogle(String str)
		{
		    Pattern p = Pattern.compile("-?\\d+(\\.\\d+)?(,)-?\\d+(\\.\\d+)");
		    Matcher m = p.matcher(str);
		    return m.find();
		}
		
		
		public  boolean isNumericDouble(String str)
		{
			if (str ==null || str.equals("")) 
				return false;
		    return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
		}
		
		public  boolean isNumericInteger(String str)
		{
			if (str ==null || str.equals("")) 
				return false;
		    return str.matches("\\d+");  
		}
		
		public  boolean isSpanishTexValue(String str)
		{
		    Pattern p = Pattern.compile("[a-z�������A-��������]+[ ,.]+");
		    Matcher m = p.matcher(str);
		    return m.find(); //Nombre de localidad  '-' and space.
		}
	
}
