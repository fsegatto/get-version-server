package gs.sidartatech.safecheckout.api;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import gs.sidartatech.safecheckout.Main;
import gs.sidartatech.safecheckout.domain.User;

public class AuthenticationAPI extends ConnectionAPI<User, User> {
	private Logger logger = Logger.getLogger(AuthenticationAPI.class.getName());
	public User authentication() {
		User u = new User();
		u.setUserLogin("sidarta");
		u.setUserPassword("sql");
		try {
			u = post("authenticate", u);
			Main.token = u.getToken();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		}
		return u;
	}
}
