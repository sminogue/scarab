package net.theblackchamber.pages;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * 
 * Page class for the fatal error display. This should be used to show an error
 * which cannot be recovered from.
 * 
 * @author sminogue
 * 
 */
public class Err {

	@Property
	@ActivationRequestParameter("errcode")
	private String								errcode;

	@Inject
	private Messages							messages;

	@Property
	private String								errMessage;

	/**
	 * Translation map from error code in the url to key in the messages file(s)
	 */
	private static final Map<String, String>	errMappings;
	static {
		Map<String, String> aMap = new HashMap<String, String>();
		aMap.put("nocookie", "no.cookie.error");
		aMap.put("unexpected", "unexpected.error");
		aMap.put("badassertion", "assertion.error");
		errMappings = Collections.unmodifiableMap(aMap);
	}

	/**
	 * Method which will fetch the correct error message from the messages
	 * file(s) and prepare for it to be displayed on render.
	 */
	@SetupRender
	void initializeValue() {

		if (StringUtils.isBlank(errcode) || errMappings.containsKey(errcode)) {

			errcode = "unexepected";
			// TODO make a WS call to log missing message key

		}

		String messageKey = errMappings.get(errcode);
		errMessage = messages.get(messageKey);

	}
}
