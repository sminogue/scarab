package net.theblackchamber.components;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.internal.InternalConstants;

@Import(stack={InternalConstants.CORE_STACK_NAME, "site"}, module={"bootstrap","setup"},library={"context:js/layout.js"})
public class Layout
{
	
}
