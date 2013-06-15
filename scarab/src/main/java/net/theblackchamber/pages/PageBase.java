package net.theblackchamber.pages;

import java.util.Collections;
import java.util.List;

import net.theblackchamber.model.User;

import org.apache.cayenne.BaseContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;

/**
 * Class which will provide base functionality for all web service and regular
 * page classes.
 * 
 * @author sminogue
 * 
 */
public abstract class PageBase {

	/**
	 * Find an existing use in the database by securityToken.
	 * 
	 * @param securityToken
	 * @return User or null is not found.
	 */
	protected User findUserBySecurityToken(String securityToken) {
		List<User> users = findUsersByAttribute(User.SECURITY_TOKEN_PROPERTY,
				securityToken);

		if (users == null || users.isEmpty()) {
			return null;
		} else {
			return users.get(0);
		}
	}

	/**
	 * Find an existing use in the database by email address.
	 * 
	 * @param email
	 * @return User or null is not found.
	 */
	protected User findUserByEmail(String email) {

		List<User> users = findUsersByAttribute(User.EMAIL_PROPERTY, email);

		if (users == null || users.isEmpty()) {
			return null;
		} else {
			return users.get(0);
		}

	}

	/**
	 * Common DRY method which will be used to retrieve the user from the
	 * database by any attribute.
	 * 
	 * @param attribute
	 *            Attribute to search by as defined in constants in {@link User}
	 *            class
	 * @param value
	 *            Value to match. This method MATCHES not a LIKE
	 * @return Return a list of users matching the attribute/value. Empty list
	 *         should none be found.
	 * 
	 */
	@SuppressWarnings("unchecked")
	private List<User> findUsersByAttribute(String attribute, Object value) {

		Expression qualifier = ExpressionFactory.matchExp(attribute, value);

		List<User> users = BaseContext.getThreadObjectContext().performQuery(
				new SelectQuery(User.class, qualifier));

		if (users == null) {
			return Collections.emptyList();
		} else {
			return users;
		}

	}

}
