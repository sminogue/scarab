(function($) {

	String.prototype.endsWith = function(suffix) {
	    return this.indexOf(suffix, this.length - suffix.length) !== -1;
	};
	
	Utility = function() {
	}

	Utility.prototype = {
		cookiesEnabled : function() {
			//Try and Create test cookie
			$.cookie("test","test");
			
			//Try and get test cookie
			if($.cookie("test")){
				$.cookie("test",null);
				return true;
			}else{
				$.cookie("test",null);
				return false;
			}
		},
		showModal : function(containerSelector){
			$('body').css('overflow', 'hidden');
			$(containerSelector).modal('show');
		},
		showErrorModal: function(errorText){
			$('#errorModal .fatal-error').text(errorText);
			Utility.showModal('#errorModal');
		}
	}
	Utility = new Utility();

})(jQuery);