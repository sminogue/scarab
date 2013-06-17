package net.theblackchamber.utility;

import org.apache.cayenne.access.DataContext;

public class CayenneUtility {

	public static DataContext createContext() {
		return DataContext.createDataContext();
	}

}
