
package net.theblackchamber.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.theblackchamber.model.Issue;
import net.theblackchamber.model.Label;
import net.theblackchamber.model.User;

import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;
import org.slf4j.Logger;

/**
 * Backing class for the Project details page.
 * @author sminogue
 *
 */
public class Project extends BasePage{

	@Inject
	Logger logger;
	
	private String _projectName;
	
	@Property
	private String _statusFilter;
	
	@Property
	private String _assignedFilter;
	
	@Property
	private net.theblackchamber.model.Project _project;
	
	@Inject
	private Cookies	cookies;
	
	@Property
	private User _loggedInUser;
	
	@Property
	private List<Issue> _allIssues;
	
	private List<Issue> _displayIssues;
	
	@Property
	private Issue _displayIssue;
	
	@Property
	private List<Issue> _myIssues;
	
	@Property
	private List<Issue> _unassignedIssues;
	
	@Property
	private List<Integer> _pageNumbers;
	
	@Property
	private List<Label> _labels;
	
	@Property
	private Label _label;
	
	private static final int PAGE_SIZE = 4;
	
	private static final int PAGINATION_SIZE = 5;
	
	@Property
	@ActivationRequestParameter("pg")
	private int _currentPage;
	
	public List<Issue> getDisplayIssues() {
		
		if(_currentPage < 1){
			_currentPage = 0;
		}
		
		int start = (_currentPage*PAGE_SIZE);
		int end = (_currentPage*PAGE_SIZE) + PAGE_SIZE;
		
		return _displayIssues.subList(start, end);
	}

	public void setDisplayIssues(List<Issue> _displayIssues) {
		this._displayIssues = _displayIssues;
	}

	public int getMyIssuesSize(){
		if(_myIssues == null){
			return 0;
		}else{
			return _myIssues.size();
		}
	}
	
	@Log
	void onActivate(String projectName,String statusFilter,String assignedFilter)
    {
		_projectName = projectName;
		_statusFilter = statusFilter;
		_assignedFilter = assignedFilter;
    }
	
	@Log
	String onPassivate(){
		return _projectName;
	}
	
	private List<Issue> filterIssueList(String field, Object value, List<Issue> originalList){
		
		Expression exp = ExpressionFactory.matchExp(field,value);
		List<Issue> issues = exp.filterObjects(originalList);
		
		return issues;
		
	}
	
	@Log
	private void populateClosedIssueLists(){
		
		if(_loggedInUser != null){
			_myIssues = filterIssueList(Issue.STATUS_PROPERTY, "C", _loggedInUser.getAssigned());
		}else{
			_myIssues = Collections.emptyList();
		}
		_allIssues = filterIssueList(Issue.STATUS_PROPERTY, "C", _project.getIssues());
		//_unassignedIssues = filterIssueList(Issue.ASSIGNED_PROPERTY, null,_allIssues);
		
	}
	
	public String getAllIssuesPillClass(){
		if(StringUtils.equalsIgnoreCase(_assignedFilter, "all") ){
			return "active";
		}
		return "";
	}
	
	public String getMyIssuesPillClass(){
		if(StringUtils.equalsIgnoreCase(_assignedFilter, "mine") ){
			return "active";
		}
		return "";
	}
	
	public String getUnassignedIssuesPillClass(){
		if(StringUtils.equalsIgnoreCase(_assignedFilter, "unassigned") ){
			return "active";
		}
		return "";
	}
	
	/**
	 * Helper method which will be used to populate the open issue list.
	 */
	@Log
	private void populateOpenIssueLists(){
		
		if(_loggedInUser != null){
			_myIssues = filterIssueList(Issue.STATUS_PROPERTY, "O", _loggedInUser.getAssigned());
		}else{
			_myIssues = Collections.emptyList();
		}
		_allIssues = filterIssueList(Issue.STATUS_PROPERTY, "O", _project.getIssues());
		//_unassignedIssues = filterIssueList(Issue.ASSIGNED_PROPERTY, null,_allIssues);
		
	}
	
	/**
	 * Method which will be called to setup things prior to page render.
	 */
	@Log
	void setupRender(){
		
		logger.debug("Looking Up Project by name: " + _projectName + " and " + _statusFilter);
		_project = getProjectByName(_projectName);
		
		logger.debug("Found project");
		
		String securityToken = cookies.readCookieValue("scarab");
		
		//Verify the security token
		if(!StringUtils.isBlank(securityToken)){
			logger.debug("About to lookup user by security token: " + securityToken);
			_loggedInUser = findUserBySecurityToken(securityToken);
		}
			if(_loggedInUser != null){
				logger.debug("Found user by security token: " + _loggedInUser.getEmail());
				//Populate lists depending on the statusFilter
				
				if(StringUtils.equals("open", _statusFilter)){
					
					populateOpenIssueLists();
					
				}else{
				
					populateClosedIssueLists();
					
				}
				
			}else{
				if(StringUtils.equals("open", _statusFilter)){
					
					populateOpenIssueLists();
					
				}else{
				
					populateClosedIssueLists();
					
				}
			}
			
			//Depending on the assignedFilter populate current display list.
			if(StringUtils.equalsIgnoreCase(_assignedFilter, "mine")){
				_displayIssues = _myIssues;
			}else if(StringUtils.equalsIgnoreCase(_assignedFilter, "unassigned")){
				_displayIssues = _unassignedIssues;
			}else{
				_displayIssues = _allIssues;
			}
			
			//Page list
			int totalPages = _allIssues.size() / PAGE_SIZE;
			if(_allIssues.size() % PAGE_SIZE !=0){
				totalPages += 1;
			}
		
			_pageNumbers = new ArrayList<Integer>();
			if(totalPages <= PAGINATION_SIZE){
				for(int i = 1;i<=totalPages;i++){
					_pageNumbers.add(i);
				}
			}else{
				//TODO
			}
			
			_labels = fetchLabels();
			
	}
	
	/**
	 * Method which will fetch all issue labels from the database.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Label> fetchLabels(){
		
		SelectQuery query = new SelectQuery(Label.class);
		
		return DataContext.createDataContext().performQuery(query);
		
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
