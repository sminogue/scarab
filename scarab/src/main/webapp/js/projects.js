(function($){
	
	function initializePage(){
		$("[rel='tooltip']").tooltip();
	}
	
	
	/*$('.issues-filter-link').bind('click',function(e){
		e.preventDefault();
		$('.issues-filter-link').parent().removeClass('active');
		$(this).parent().addClass('active');
	});*/
	
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
	
	$('#project-create-issue-button').bind('click',function(e){
		e.preventDefault();
		$('#create-issue-dialog').modal('show')
		$('.create-label').on('click',function(e2){
			e2.preventDefault();
			
			if($(this).parent().hasClass('active')){
				$(this).parent().removeClass('active');
			}else{
				$(this).parent().addClass('active');
			}
			
			
		});
	});
	
})(jQuery);