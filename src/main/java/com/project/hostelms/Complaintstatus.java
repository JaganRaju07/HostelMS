package com.project.hostelms;

public enum Complaintstatus { 
	
	LODGED(0,"Newly Lodged"),
	PROCESSING (1,"Processing"), 
	CLOSED(2,"Closed");
	
	private int acode;
	private String aname;
	Complaintstatus(int code,String name)
	{
		this.acode=code;
		this.aname=name;
	}
	
	
	public int acode() {return acode;}
	public String aname() {return aname;} 
	
	public static String getName(int code)
	{
		for(Complaintstatus s: Complaintstatus.values())
			if(s.acode()==code)
		       return s.aname();
		return "";
	}
}
