package gs.sidartatech.safecheckout.service;

public class ServiceFactory {
	private static ServiceFactory instance;
	private RoutineService routineService;
	private RoutineSituationService routineSituationService;
	private ControlExecutionService controlExecutionService;
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
	
	public RoutineService getRoutineService() {
		if(routineService == null) {
			routineService = new RoutineService();
		}
		return routineService;
	}
	
	public RoutineSituationService getRoutineSituationService() {
		if(routineSituationService == null) {
			routineSituationService = new RoutineSituationService();
		}
		return routineSituationService;
	}
	
	public ControlExecutionService getControlExecutionService() {
		if(controlExecutionService == null) {
			controlExecutionService = new ControlExecutionService();
		}
		return controlExecutionService;
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
