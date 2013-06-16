
package net.theblackchamber.pages;

import java.util.List;

import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

/**
 * Backing class for the Project details page.
 * @author sminogue
 *
 */
public class Project {

	@Inject
	Logger logger;
	
	private String _projectName;
	
	@Property
	private net.theblackchamber.model.Project _project;
	
	@Log
	void onActivate(String projectName)
    {
		
		if(StringUtils.isBlank(projectName)){
			throw new RuntimeException("Missing Parameter");
		}
		
		_projectName = projectName;
		
    }
	
	String onPassivate(){
		return _projectName;
	}
	
	@Log
	void setupRender(){
		
		_project = getProjectByName(_projectName);
		
	}
	
	/**
	 * Helper method which will fetch an instance of {@link net.theblackchamber.model.Project} from the database by name.
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private net.theblackchamber.model.Project getProjectByName(String name){
		Expression qualifier = ExpressionFactory.matchExp(net.theblackchamber.model.Project.NAME_PROPERTY, name);

		List<net.theblackchamber.model.Project> projects = DataContext.createDataContext().performQuery(
				new SelectQuery(net.theblackchamber.model.Project.class, qualifier));

		if (projects == null) {
			return null;
		} else {
			return projects.get(0);
		}
	}
	
}
