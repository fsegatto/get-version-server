package gs.sidartatech.safecheckout.api;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import gs.sidartatech.safecheckout.domain.AppVersion;

public class AppVersionAPI extends ConnectionAPI<List<AppVersion>, List<AppVersion>> {
	private Logger logger = Logger.getLogger(AppVersionAPI.class.getName());
	public List<AppVersion> getAll() {
		List<AppVersion> appVersions = null;
		try {
			appVersions = get("app-version/all", null);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		}
		return appVersions;
	}
}
