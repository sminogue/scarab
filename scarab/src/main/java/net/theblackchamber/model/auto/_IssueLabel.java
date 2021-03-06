package net.theblackchamber.model.auto;

import org.apache.cayenne.CayenneDataObject;

import net.theblackchamber.model.Issue;

/**
 * Class _IssueLabel was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _IssueLabel extends CayenneDataObject {

    public static final String TO_ISSUE_PROPERTY = "toIssue";

    public static final String ID_PK_COLUMN = "id";

    public void setToIssue(Issue toIssue) {
        setToOneTarget("toIssue", toIssue, true);
    }

    public Issue getToIssue() {
        return (Issue)readProperty("toIssue");
    }


}
