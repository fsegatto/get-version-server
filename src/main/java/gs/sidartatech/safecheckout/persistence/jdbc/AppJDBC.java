package gs.sidartatech.safecheckout.persistence.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import gs.sidartatech.safecheckout.domain.App;
import gs.sidartatech.safecheckout.persistence.PersistenceDAO;
import gs.sidartatech.safecheckout.util.ReadFile;

public class AppJDBC extends AbstractJDBC implements PersistenceDAO<Integer, App>{
	
	private static Logger logger = Logger.getLogger(AppJDBC.class.getName());
	
	private final String INSERT_SQL_FILE = "insertApp.sql";
	private final String UPDATE_SQL_FILE = "updateApp.sql";
	private final String SELECT_SQL_FILE = "selectApp.sql";
	private final String SELECT_ALL_SQL_FILE = "selectAllApp.sql";
	private String sqlInsert;
	private String sqlUpdate;
	private String sqlSelect;
	private String sqlSelectAll;
	
	public AppJDBC() {
		super();
	}
	
	@Override
	public App getByKey(Integer k) {
		if(sqlSelect == null) {
			sqlSelect = ReadFile.readSqlFile(SELECT_SQL_FILE);
		}
		App a = null;
		try(PreparedStatement ps = con.prepareStatement(sqlSelect)) {
			ps.setInt(1, k);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				a = new App();
				a.setAppId(rs.getInt("app_id"));
				a.setAppName(rs.getString("app_name"));
				a.setFileName(rs.getString("file_name"));
				a.setReplaceNow(rs.getString("replace_now"));
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return a;
	}

	@Override
	public void insert(App app) {
		if(sqlInsert == null) {
			sqlInsert = ReadFile.readSqlFile(INSERT_SQL_FILE);
		}

		try (PreparedStatement ps = con.prepareStatement(sqlInsert)){
			ps.setInt(1, app.getAppId());
			ps.setString(2, app.getAppName());
			ps.setString(3, app.getFileName());
			ps.setString(4, app.getReplaceNow());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	@Override
	public void update(App app) {
		if(sqlUpdate == null) {
			sqlUpdate = ReadFile.readSqlFile(UPDATE_SQL_FILE);
		}
		
		try (PreparedStatement ps = con.prepareStatement(sqlUpdate)){
			ps.setString(1, app.getAppName());
			ps.setString(2, app.getFileName());
			ps.setString(3, app.getReplaceNow());
			ps.setInt(4, app.getAppId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public List<App> getAll() {
		if(sqlSelectAll == null) {
			sqlSelectAll = ReadFile.readSqlFile(SELECT_ALL_SQL_FILE);
		}
		List<App> list = new ArrayList<App>();
		try(Statement s = con.createStatement()) {
			ResultSet rs = s.executeQuery(sqlSelectAll);
			while(rs.next()) {
				App app = new App();
				app.setAppId(rs.getInt("app_id"));
				app.setAppName(rs.getString("app_name"));
				app.setFileName(rs.getString("file_name"));
				app.setReplaceNow(rs.getString("replace_now"));
				list.add(app);
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return list;
	}
}