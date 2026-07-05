package com.project.hostelms.controller;
 
 
import com.project.hostelms.CommonFuns;
import com.project.hostelms.Complaintstatus;
import com.project.hostelms.Roomstatuses;
import com.project.hostelms.LoginTypes; 
import com.project.hostelms.entity.*;
import com.project.hostelms.repository.*;
import com.project.hostelms.service.LoginsService;

import jakarta.annotation.Resource;  
import jakarta.servlet.http.HttpServletRequest;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;  
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody; 

@Controller
public class StudentController { 
	
 
	 
	@Autowired 
	private LoginsService memberService;  
	  
    @Resource    private UsersRepository members;  
    @Resource    private BlockRepository blocks;      
    @Resource    private RoomRepository rooms;         
    @Resource    private RoomstudentsRepository roomstatus;       
    @Resource    private ComplaintRepository complaints;  
    @Resource    private ComplaintcommentsRepository ccomments;       
    @Resource    private RegistrationrequestsRepository regrequests;    
    @Resource    private NoticeboardRepository           noticeboard;
    @Resource    private StudentRepository           students;
    @Resource    private DepartmentRepository departments;
    @Resource    private CourseRepository courses;
    @Resource    private SettingsRepository settings;
    @Resource    private   StudentmonthlyfeesRepository studentmonthlyfees;
   

    @Resource    private VisitorRepository visitors;  
    
	 @GetMapping("/")
	    public String index(Authentication auth){  
		//studentService.changeUserPassword(memberService.findByUsername("anuj.lpu1@gmail.com"), "Test@123");
		   if(auth!=null)
		    if(auth.isAuthenticated())
		    {
		    	if(!auth.getAuthorities().isEmpty())
		    	{
		    		for(LoginTypes l:LoginTypes.values())
		    		if(auth.getAuthorities().contains(new SimpleGrantedAuthority(l.toString())))
		    		{
		    			
		    			return "redirect:/"+l.toString().toLowerCase()+"/dashboard"; 
		    	}
		    	}
		    }
	        return "redirect:/login";
	    }  

	    @GetMapping("/signup") 
	    public String signup(Authentication auth,Model m){  
	    	
	    	 if(auth!=null)
	 		    if(auth.isAuthenticated())
	 		    {
	 		    	if(!auth.getAuthorities().isEmpty())
	 		    	{
	 		    		for(LoginTypes l:LoginTypes.values())
	 		    		if(auth.getAuthorities().contains(new SimpleGrantedAuthority(l.toString())))
	 		              	return "redirect:/"+l.toString().toLowerCase()+"/dashboard"; 
	 		    	}
	 		    }

		    	m.addAttribute("departments", departments.findAll());
	        return "signup";
	    } 
	    
