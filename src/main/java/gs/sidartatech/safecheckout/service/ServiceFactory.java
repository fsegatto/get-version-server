package gs.sidartatech.safecheckout.service;

public class ServiceFactory {
	private static ServiceFactory instance;
	private AppService appService;
	private AppVersionService appVersionService;
	
	private ServiceFactory() {
		
	}
	
	public static synchronized ServiceFactory getInstance() {
		if(instance == null) {
			instance = new ServiceFactory();
		}
		return instance;
	}
	
	public AppService getAppService() {
		if(appService == null) {
			appService = new AppService();
		}
		return appService;
	}
	
	public AppVersionService getAppVersionService() {
		if(appVersionService == null) {
			appVersionService = new AppVersionService();
		}
		return appVersionService;
	}
}
