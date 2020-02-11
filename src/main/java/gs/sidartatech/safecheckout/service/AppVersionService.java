package gs.sidartatech.safecheckout.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import gs.sidartatech.safecheckout.Main;
import gs.sidartatech.safecheckout.domain.AppVersion;
import gs.sidartatech.safecheckout.persistence.PersistenceDAO;
import gs.sidartatech.safecheckout.persistence.PersistenceFactory;

public class AppVersionService implements AbstractService<Integer, AppVersion>{

	private PersistenceDAO<Integer, AppVersion> dao;
	
	public AppVersionService() {
		dao = PersistenceFactory.getInstance().getAppVersionJDBC();
	}
	
	@Override
	public void insert(AppVersion e) {
		e.setVersionPath(saveFile(e));
		dao.insert(e);
	}
	
	@Override
	public void update(AppVersion e) {
		e.setVersionPath(saveFile(e));
		dao.update(e);
	}

	@Override
	public AppVersion getByKey(Integer k) {
		return dao.getByKey(k);
	}
	@Override
	public List<AppVersion> getAll() {
		return dao.getAll();
	}
	
	public AppVersion getLastVersion(Integer appId) {
		return PersistenceFactory.getInstance().getAppVersionJDBC().getLastVersion(appId);
	}
	
	private String saveFile(AppVersion appVersion) {
		String path = Main.versionPath + "/" + appVersion.getVersionFileName();
		byte[] data = Base64.getDecoder().decode(appVersion.getFileBase64().getBytes(StandardCharsets.UTF_8));
		try (OutputStream stream = new FileOutputStream(path)) {
		    stream.write(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