	    @PostMapping("/checkAvailability")
	 	@ResponseBody
	    public Map<String, Object>   checkAvailability(HttpServletRequest request, Model model){  
	        Map < String, Object > arr = new HashMap < > ();  
	     	String un=request.getParameter("emailid"); 
	     	  arr.put("html", "<span style='color:yellow'> Email available for Registration .</span><script>$('#submit').prop('disabled',false);</script>");
	     	if(memberService.findByUsername(un)!=null)
	     	  arr.put("html", "<span style='color:red'> Email already exists .</span><script>$('#submit').prop('disabled',true);</script>");
	   		arr.put("success",  1); 
	   		return arr;
	    }
	    
	    
	    @PostMapping("/registerdb") 
	    @ResponseBody
		   public Map < String, Object > registerdb( HttpServletRequest request,Model model)   {
		     Map < String, Object > arr = new HashMap < > ();  

		     String fullname = request.getParameter("name");   
		     String mobileno =  request.getParameter("mobile");    
		     String emailid =  request.getParameter("email");    
		     String password =  request.getParameter("password");     
		     String address = request.getParameter("address");  
			  String parentname = request.getParameter("parentname");  
			  String parentmobile = request.getParameter("parentmobile");   
			  password=request.getParameter("password");   
			  int department = Integer.parseInt(request.getParameter("department"));   
			  int course = Integer.parseInt(request.getParameter("course"));  
			  int year = Integer.parseInt(request.getParameter("year"));  
			  Date dob = Date.valueOf(request.getParameter("dob"));
			  String regno = request.getParameter("regno"); 
			  String gender = request.getParameter("gender");  
		      
		     List < String > errors = new ArrayList < String > ();  
		     
		     Users obj;
			if (mobileno.trim().isBlank() || mobileno == null)
		       errors.add("Mobile no");
		     else
		     {
		        obj = members.findByMobilenumber(mobileno);	   
		      if (obj != null)
		         errors.add("Mobile no exists!");
		     } 
		     
		      
		     if (!memberService.validation_Password(password)) errors.add("A valid Password");
		     
		     if (emailid.trim() .isBlank() || emailid == null)
			       errors.add("Emailid");
			     else
			     {
			        Users u = memberService.findByUsername(emailid);	   
			      if (u != null)
			         errors.add("Emailid exists!");
			     }
		     if(students.findByRegno(regno)!=null)
		    	 errors.add("RegNo. already exists!");
		     if (  fullname == null || fullname.trim().isBlank()) errors.add("Full name");
		     
		     arr.put("success",  0); 
		     if (errors.size() == 0) {   
		       Registrationrequests u=new Registrationrequests();
		       u.setPassword(CommonFuns.encode64(password));
		       u.setStatus(1);
		       u.setFullname(fullname); 
		       u.setEmailid(emailid);
		       u.setMobilenumber(mobileno); 
		       u.setAddress(address);
			    u.setCourse(course);
			    u.setDepartment(department);
			    u.setDob(dob);
			    u.setGender(gender);
			    u.setParentmobile(parentmobile);
			    u.setParentname(parentname);
			    u.setRegno(regno);
			    u.setYear(year);
		       u.setCreationdate(CommonFuns.ctm());
		       regrequests.save(u);
		       
		    	   arr.put("success",  1); 
			       arr.put("html",  "Submitted successfully!.You can login after ADMIN approve your registration.");   
		     } else
		    	 arr.put("html",  "The following fields contains errors: " + errors.stream().collect(Collectors.joining(", ")));

		     return  arr;
		   }   
	    
	 @GetMapping("/login")
	    public String login(Authentication auth){  
		 //studentService.changeUserPassword(memberService.findByUsername("admin@admin.com"),"admin@123");
		   if(auth!=null)
		    if(auth.isAuthenticated())
		    { 
		    	if(!auth.getAuthorities().isEmpty())
		    	{  
		    		for(LoginTypes l:LoginTypes.values())
		    		{
		    		if(auth.getAuthorities().contains(new SimpleGrantedAuthority(l.toString())))
		    		{
		    			
		    			return "redirect:/"+l.toString().toLowerCase()+"/dashboard"; 
		    	   }
		    	}
		    }
		    }
	        return "login";
	    }  
 
