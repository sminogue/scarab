package net.theblackchamber.model.auto;

import java.util.List;

import org.apache.cayenne.CayenneDataObject;

import net.theblackchamber.model.IssueLabel;

/**
 * Class _Label was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Label extends CayenneDataObject {

    public static final String COLOR_PROPERTY = "color";
    public static final String LABEL_PROPERTY = "label";
    public static final String ISSUE_LABELS_PROPERTY = "issueLabels";

    public static final String ID_PK_COLUMN = "id";

    public void setColor(String color) {
        writeProperty("color", color);
    }
    public String getColor() {
        return (String)readProperty("color");
    }

    public void setLabel(String label) {
        writeProperty("label", label);
    }
    public String getLabel() {
        return (String)readProperty("label");
    }

    public void addToIssueLabels(IssueLabel obj) {
        addToManyTarget("issueLabels", obj, true);
    }
    public void removeFromIssueLabels(IssueLabel obj) {
        removeToManyTarget("issueLabels", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<IssueLabel> getIssueLabels() {
        return (List<IssueLabel>)readProperty("issueLabels");
    }


}