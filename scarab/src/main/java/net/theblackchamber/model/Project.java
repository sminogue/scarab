package net.theblackchamber.model;

import java.util.List;

import net.theblackchamber.model.auto._Project;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;

public class Project extends _Project {

	public int getOpenPercentage(){
		int issueCount = getIssues().size();
		
		if(issueCount == 0){
			return 0;
		}else{
			double per = (((double)getOpenIssueCount() / (double)issueCount));
			return (int)(per*100);
		}
		
	}
	
	public int getClosedPercentage(){
		return 100 - getOpenPercentage();
	}
	
	public int getClosedIssueCount(){
		Expression exp = ExpressionFactory.matchExp(Issue.STATUS_PROPERTY,"C");
		List<Issue> openIssues = exp.filterObjects(getIssues());
		
		return openIssues.size();
	}
	
	public int getOpenIssueCount(){
		
		Expression exp = ExpressionFactory.matchExp(Issue.STATUS_PROPERTY,"O");
		List<Issue> openIssues = exp.filterObjects(getIssues());
		
		return openIssues.size();
		
	}
	
}