	 @PostMapping("/loadcourses")
	 @ResponseBody
		public Hashtable<String,Object>  loadcourses(HttpServletRequest request)
		{
			Hashtable<String,Object> arr=new Hashtable<String,Object>(); 
			arr.put("success", 0); 
			int   department ; 
			List<Hashtable<String,Object>> lst= new ArrayList<>();
			arr.put("success", 1);
			  lst= new ArrayList<>();
			  department=Integer.parseInt(request.getParameter("department"));
			for(Course b:courses.findByDepartment(department))
			{
				arr=new Hashtable<String,Object>();
				arr.put("id", b.getId());
				arr.put("name", b.getName());
				arr.put("department", departments.findById(b.getDepartment()).getName()); 
				lst.add(arr);
			}
			arr=new Hashtable<String,Object>();
			arr.put("success", 1);
			arr.put("data", lst); 
			return arr;
		}
	 @GetMapping("/student") 
	    public String index(Model model){
	        //model.addAttribute("users", users.findByTypeOrderByIdDesc(LoginTypes.EMPLOYEE.toString()));
	        return "redirect:/student/dashboard";
	    } 
	    
	
    @GetMapping("/student/dashboard") 
    public String dashboard(Model model){
        //model.addAttribute("users", users.findByTypeOrderByIdDesc(LoginTypes.EMPLOYEE.toString()));
    	Users user = memberService.findByUsername(
    		      SecurityContextHolder.getContext().getAuthentication().getName());
    	Hashtable<String,Object> arr=new Hashtable<String,Object>(); 

    	 
    	   float  sum=0;
			ArrayList<Object> lst = new ArrayList<>();
			for(Studentmonthlyfees sm : studentmonthlyfees.findByUserAndPaid(user.getId(),0))
			{
				arr=new Hashtable<String,Object>(); 
			    arr.put("id", sm.getId());    
			    arr.put("room",rooms.findById(sm.getRoom()).getRoomno());    
			    arr.put("monthyear",Month.of(sm.getMonth()).name()+"-"+sm.getYear());  
				arr.put("totalfees", sm.getTotalfees());    
				sum+=sm.getTotalfees();
				lst.add(arr); 
			}
			
			model.addAttribute("fees", lst);
			model.addAttribute("balance",sum);
    	
        return "student/dashboard";
    } 
 
    
    @GetMapping("/student/rooms") 
    public String rooms(Model model){
        //model.addAttribute("users", users.findByTypeOrderByIdDesc(LoginTypes.EMPLOYEE.toString()));
    	Users user = memberService.findByUsername(
    		      SecurityContextHolder.getContext().getAuthentication().getName());
    	Hashtable<String,Object> arr=new Hashtable<String,Object>(); 

    	   
			ArrayList<Object> lst = new ArrayList<>();
			for(Roomstudents sm : roomstatus.findByStudent(user.getId(),Sort.by("id").descending()))
			{
				arr=new Hashtable<String,Object>(); 
			    arr.put("id", sm.getId()); 
			    Room r=rooms.findById(sm.getRoom());
			    arr.put("room",r.getRoomno());     
			    arr.put("block",blocks.findById(r.getBlock()).getName());
			    arr.put("floor", r.getFloor()==0?"Ground":r.getFloor()); 
				arr.put("rent", sm.getMonthlyrent());    
			    arr.put("fromdate",sm.getFromdt());      
			    arr.put("todate",sm.getTodt()==null?"":sm.getTodt());     
				lst.add(arr); 
			}
			
			
			model.addAttribute("rooms", lst); 
    	
        return "student/roomallocation";
    } 
    
    @GetMapping("/student/monthlyfees") 
    public String monthlyfees(Model model){
        //model.addAttribute("users", users.findByTypeOrderByIdDesc(LoginTypes.EMPLOYEE.toString()));
    	Users user = memberService.findByUsername(
    		      SecurityContextHolder.getContext().getAuthentication().getName());
    	Hashtable<String,Object> arr=new Hashtable<String,Object>(); 

    	 
    	   float  sum=0;
			ArrayList<Object> lst = new ArrayList<>();
			for(Studentmonthlyfees sm : studentmonthlyfees.findByUser(user.getId(),Sort.by("id").descending()))
			{
				arr=new Hashtable<String,Object>(); 
			    arr.put("id", sm.getId());    
			    arr.put("room",rooms.findById(sm.getRoom()).getRoomno());    
			    arr.put("monthyear",Month.of(sm.getMonth()).name()+"-"+sm.getYear());  
				arr.put("totalfees", sm.getTotalfees());  
 				arr.put("paiddate", sm.getPaiddate()==null?"":sm.getPaiddate());  
 				arr.put("paidby", sm.getPaidby()==null?"":sm.getPaidby());  
 				arr.put("paid", sm.getPaid());    
 				arr.put("rent", sm.getRent());    
				  arr.put("days", sm.getDays());  
				  arr.put("mess",sm.getMess());  
 				  arr.put("other", sm.getOther());  
				if(sm.getPaid()==0)sum+=sm.getTotalfees();
				lst.add(arr); 
			}
			
			model.addAttribute("fees", lst);
			model.addAttribute("balance",sum);
    	
        return "student/monthlyfees";
    } 
    
