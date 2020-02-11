package gs.sidartatech.safecheckout.persistence.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import gs.sidartatech.safecheckout.domain.Routine;
import gs.sidartatech.safecheckout.persistence.PersistenceDAO;
import gs.sidartatech.safecheckout.util.ReadFile;

public class RoutineJDBC extends AbstractJDBC implements PersistenceDAO<Integer, Routine>{
	
	private static Logger logger = Logger.getLogger(RoutineJDBC.class.getName());
	
	private final String SELECT_SQL_FILE = "selectRoutine.sql";
	private String sqlSelect;
	
	public RoutineJDBC() {
		super();
	}
	
	@Override
	public Routine getByKey(Integer k) {
		if(sqlSelect == null) {
			sqlSelect = ReadFile.readSqlFile(SELECT_SQL_FILE);
		}
		Routine r = null;
		try(PreparedStatement ps = con.prepareStatement(sqlSelect)) {
			ps.setInt(1, k);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				r = new Routine();
				r.setRoutineId(rs.getInt("routine_id"));
				r.setRoutineName(rs.getString("routine_name"));
				r.setFrequency(rs.getString("frequency"));
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return r;
	}

	@Override
	public void insert(Routine routine) {
		
	}
	
	@Override
	public void update(Routine routine) {

	}

	@Override
	public List<Routine> getAll() {
		return null;
	}
}