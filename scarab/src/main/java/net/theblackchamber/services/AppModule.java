package net.theblackchamber.services;

import java.sql.Time;

import javax.sql.DataSource;

import net.theblackchamber.constants.ApplicationConstants;
import net.theblackchamber.stacks.SiteStack;

import org.apache.cayenne.access.DataContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.services.compatibility.Compatibility;
import org.apache.tapestry5.services.compatibility.Trait;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.JavaScriptStackSource;

import com.googlecode.flyway.core.Flyway;
import com.googlecode.flyway.core.api.MigrationVersion;

public class AppModule {

	public static void bind(ServiceBinder binder) {

	}

	public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) 
	{
		configuration.add(SymbolConstants.APPLICATION_VERSION, "Scarab-" + ApplicationConstants.APPLICATION_VERSION);
		configuration.add(SymbolConstants.HMAC_PASSPHRASE, "59f81759-b0d2-41d5-9b41-e455ed6cdc78");
		configuration.add(SymbolConstants.BOOTSTRAP_ROOT, "context:/lib/bootstrap");
		configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
		configuration.add(SymbolConstants.PRODUCTION_MODE, "true");
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");
	}

	@Contribute(Compatibility.class)
	public static void disableScriptaculous(MappedConfiguration<Trait, Boolean> configuration) 
	{
		configuration.add(Trait.SCRIPTACULOUS, false);
		configuration.add(Trait.INITIALIZERS, false);
	}

	@Contribute(JavaScriptStackSource.class)
	public static void addStacks(MappedConfiguration<String, JavaScriptStack> configuration) 
	{
		configuration.addInstance("site", SiteStack.class);
	}

	@Startup
	public void runFlyway() {
		DataContext context = DataContext.createDataContext();
		DataSource dataSource = context.getParentDataDomain()
				.getNode("ScarabNode").getDataSource();
		Flyway flyway = new Flyway();

		flyway.setDataSource(dataSource);
		flyway.setInitVersion("1.0");
		flyway.setInitOnMigrate(true);
		flyway.setTarget(MigrationVersion.LATEST);
		flyway.migrate();
	}

	public static void contributeTypeCoercer(
			@SuppressWarnings("rawtypes") Configuration<CoercionTuple> configuration) {
		Coercion<String, Time> coercion = new Coercion<String, Time>() {
			@Override
			public Time coerce(String input) {
				int count = StringUtils.countMatches(input, ":");

				// Rudimentary check of format.
				if (count == 0 || count > 2)
					throw new IllegalArgumentException(
							"Time format must be HH:MM:SS or HH:MM");
				else if (count == 1)
					input += ":00";

				return Time.valueOf(input);
			}
		};

		configuration.add(new CoercionTuple<String, Time>(String.class,
				Time.class, coercion));
	}
}
