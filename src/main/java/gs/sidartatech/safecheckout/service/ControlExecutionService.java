package gs.sidartatech.safecheckout.service;

import java.util.List;

import gs.sidartatech.safecheckout.domain.ControlExecution;
import gs.sidartatech.safecheckout.persistence.PersistenceDAO;
import gs.sidartatech.safecheckout.persistence.PersistenceFactory;

public class ControlExecutionService implements AbstractService<Integer, ControlExecution>{

	private PersistenceDAO<Integer, ControlExecution> dao;
	
	public ControlExecutionService() {
		dao = PersistenceFactory.getInstance().getControlExecutionJDBC();
	}
	
	@Override
	public void insert(ControlExecution e) {
		dao.insert(e);
	}
	
	public ControlExecution saveWithReturn(ControlExecution controlExecution) {
		return PersistenceFactory.getInstance().getControlExecutionJDBC().saveWithReturn(controlExecution);
	}
	
	@Override
	public void update(ControlExecution e) {
		dao.update(e);
	}

	@Override
	public ControlExecution getByKey(Integer k) {
		return dao.getByKey(k);
	}
	@Override
	public List<ControlExecution> getAll() {
		return dao.getAll();
	}
}
