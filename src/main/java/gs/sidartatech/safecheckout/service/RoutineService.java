package gs.sidartatech.safecheckout.service;

import java.util.List;

import gs.sidartatech.safecheckout.domain.Routine;
import gs.sidartatech.safecheckout.persistence.PersistenceDAO;
import gs.sidartatech.safecheckout.persistence.PersistenceFactory;

public class RoutineService implements AbstractService<Integer, Routine>{

	private PersistenceDAO<Integer, Routine> dao;
	
	public RoutineService() {
		dao = PersistenceFactory.getInstance().getRoutineJDBC();
	}
	
	@Override
	public void insert(Routine e) {
		dao.insert(e);
	}
	
	@Override
	public void update(Routine e) {
		dao.update(e);
	}

	@Override
	public Routine getByKey(Integer k) {
		return dao.getByKey(k);
	}
	@Override
	public List<Routine> getAll() {
		return dao.getAll();
	}
}
