(function($){
	
	$('.issues-filter-link').bind('click',function(e){
		e.preventDefault();
		$('.stats-link').parent().removeClass('active');
		$(this).parent().addClass('active');
	});
	
	$('#issues-tab').bind('click',function(e){
		e.preventDefault();
		$('#project-issues-page').show();
		$('#project-milestones-page').hide();
		$('.nav-tabs li').removeClass('active');
		$(this).parent().addClass('active');
	});
	
	$('#milestones-tab').bind('click',function(e){
		e.preventDefault();
		$('#project-issues-page').hide();
		$('#project-milestones-page').show();
		$('.nav-tabs li').removeClass('active');
		$(this).parent().addClass('active');
	});
	
})(jQuery)