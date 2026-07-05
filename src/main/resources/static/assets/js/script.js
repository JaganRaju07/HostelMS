/*------------------------------------------------------------------
* Bootstrap Simple Admin Template
* Version: 3.0
* Author: Alexis Luna
* Website: https://github.com/alexis-luna/bootstrap-simple-admin-template
-------------------------------------------------------------------*/

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
	 
	 (function($) {
	     'use strict';
    // Toggle sidebar on Menu button click
    $('#sidebarCollapse').on('click', function() {
        $('#sidebar').toggleClass('active');
        $('#body').toggleClass('active');
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
							 
							 
							 $('input[name=name]').on('keypress',function(event){
							 	 return validatealpha(event);		 
							  });
							   $('input[name=mobile]').on('keypress',function(event){
							 	 return validatemobile(event);		 
							  });
})(jQuery);

 
