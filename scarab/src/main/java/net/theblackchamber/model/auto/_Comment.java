package net.theblackchamber.model.auto;

import java.util.Date;

import org.apache.cayenne.CayenneDataObject;

/**
 * Class _Comment was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Comment extends CayenneDataObject {

    public static final String CREATED_DATE_PROPERTY = "createdDate";
    public static final String TEXT_PROPERTY = "text";

    public static final String ID_PK_COLUMN = "id";

    public void setCreatedDate(Date createdDate) {
        writeProperty("createdDate", createdDate);
    }
    public Date getCreatedDate() {
        return (Date)readProperty("createdDate");
    }

    public void setText(String text) {
        writeProperty("text", text);
    }
    public String getText() {
        return (String)readProperty("text");
    }

}
