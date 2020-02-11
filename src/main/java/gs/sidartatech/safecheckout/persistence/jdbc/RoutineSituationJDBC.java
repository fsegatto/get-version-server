package gs.sidartatech.safecheckout.persistence.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import gs.sidartatech.safecheckout.domain.RoutineSituation;
import gs.sidartatech.safecheckout.persistence.PersistenceDAO;
import gs.sidartatech.safecheckout.util.ReadFile;

public class RoutineSituationJDBC extends AbstractJDBC implements PersistenceDAO<Integer, RoutineSituation>{
	
	private static Logger logger = Logger.getLogger(RoutineSituationJDBC.class.getName());
	
	private final String SELECT_SQL_FILE = "selectRoutineSituation.sql";
	private String sqlSelect;
	
	public RoutineSituationJDBC() {
		super();
	}
	
	@Override
	public RoutineSituation getByKey(Integer k) {
		if(sqlSelect == null) {
			sqlSelect = ReadFile.readSqlFile(SELECT_SQL_FILE);
		}
		RoutineSituation r = null;
		try(PreparedStatement ps = con.prepareStatement(sqlSelect)) {
			ps.setInt(1, k);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				r = new RoutineSituation();
				r.setRoutineSituationId(rs.getInt("routine_situation_id"));
				r.setRoutineSituationName(rs.getString("routine_situation_name"));
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return r;
	}

	@Override
	public void insert(RoutineSituation rs) {

	}
	
	@Override
	public void update(RoutineSituation rs) {

	}

	@Override
	public List<RoutineSituation> getAll() {
		return null;
	}
}