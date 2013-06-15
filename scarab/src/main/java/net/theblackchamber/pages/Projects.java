package net.theblackchamber.pages;

import java.util.List;

import net.theblackchamber.model.Project;

import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.query.SelectQuery;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

/**
 * Class which is the backing class for the projects page.
 * 
 * @author sminogue
 * 
 */

public class Projects extends PageBase {

	@Inject
	private Logger		logger;

	@Inject
	private Messages	messages;

	@Property
	private Project		_project;

	/**
	 * Get list of Projects from database.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Log
	public List<Project> getProjects() {

		try {

			List<Project> projects = DataContext.createDataContext()
					.performQuery(new SelectQuery(Project.class));

			logger.debug("Retrieved Projects from database: " + projects.size());

			return projects;

		} catch (Throwable t) {
			String uid = RandomStringUtils.randomAlphanumeric(6);
			logger.error("[" + uid + "] Failed to retrieve list of projects: "
					+ t.getMessage(), t);
			throw new RuntimeException(StringUtils.replace(
					messages.get("load.projects.error"), "${uid}", uid));
		}

	}

}