    @GetMapping("/student/changepassword") 
    public String changepassword(Model model){ 
        return "student/changepassword";
    } 
     
    
    @GetMapping("/student/myprofile")
    public String myprofile(Model model) {
    	Users user = memberService.findByUsername(
  		      SecurityContextHolder.getContext().getAuthentication().getName());
    	Users s=members.findByUsername(user.getUsername());  
    	model.addAttribute("user",user);   
    	model.addAttribute("student",students.findByUser(user.getId()));   
    	
    	return "student/myprofile";
    } 
     
    
    @PostMapping("/student/updatepassword")
    public String updatepassword(HttpServletRequest request,Model m)
    {
    	String password=request.getParameter("password");
    	String newpassword=request.getParameter("newpassword");
    	String confirmpassword=request.getParameter("confirmpassword");
    	
    	Users user = memberService.findByUsername(
    		      SecurityContextHolder.getContext().getAuthentication().getName());
    		    if(newpassword.compareTo(confirmpassword)>0)
    		    {
    		    	m.addAttribute("updatepassword_errors", "New Password and Confirm Password Field do not match  !!");
    		    }
    		    else if (!memberService.checkIfValidOldPassword(user, password)) {
    		        m.addAttribute("updatepassword_errors", "Inavlid Current Password");
    		    }
    		    else if(!memberService.validation_Password(newpassword))
    		    {
    		    	  m.addAttribute("updatepassword_errors", "Inavlid New Password.Please follow specified policies.");
    		    }
    		    else
    		    {
    		    	memberService.changeUserPassword(user, newpassword);
    		      m.addAttribute("updatepassword_success", "Password updated successfully");
    		    }
    	return changepassword(m);
    } 
    
    @PostMapping("/student/updateprofile") 
	   public String updateprofile(HttpServletRequest request,Model model) throws  Exception   { 
	     String fullname = request.getParameter("fullname");   
	     String mobileno =  request.getParameter("mobileno");   
	     
	     List < String > errors = new ArrayList < String > ();
	     
	     Users user = memberService.findByUsername(
		      SecurityContextHolder.getContext().getAuthentication().getName());
	     
	     Users obj;
		if (mobileno.trim() .isBlank() || mobileno == null)
	       errors.add("Mobile no");
	     else
	     {
	        obj = members.findByMobilenumber(mobileno);	   
	      if (obj != null && obj.getId()!=members.findByUsername(user.getUsername()).getId())
	         errors.add("Mobile no exists!");
	     } 
	     
	     if (  fullname == null || fullname.trim().isBlank()) errors.add("Full name");

	     if (errors.size() == 0) {  
	       obj = members.findByUsername(user.getUsername()); 
	       obj.setFullname(fullname);
	       obj.setMobilenumber(mobileno); 
	       members.save(obj);
	        
	       model.addAttribute("success",  " Updated successfully!."); 
	       
	     } else
	    	 model.addAttribute("error",  "The following fields contains errors: " + errors.stream().collect(Collectors.joining(", ")));

	     return  myprofile(model);
	   }
    
     
    @GetMapping("/student/complaints") 
    public String complaints(Model model){ 
    	  Users user = memberService.findByUsername(
    		      SecurityContextHolder.getContext().getAuthentication().getName());
    	  
    	 List<Object> fts=new ArrayList<Object>();
    	 
    	 Map < String, Object > arr = new HashMap < > ();  
    	 
    	 List<Integer> ids =new ArrayList<Integer>();
    	 
    	 
       	 for(Roomstudents fs:roomstatus.findByStudentAndStatus(user.getId(),1))
    	 {
    		 arr = new HashMap < > (); 
    		 if(ids.stream().filter(obj->fs.getRoom()==obj).findAny().orElse(null)==null)
    		 {
    		 
    		 Room f=rooms.findById(fs.getRoom());
    		 arr.put("name", f.getRoomno()+"("+blocks.findById(f.getBlock()).getName()+")"); 
    		 arr.put("id", f.getId());
    		 fts.add(arr);
    	   }
    	 }
       	 
    	 model.addAttribute("rooms",fts); 
    	 model.addAttribute("complaints", complaints.findByStudentOrderByIdDesc(user.getId()));
 
    	 return "student/complaints";
    } 
    
    
    @PostMapping("/student/addcomplaintdb") 
    @ResponseBody
	   public Map < String, Object > addcomplaintdb( HttpServletRequest request,Model model)   {
	     Map < String, Object > arr = new HashMap < > ();  

	     String title = request.getParameter("title");   
	     String desc = request.getParameter("desc");  
	     int room = Integer.parseInt(request.getParameter("room")); 
	       
	     Users user = memberService.findByUsername(
   		      SecurityContextHolder.getContext().getAuthentication().getName());
   	 
	      
	     List < String > errors = new ArrayList < String > ();
	     Complaint obj; 
	     
	     if (title.trim() .isBlank() || title == null)
	       errors.add("Title");
	      
	     if (desc.trim() .isBlank() || desc == null)
		       errors.add("Description");
		      
	        
	      arr.put("success",  0); 
	     if (errors.size() == 0) {  
	       obj = new Complaint(); 
	       obj.setCtitle(title);
	       obj.setCdescription(desc);
	       obj.setRoom(room); 
	       obj.setStudent(user.getId());
	       obj.setCreationdate(CommonFuns.ctm());
	       obj.setStatus(Complaintstatus.LODGED.acode());
	       complaints.save(obj); 
	    	   arr.put("success",  1); 
		       arr.put("html",  "Lodged successfully!.");   
	     } else
	    	 arr.put("html",  "The following fields contains errors: " + errors.stream().collect(Collectors.joining(", ")));

	     return  arr;
	   }
    
