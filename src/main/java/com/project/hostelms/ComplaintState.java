package com.project.hostelms;

public enum ComplaintState { 
	
	LODGED(0,"Newly Lodged"),
	PROCESSING (1,"Processing"), 
	CLOSED(2,"Closed");
	
	private int acode;
	private String aname;
	ComplaintState(int code,String name)
	{
		this.acode=code;
		this.aname=name;
	}
	
	
	public int acode() {return acode;}
	public String aname() {return aname;} 
	
	public static String getName(int code)
	{
		for(ComplaintState s: ComplaintState.values())
			if(s.acode()==code)
		       return s.aname();
		return "";
	}
}
