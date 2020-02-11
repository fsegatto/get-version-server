package gs.sidartatech.safecheckout.persistence.jdbc;

import java.sql.Connection;

import gs.sidartatech.safecheckout.persistence.ConnectionFactory;

public abstract class AbstractJDBC {
	protected Connection con;
	
	public AbstractJDBC() {
		con = ConnectionFactory.getConnection();
	}
}