    @PostMapping("/student/addcomplaintcommentdb") 
    @ResponseBody
	   public Map < String, Object > addcomplaintcommentdb( HttpServletRequest request,Model model)   {
	     Map < String, Object > arr = new HashMap < > ();  

	     String comment = request.getParameter("comment");   
	     int id = Integer.parseInt(request.getParameter("id"));
	        

	     Users user = memberService.findByUsername(
   		      SecurityContextHolder.getContext().getAuthentication().getName());
   	 
	     List < String > errors = new ArrayList < String > ();
	  
	     
	     if (comment.trim() .isBlank() || comment == null)
	       errors.add("Comment");
	      
	     
	     if (complaints.findById(id) == null)
		       errors.add("Complaint not found");
	        
	      arr.put("success",  0); 
	     if (errors.size() == 0) {  
	       Complaintcomments obj = new Complaintcomments();
	       obj.setComment(comment) ; 
	       obj.setComplaint(id);
	       obj.setUser(user.getFullname()+"(#"+user.getId()+")");
	       obj.setCreationdate(CommonFuns.ctm());
	       ccomments.save(obj); 
	    	   arr.put("success",  1); 
		       arr.put("html",  "Submitted successfully!.");   
	     } else
	    	 arr.put("html",  "The following fields contains errors: " + errors.stream().collect(Collectors.joining(", ")));

	     return  arr;
	   }
    
    @GetMapping("/student/getcomplaint/{id}") 
    @ResponseBody
    public Map < String, Object > getcomplaint(@PathVariable int id){   
    	    Complaint cobj=complaints.findById(id);
    	    Map < String, Object > arr = new HashMap < > ();
    	    arr.put("complaint", cobj);
    	    Room f=rooms.findById(cobj.getRoom());
    	    arr.put("room",f.getRoomno()+"("+blocks.findById(f.getBlock()).getName()+")");
    	    arr.put("sstatus", Complaintstatus.getName(cobj.getStatus())); 
    	    arr.put("status", cobj.getStatus()); 
    	    arr.put("comments", ccomments.findByComplaintOrderByIdDesc(id));
    	    arr.put("creationdate",CommonFuns.Formatdt(cobj.getCreationdate())); 
    	    return arr;
    } 
    
    
   
     
    
    
    
