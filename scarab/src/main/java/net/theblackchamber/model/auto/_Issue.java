package net.theblackchamber.model.auto;

import java.util.Date;
import java.util.List;

import org.apache.cayenne.CayenneDataObject;

import net.theblackchamber.model.Comment;

/**
 * Class _Issue was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Issue extends CayenneDataObject {

    public static final String ASSIGNED_PROPERTY = "assigned";
    public static final String CREATED_DATE_PROPERTY = "createdDate";
    public static final String DESCRIPTION_PROPERTY = "description";
    public static final String STATUS_PROPERTY = "status";
    public static final String SUBJECT_PROPERTY = "subject";
    public static final String COMMENTS_PROPERTY = "comments";

    public static final String ID_PK_COLUMN = "ID";

    public void setAssigned(Integer assigned) {
        writeProperty("assigned", assigned);
    }
    public Integer getAssigned() {
        return (Integer)readProperty("assigned");
    }

    public void setCreatedDate(Date createdDate) {
        writeProperty("createdDate", createdDate);
    }
    public Date getCreatedDate() {
        return (Date)readProperty("createdDate");
    }

    public void setDescription(String description) {
        writeProperty("description", description);
    }
    public String getDescription() {
        return (String)readProperty("description");
    }

    public void setStatus(String status) {
        writeProperty("status", status);
    }
    public String getStatus() {
        return (String)readProperty("status");
    }

    public void setSubject(String subject) {
        writeProperty("subject", subject);
    }
    public String getSubject() {
        return (String)readProperty("subject");
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


}
