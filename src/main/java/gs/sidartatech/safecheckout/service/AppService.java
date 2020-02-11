package gs.sidartatech.safecheckout.service;

import java.util.List;

import gs.sidartatech.safecheckout.domain.App;
import gs.sidartatech.safecheckout.persistence.PersistenceDAO;
import gs.sidartatech.safecheckout.persistence.PersistenceFactory;

public class AppService implements AbstractService<Integer, App>{

	private PersistenceDAO<Integer, App> dao;
	
	public AppService() {
		dao = PersistenceFactory.getInstance().getAppJDBC();
	}
	
	@Override
	public void insert(App e) {
		
		dao.insert(e);
	}
	
	@Override
	public void update(App e) {
		dao.update(e);
	}

	@Override
	public App getByKey(Integer k) {
		return dao.getByKey(k);
	}
	@Override
	public List<App> getAll() {
		return dao.getAll();
	}
}
