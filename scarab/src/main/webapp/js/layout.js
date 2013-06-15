(function($) {

	/**
	 * Function which will show the logging in text instead of login or logout
	 * link.
	 */
	function showLoggingIn(){
		$('#login-link').html('<span id="signin">'+loggingIn+'</span>');
	}
	
/**
 * Function which will show the logging out text instead of login or logout
 * link.
 */
	function showLoggingOut(){
		$('#login-link').html('<span id="signout">'+loggingOut+'</span>');
	}

	/**
	 * Show the Logout link
	 */
	function showLogoutLink(){
		$('#login-link').html('<a href="#" id="signout">'+logout+'</a>');
		$('#signout').bind('click', function(e) {
			e.preventDefault();
			navigator.id.logout();
		});
	}
	
	/**
	 * Show the Login link.
	 */
	function showLoginLink(){
		$('#login-link').html('<a href="#" id="signin">'+login+'</a>');
		$('#signin').bind('click', function(e) {
			e.preventDefault();
			navigator.id.request();
		});
	}
	
	/**
	 * Function which will be used to show and perform any kind of binds on 
	 * controls which require the user to be logged in.
	 */
	function showAuthenticationRequiredResources(){
		$('.auth-required').show();
	}
	
	/**
	 * Function which will be used to hide and perform any kind of unbinds on 
	 * controls which require the user to be logged in.
	 */
	function hideAuthenticationRequiredResources(){
		$('.auth-required').hide();
	}
	
	$(document).ready(function() {

		$('.navbar-inner .active').removeClass('active');
		
		if (!Utility.cookiesEnabled) {
			// Redirect to error page.
			var loc = window.location.pathname;
			if (loc.endsWith("/Err")) {
				return;
			} else {
				window.location.replace("/err&errcode=nocookie");
			}
		}

		$('#' + activeNavBarButton + '-button').addClass('active');
		
		var currentUser = '';

		$('#signin').bind('click', function(e) {
			e.preventDefault();
			navigator.id.request();
		});
		
		$('#signout').bind('click', function(e) {
			e.preventDefault();
			navigator.id.logout();
		});
		
		navigator.id.watch({
			loggedInUser : currentUser,
			onlogin : function(assertion) {
				showLoggingIn();
				$.ajax({
					type : 'POST',
					url : '/ws/UserWebService:login', // This is a URL on your website.
					data : {
						assertion : assertion
					},
					success : function(res, status, xhr) {
						if (res.status == 'okay') {
							
							currentUser = res.email;

							// Get UID from response and create cookie
							$.cookie("scarab", res.securityToken);
							
							//Update the Login/Logout links
							showLogoutLink();
							
							showAuthenticationRequiredResources();
						} else {
							console.log('Bad assertion.');
							window.location.replace("/err&errcode=badassertion");
						}
					},
					error : function(xhr, status, err) {
						navigator.id.logout();
						showLoginLink();
						Utility.showErrorModal(loginError);
					}
				});

			},
			onlogout : function() {
				showLoggingOut();
				$.ajax({
					type : 'POST',
					url : '/ws/UserWebService:logout', 
					success : function(res, status, xhr) {
						//Clear the user
						currentUser = null;
						$.cookie("scarab", null);
						
						showLoginLink();
					},
					error : function(xhr, status, err) {
						showLogoutLink();
						Utility.showErrorModal(logoutError);
					}
				});

			}
		});
	});
})(jQuery);