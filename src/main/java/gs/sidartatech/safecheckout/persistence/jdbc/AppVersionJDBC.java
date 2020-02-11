package gs.sidartatech.safecheckout.persistence.jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import gs.sidartatech.safecheckout.domain.App;
import gs.sidartatech.safecheckout.domain.AppVersion;
import gs.sidartatech.safecheckout.persistence.PersistenceDAO;
import gs.sidartatech.safecheckout.util.ReadFile;

public class AppVersionJDBC extends AbstractJDBC implements PersistenceDAO<Integer, AppVersion>{
	
	private static Logger logger = Logger.getLogger(AppVersionJDBC.class.getName());
	
	private final String INSERT_SQL_FILE = "insertAppVersion.sql";
	private final String UPDATE_SQL_FILE = "updateAppVersion.sql";
	private final String SELECT_SQL_FILE = "selectAppVersion.sql";
	private final String SELECT_LAST_SQL_FILE = "selectLastAppVersion.sql";
	private String sqlInsert;
	private String sqlUpdate;
	private String sqlSelect;
	private String sqlSelectLast;
	
	public AppVersionJDBC() {
		super();
	}
	
	@Override
	public AppVersion getByKey(Integer k) {
		if(sqlSelect == null) {
			sqlSelect = ReadFile.readSqlFile(SELECT_SQL_FILE);
		}
		AppVersion appVersion = null;
		try(PreparedStatement ps = con.prepareStatement(sqlSelect)) {
			ps.setInt(1, k);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				appVersion = new AppVersion();
				appVersion.setAppVersionId(rs.getInt("app_version_id"));
				App app = new App();
				app.setAppId(rs.getInt("app_id"));
				app.setAppName(rs.getString("app_name"));
				appVersion.setApp(app);
				appVersion.setVersionNumber(rs.getString("version_number"));
				appVersion.setVersionDate(rs.getDate("version_date"));
				appVersion.setVersionPath(rs.getString("version_path"));
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return appVersion;
	}

	@Override
	public void insert(AppVersion appVersion) {
		if(sqlInsert == null) {
			sqlInsert = ReadFile.readSqlFile(INSERT_SQL_FILE);
		}

		try (PreparedStatement ps = con.prepareStatement(sqlInsert)){
			ps.setInt(1, appVersion.getAppVersionId());
			ps.setInt(2, appVersion.getApp().getAppId());
			ps.setString(3, appVersion.getVersionNumber());
			ps.setDate(4, new Date(appVersion.getVersionDate().getTime()));
			ps.setString(5, appVersion.getVersionPath());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public void update(AppVersion appVersion) {
		if(sqlUpdate == null) {
			sqlUpdate = ReadFile.readSqlFile(UPDATE_SQL_FILE);
		}
		
		try (PreparedStatement ps = con.prepareStatement(sqlUpdate)){
			ps.setInt(1, appVersion.getApp().getAppId());
			ps.setString(2, appVersion.getVersionNumber());
			ps.setDate(3, new Date(appVersion.getVersionDate().getTime()));
			ps.setString(4, appVersion.getVersionPath());
			ps.setInt(5, appVersion.getAppVersionId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	public AppVersion getLastVersion(Integer appId) {
		if(sqlSelectLast == null) {
			sqlSelectLast = ReadFile.readSqlFile(SELECT_LAST_SQL_FILE);
		}
		AppVersion appVersion = null;
		try(PreparedStatement ps = con.prepareStatement(sqlSelectLast)) {
			ps.setInt(1, appId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				appVersion = new AppVersion();
				appVersion.setAppVersionId(rs.getInt("app_version_id"));
				App app = new App();
				app.setAppId(rs.getInt("app_id"));
				app.setAppName(rs.getString("app_name"));
				appVersion.setApp(app);
				appVersion.setVersionNumber(rs.getString("version_number"));
				appVersion.setVersionDate(rs.getDate("version_date"));
				appVersion.setVersionPath(rs.getString("version_path"));
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return appVersion;
	}

	@Override
	public List<AppVersion> getAll() {
		return null;
	}
}