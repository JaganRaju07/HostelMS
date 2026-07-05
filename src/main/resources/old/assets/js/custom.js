var ajxfrm;   

function validatemobile(evt)
 {
  evt = (evt) ? evt : window.event;
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   if (charCode > 31 && (charCode < 48 || charCode > 57)) {
       return false;
   }
   return true;
 } 
   function checkEmail(email) { 
   var re = /\S+@\S+\.\S+/;
       return re.test(email);
 }
 
 function  validatealpha(event)
 { 
    var charCode = event.keyCode;

           if ((charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123) || charCode == 8 || charCode == 32 || charCode == 46)

               return true;
           else
               return false;
 }

 $(document).ready(function(){
	$(".menu-icon").click(function(e) {
	       e.preventDefault();
	       $(".menu-icon").toggleClass("menuicon");
	       $(".main").toggleClass("main-width");
	       $(".sidebar").toggleClass("active1");
	       $(".sidenav li a").toggleClass("anchor");
	       $(".sidenav li").toggleClass("lislide");
	       $(".sidenav p").toggleClass("apphide");
	       $(".logo span").toggleClass("headspan");
	       $(".logo").toggleClass("lm");

	   });
	  
	   
	
	   $("form.ajaxfrm").off( "submit");
	   	$("form.ajaxfrm").submit(function(e) {

	   	    e.preventDefault(); // avoid to execute the actual submit of the form.
           ajxfrm=this;
		   $(this).find('button').attr('disabled',true);
	   	    var form = $(this);
	   	    var url = form.attr('action');
			jQuery('.aert').removeClass('d-none').addClass('d-none');
	   	    $.ajax({
	   	           type: "POST",
	   	           url: url,
	   	           data: form.serialize(), // serializes the form's elements.
	   			    dataType: "json",
	   	           success: function(data)
	   	           {
	   			      if(data.success)
	   	                { 
							$(ajxfrm).find(".alert.alert-success").html(data.html).removeClass('d-none');
							if($(ajxfrm).attr("data-reset")=="1") $(ajxfrm).trigger("reset");
						
							setTimeout(function() { 
								$(ajxfrm).parents(".modal").modal('hide');
								if($(ajxfrm).attr("data-reload")=="1")
								    document.location.reload();
							}, 2000);
						} // show response from the php script.
	   				  else
					  {

						$(ajxfrm).find('button').attr('disabled',false);
	   				     $(ajxfrm).find(".alert.alert-danger").html(data.html).removeClass('d-none'); // show response from the php script.
	   				   }
						  
	   				  setTimeout(function() {jQuery('.aert').addClass('d-none');}, 2000);
	   	           }
	   	         });
	   			     });
					 
					 
					 $('#print').click(function(){ 
					         var _h = $('head').clone()
					         var _p = $('#print_out').clone();
					         var _el = $('<div>')
					         _el.append(_h)
					         _el.append('<style>html, body, .wrapper {min-height: unset !important;}.btn,.nprint{display:none !important}</style>')
					         _p.prepend('<div   class="mb-3 w-100">'+
					         '<img class="mx-4" src="/assets/img/logo1.png" width="50px" height="50px"/>'+					          
					         '<h3 class="text-center w-100 text-black">Online Apartment Management</h3>'+
					         '<h2 class="text-center w-100 text-black ">'+$(this).attr("title")+'</h2>'+					          
					         '</div><hr/>'); 
					         _el.append("<div class='p-3 text-black'>"+_p.html()+"</div>")
					         var nw = window.open("","_blank","width=1200,height=1200")
					             nw.document.write(_el.html())
					             nw.document.close()
					             setTimeout(() => {
					                 nw.print()
					                 setTimeout(() => {
					                     nw.close() 
					                 }, 300);
					             }, 500);
					     })
						 $("#sidebar a").each(function(){
						 				 	if(document.location.href==this.href)
						 				 		$(this).addClass('active');
						 				 });	
		   
}) ;