    @GetMapping("/student/visitors") 
    public String visitors(Model model){ 
    	  Users user = memberService.findByUsername(
    		      SecurityContextHolder.getContext().getAuthentication().getName());
    	  
    	 List<Object> fts=new ArrayList<Object>();
    	 List<Object> vrequests=new ArrayList<Object>();
    	 
    	 
    	 Map < String, Object > arr = new HashMap < > ();  
    	 
    	 List<Integer> ids =new ArrayList<Integer>();
    	 
    	 for(Visitor v:visitors.findByStudentAndStatusOrderByIdAsc(user.getId(),1))
    	 {
    		 arr = new HashMap < > (); 
    		 Room f=rooms.findById(v.getRoom());
    		 arr.put("room", f.getRoomno()+"("+blocks.findById(f.getBlock()).getName()+")"); 
    		 arr.put("visitor", v.getVisitorname());
    		 arr.put("purpose", v.getPurpose());
    		 arr.put("visitingtime", v.getVisitingtime());
    		 arr.put("id", v.getId());
    		 vrequests.add(arr); 
    	 }
    	   
    	  
    	 
       	 for(Roomstudents fs:roomstatus.findByStudentAndStatus(user.getId(),1))
    	 {
    		 arr = new HashMap < > (); 
    		 if(ids.stream().filter(obj->fs.getRoom()==obj).findAny().orElse(null)==null)
    		 {
    		 
    		 Room f=rooms.findById(fs.getRoom());
    		 arr.put("name", f.getRoomno()+"("+blocks.findById(f.getBlock()).getName()+")"); 
    		 arr.put("id", f.getId());
    		 fts.add(arr);
    	   }
    	 }
       	 
    	 model.addAttribute("rooms",fts); 
    	 model.addAttribute("vrequests", vrequests);
 
    	 return "student/visitors";
    } 
    
    
    @PostMapping("/student/addvisitordb") 
    @ResponseBody
	   public Map < String, Object > addvisitordb( HttpServletRequest request,Model model)   {
	     Map < String, Object > arr = new HashMap < > ();  

	     String name = request.getParameter("name");   
	     String purpose = request.getParameter("purpose");  
	     int room = Integer.parseInt(request.getParameter("room")); 
	     String fromtime = request.getParameter("fromtime");  
	     String totime = request.getParameter("totime");   
	      String visitingtime="";
	     Users user = memberService.findByUsername(
   		      SecurityContextHolder.getContext().getAuthentication().getName());
   	 
	      
	     List < String > errors = new ArrayList < String > ();
	     Visitor obj; 
	     

	     if (fromtime.trim() .isBlank() || fromtime == null)
	       errors.add("From time"); 

	     if (totime.trim() .isBlank() || totime == null)
	       errors.add("To time");
	     
	     if (errors.size() == 0) {  
	    	Timestamp t1=CommonFuns.toTimestamp  (fromtime);
	      	Timestamp t2=CommonFuns.toTimestamp (totime);
	      	
	      	if(t1.compareTo(CommonFuns.ctm())<=0 && t2.compareTo(CommonFuns.ctm())<=0)
	      		errors.add("Invalid Timings");
	     }
	     
	     if (name.trim() .isBlank() || name == null)
		       errors.add("Visitor name"); 
	     
	      arr.put("success",  0); 
	     if (errors.size() == 0) {  
	    	 visitingtime=CommonFuns.Formatdt( CommonFuns.toTimestamp  (fromtime));
	    	 visitingtime+=" To ";
	    	 visitingtime+=CommonFuns.Formatdt( CommonFuns.toTimestamp  (totime));
	    	 
	       obj = new Visitor(); 
	       obj.setVisitorname(name);
	       obj.setVisitingtime(visitingtime);
	       obj.setPurpose(purpose);
	       obj.setRoom(room); 
	       obj.setStudent(user.getId());
	       obj.setCreationtime(CommonFuns.ctm());
	       obj.setStatus(1);
	       visitors.save(obj); 
	    	   arr.put("success",  1); 
		       arr.put("html",  "Lodged successfully!.");   
	     } else
	    	 arr.put("html",  "The following fields contains errors: " + errors.stream().collect(Collectors.joining(", ")));

	     return  arr;
	   }
    
    
    @PostMapping("/student/removevisitorentry/{id}")  
  	 @ResponseBody
      public  Map < String, Object > removevisitorentry(@PathVariable("id") int id ,Model model){ 
      	 Map < String, Object > arr = new HashMap < > ();
  		 
  		arr.put("success",  1); 
  		try {
  		 Visitor obj=visitors.findById(id);
  		 obj.setStatus(0);
  		 visitors.save(obj);
  		 arr.put("html",  "Changed Successfully! ");
  		}
  		catch(Exception e)
  		{
  			arr.put("success",  0);
  			arr.put("html",  "Error in changing! : "+e.getMessage());
  		}
  		return arr;
      }
    
}