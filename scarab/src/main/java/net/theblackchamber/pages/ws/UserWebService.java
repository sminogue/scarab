package net.theblackchamber.pages.ws;

import java.io.BufferedReader;
import java.util.Date;
import java.util.UUID;

import net.theblackchamber.constants.ApplicationConstants;
import net.theblackchamber.model.User;
import net.theblackchamber.pages.BasePage;
import net.theblackchamber.utility.CayenneUtility;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Cookies;
import org.h2.util.IOUtils;
import org.slf4j.Logger;

/**
 * 
 * Webservice class to provide user functions via webservice REST Calls.
 * 
 * @author sminogue
 * 
 */
public class UserWebService extends BasePage {
	
	@Inject
	private Logger	logger;

	@Inject
	private Cookies	cookies;

	/**
	 * Method which will try and find a user by email and if its not found
	 * insert it.
	 * 
	 * @param email
	 * @return
	 * @throws Exception
	 */
	private User getOrCreateUser(String email) throws Exception {
		
		User user = findUserByEmail(email);

		if (user == null) {
			// No User exists... so create them

			user = CayenneUtility.createContext().newObject(User.class);
			user.setEmail(email);
			user.setJoinDate(new Date());
			user.setUpdated(new Date());

			user.getObjectContext().commitChanges();

			user = findUserByEmail(email);

			if (user == null) {
				// The commit didnt work... this is a bad thing. Throw an error.
				throw new Exception(
						"Failed to insert new user into the database.");
			} else {
				return user;
			}

		} else {
			return user;
		}

	}

	/**
	 * Webservice which will be called to login a user using mozilla persona.
	 * 
	 * @param assertion
	 *            Persona assertion.
	 * @return
	 * @throws Exception
	 */
	@Log
	public JSONObject onLogin(
			@RequestParameter(value = "assertion", allowBlank = false) String assertion)
			throws Exception {
		try {
			// Verify assertion with persona.
			String url = "https://verifier.login.persona.org/verify?assertion="
					+ assertion + "&audience="
					+ ApplicationConstants.PERSONA_AUDIENCE;
			HttpPost post = new HttpPost(url);

			DefaultHttpClient client = new DefaultHttpClient();

			HttpResponse response = client.execute(post);

			BufferedReader reader = new BufferedReader(
					IOUtils.getReader(response.getEntity().getContent()));

			String line = "";
			String jsonString = "";
			while ((line = reader.readLine()) != null) {
				jsonString += line;
			}

			JSONObject obj = new JSONObject(jsonString);

			// See if persona authorized the user
			if(!StringUtils.equalsIgnoreCase(obj.getString("status"),"okay")){
				//User is not really logged in. Log it and return a brush off.
				//(Kinda like a girl giving you the number for pizza hut rather than her real number.)
				logger.error("Failed to authenticate persona assertion: " + obj.getString("reason"));
				return obj;
			}
			
			//Find user by email
			User user = getOrCreateUser(obj.getString("email"));
			
			String securityToken = cookies.readCookieValue("scarab");
			if(StringUtils.isBlank(securityToken) || !StringUtils.equals(securityToken, user.getSecurityToken())){
				//If securityToken is blank or doesnt match the DB... 
				//Create a new security token and update the DB.
				logger.debug("Blank or non-matching security token. Updating.");
				UUID uid = UUID.randomUUID();
				user.setSecurityToken(uid.toString());
				user.getObjectContext().commitChanges();
				obj.put("securityToken", uid.toString());
			}else{
				//Security token exists and matches the user in DB.
				//This means user is logged in. Already.
				logger.debug("User already logged in and security token matches. Do Nothing.");
				obj.put("securityToken", securityToken);
			}
			
			//We be done.
			
			// Return good result to caller.
			return obj;
			
		} catch (Exception e) {
			logger.error("Failed while attempting to login user.", e);
			throw e;
		}
	}

	/**
	 * Webservice which will be called to logout a user.
	 * 
	 * @return
	 * @throws Exception
	 */
	@Log
	public JSONObject onLogout() throws Exception {

		String securityToken = cookies.readCookieValue("scarab");

		if (!StringUtils.isBlank(securityToken) && !StringUtils.equalsIgnoreCase("null", securityToken)) {
			// User will not have token if they are not logged in. Since this
			// gets called by pages when user is not logged in. See Persona
			// logout.
			//Wipe the security token from the user.
			User loggedInUser = findUserBySecurityToken(securityToken);
			loggedInUser.setSecurityToken(null);
			loggedInUser.getObjectContext().commitChanges();
		}

		JSONObject object = new JSONObject();

		object.put("status", "200");

		return object;

	}
}
