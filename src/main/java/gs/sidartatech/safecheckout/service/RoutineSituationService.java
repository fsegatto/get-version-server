package gs.sidartatech.safecheckout.service;

import java.util.List;

import gs.sidartatech.safecheckout.domain.RoutineSituation;
import gs.sidartatech.safecheckout.persistence.PersistenceDAO;
import gs.sidartatech.safecheckout.persistence.PersistenceFactory;

public class RoutineSituationService implements AbstractService<Integer, RoutineSituation>{

	private PersistenceDAO<Integer, RoutineSituation> dao;
	
	public RoutineSituationService() {
		dao = PersistenceFactory.getInstance().getRoutineSituationJDBC();
	}
	
	@Override
	public void insert(RoutineSituation e) {
		
		dao.insert(e);
	}
	
	@Override
	public void update(RoutineSituation e) {
		dao.update(e);
	}

	@Override
	public RoutineSituation getByKey(Integer k) {
		return dao.getByKey(k);
	}
	@Override
	public List<RoutineSituation> getAll() {
		return dao.getAll();
	}
}
