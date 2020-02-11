package gs.sidartatech.safecheckout.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadFile {
	
	private static Logger logger = Logger.getLogger(ReadFile.class.getName());
	private static final String PATH_SQL_FILE = "/sql/";
	public static String readSqlFile(String fileName) {
		String content = null;
		InputStream is = ReadFile.class.getResourceAsStream(PATH_SQL_FILE + fileName);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String line = "";
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			content = sb.toString();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return content;
	}
}
