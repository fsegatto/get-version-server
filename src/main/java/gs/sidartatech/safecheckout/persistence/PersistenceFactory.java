package gs.sidartatech.safecheckout.persistence;

import gs.sidartatech.safecheckout.persistence.jdbc.AppJDBC;
import gs.sidartatech.safecheckout.persistence.jdbc.AppVersionJDBC;
import gs.sidartatech.safecheckout.persistence.jdbc.ControlExecutionJDBC;
import gs.sidartatech.safecheckout.persistence.jdbc.RoutineJDBC;
import gs.sidartatech.safecheckout.persistence.jdbc.RoutineSituationJDBC;

public class PersistenceFactory {
	private static PersistenceFactory instance;
	
	private RoutineJDBC routineJDBC;
	private RoutineSituationJDBC routineSituationJDBC;
	private ControlExecutionJDBC controlExecutionJDBC;
	private AppJDBC appJDBC;
	private AppVersionJDBC appVersionJDBC;
	
	private PersistenceFactory() {
		
	}
	
	public static synchronized PersistenceFactory getInstance() {
		if(instance == null) {
			instance = new PersistenceFactory();
		}
		return instance;
	}
	
	public RoutineJDBC getRoutineJDBC() {
		if(routineJDBC == null) {
			routineJDBC = new RoutineJDBC();
		}
		return routineJDBC;
	}
	
	public RoutineSituationJDBC getRoutineSituationJDBC() {
		if(routineSituationJDBC == null) {
			routineSituationJDBC = new RoutineSituationJDBC();
		}
		return routineSituationJDBC;
	}
	
	public ControlExecutionJDBC getControlExecutionJDBC() {
		if(controlExecutionJDBC == null) {
			controlExecutionJDBC = new ControlExecutionJDBC();
		}
		return controlExecutionJDBC;
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