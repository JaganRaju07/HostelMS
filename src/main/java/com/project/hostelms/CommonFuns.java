package com.project.hostelms; 
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp; 

public class CommonFuns {
	
	 
	public static String Formatdt(Date s)
	{
		return   (new SimpleDateFormat("dd-MM-yyyy")).format(s);
	}
	
	
	public static String Formatdt(Timestamp s)
	{
		return   (new SimpleDateFormat("MMM dd,yyyy hh:mm a")).format(s);
	}
	
	public static int cint(String s)
	{
		return Integer.parseInt(s);
	} 
	public static Timestamp ctm()
	{
		return Timestamp.valueOf(LocalDateTime.now());
	}
	public static float cfloat(String s)
	{
		return Float.parseFloat(s);
	} 
 
	public static Time toTime(String tm)
	{
       if(tm.length()==5)
    	   tm=tm+":00";
		return Time.valueOf(tm);
	}
	
	public static Timestamp toTimestamp(String tm)
	{ 
		return Timestamp.valueOf(LocalDateTime.parse(tm));
	}
	
	public static Date toDate(String tm)
	{ 
		return Date.valueOf(tm);
	}
	
	public static String fromTime(Time tm)
	{
      return tm.toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
	}


	public static Double cdouble(String str) { 
		return Double.valueOf(str);
	}
	
	public static String formatfloat(float f)
	{
		return String.format("%.2f", f);
	}
	
	public static String encode64(String str)
	{
		byte[] encodedBytes = Base64.getEncoder().encode(str.getBytes());
		return new String(encodedBytes);
	}
	
	public static String decode64(String str)
	{
		byte[] decodedBytes  = Base64.getDecoder().decode(str.getBytes());
		return new String(decodedBytes);
	}
	
	
}
