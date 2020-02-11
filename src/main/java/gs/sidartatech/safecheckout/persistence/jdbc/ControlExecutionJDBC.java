package gs.sidartatech.safecheckout.persistence.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import gs.sidartatech.safecheckout.domain.ControlExecution;
import gs.sidartatech.safecheckout.persistence.PersistenceDAO;
import gs.sidartatech.safecheckout.util.ReadFile;

public class ControlExecutionJDBC extends AbstractJDBC implements PersistenceDAO<Integer, ControlExecution>{
	
	private static Logger logger = Logger.getLogger(ControlExecutionJDBC.class.getName());
	
	private final String INSERT_SQL_FILE = "insertControlExecution.sql";
	private final String UPDATE_SQL_FILE = "updateControlExecution.sql";
	private String sqlInsert;
	private String sqlUpdate;
	
	public ControlExecutionJDBC() {
		super();
	}
	
	@Override
	public ControlExecution getByKey(Integer k) {
		return null;
	}

	@Override
	public void insert(ControlExecution ce) {
		if(sqlInsert == null) {
			sqlInsert = ReadFile.readSqlFile(INSERT_SQL_FILE);
		}
		try (PreparedStatement ps = con.prepareStatement(sqlInsert)){
			ps.setInt(1, ce.getRoutine().getRoutineId());
			ps.setInt(2, ce.getRoutineSituation().getRoutineSituationId());
			ps.setTimestamp(3, new Timestamp(ce.getStartTime().getTime()));
			ps.setNull(4, Types.TIMESTAMP);
			ps.setString(5, ce.getDescription());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	public ControlExecution saveWithReturn(ControlExecution ce) {
		if(sqlInsert == null) {
			sqlInsert = ReadFile.readSqlFile(INSERT_SQL_FILE);
		}
		try (PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)){
			ps.setInt(1, ce.getRoutine().getRoutineId());
			ps.setInt(2, ce.getRoutineSituation().getRoutineSituationId());
			ps.setTimestamp(3, new Timestamp(ce.getStartTime().getTime()));
			ps.setNull(4, Types.TIMESTAMP);
			ps.setString(5, ce.getDescription());
			
			int affectedRows = ps.executeUpdate();
			
			if (affectedRows == 0) {
	            throw new SQLException("Creating Sale failed, no rows affected.");
	        }

	        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	ce.setControlExecutionId(generatedKeys.getInt(1));
	            }else {
	                throw new SQLException("Creating Sale failed, no ID obtained.");
	            }
	        }
			
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return ce;
	}
	
	@Override
	public void update(ControlExecution ce) {
		if(sqlUpdate == null) {
			sqlUpdate = ReadFile.readSqlFile(UPDATE_SQL_FILE);
		}
		
		try (PreparedStatement ps = con.prepareStatement(sqlUpdate)){
			ps.setInt(1, ce.getRoutine().getRoutineId());
			ps.setInt(2, ce.getRoutineSituation().getRoutineSituationId());
			ps.setTimestamp(3, new Timestamp(ce.getStartTime().getTime()));
			ps.setTimestamp(4, new Timestamp(ce.getEndTime().getTime()));
			ps.setString(5, ce.getDescription());
			ps.setInt(6, ce.getControlExecutionId());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public List<ControlExecution> getAll() {
		return null;
	}
}