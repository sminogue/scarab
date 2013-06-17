(function($) {
	define([ "t5/core/dom", "bootstrap" ], function(t5, bootstrap) {
		return function() {
			//Init tool tips on the page.
			$("[rel='tooltip']").tooltip();
		};
	});
})(jQuery);
