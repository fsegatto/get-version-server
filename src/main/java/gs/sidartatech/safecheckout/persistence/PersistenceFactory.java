package gs.sidartatech.safecheckout.persistence;

import gs.sidartatech.safecheckout.persistence.jdbc.AppJDBC;
import gs.sidartatech.safecheckout.persistence.jdbc.AppVersionJDBC;

public class PersistenceFactory {
	private static PersistenceFactory instance;
	private static AppJDBC appJDBC;
	private static AppVersionJDBC appVersionJDBC;
	
	private PersistenceFactory() {
		
	}
	
	public static synchronized PersistenceFactory getInstance() {
		if(instance == null) {
			instance = new PersistenceFactory();
		}
		return instance;
	}
	
	public AppJDBC getAppJDBC() {
		if(appJDBC == null) {
			appJDBC = new AppJDBC();
		}
		return appJDBC;
	}
	
	public AppVersionJDBC getAppVersionJDBC() {
		if(appVersionJDBC == null) {
			appVersionJDBC = new AppVersionJDBC();
		}
		return appVersionJDBC;
	}
}