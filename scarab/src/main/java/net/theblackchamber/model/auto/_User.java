package net.theblackchamber.model.auto;

import java.util.Date;
import java.util.List;

import org.apache.cayenne.CayenneDataObject;

import net.theblackchamber.model.Comment;
import net.theblackchamber.model.Issue;
import net.theblackchamber.model.Project;

/**
 * Class _User was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _User extends CayenneDataObject {

    public static final String EMAIL_PROPERTY = "email";
    public static final String JOIN_DATE_PROPERTY = "joinDate";
    public static final String SECURITY_TOKEN_PROPERTY = "securityToken";
    public static final String UPDATED_PROPERTY = "updated";
    public static final String ASSIGNED_PROPERTY = "assigned";
    public static final String COMMENTS_PROPERTY = "comments";
    public static final String PROJECTS_PROPERTY = "projects";
    public static final String REPORTED_PROPERTY = "reported";

    public static final String ID_PK_COLUMN = "id";

    public void setEmail(String email) {
        writeProperty("email", email);
    }
    public String getEmail() {
        return (String)readProperty("email");
    }

    public void setJoinDate(Date joinDate) {
        writeProperty("joinDate", joinDate);
    }
    public Date getJoinDate() {
        return (Date)readProperty("joinDate");
    }

    public void setSecurityToken(String securityToken) {
        writeProperty("securityToken", securityToken);
    }
    public String getSecurityToken() {
        return (String)readProperty("securityToken");
    }

    public void setUpdated(Date updated) {
        writeProperty("updated", updated);
    }
    public Date getUpdated() {
        return (Date)readProperty("updated");
    }

    public void addToAssigned(Issue obj) {
        addToManyTarget("assigned", obj, true);
    }
    public void removeFromAssigned(Issue obj) {
        removeToManyTarget("assigned", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Issue> getAssigned() {
        return (List<Issue>)readProperty("assigned");
    }


    public void addToComments(Comment obj) {
        addToManyTarget("comments", obj, true);
    }
    public void removeFromComments(Comment obj) {
        removeToManyTarget("comments", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Comment> getComments() {
        return (List<Comment>)readProperty("comments");
    }


    public void addToProjects(Project obj) {
        addToManyTarget("projects", obj, true);
    }
    public void removeFromProjects(Project obj) {
        removeToManyTarget("projects", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Project> getProjects() {
        return (List<Project>)readProperty("projects");
    }


    public void addToReported(Issue obj) {
        addToManyTarget("reported", obj, true);
    }
    public void removeFromReported(Issue obj) {
        removeToManyTarget("reported", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Issue> getReported() {
        return (List<Issue>)readProperty("reported");
    }


}
