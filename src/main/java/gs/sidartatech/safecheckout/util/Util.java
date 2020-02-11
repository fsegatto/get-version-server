package gs.sidartatech.safecheckout.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Util {
	private Util() {
		
	}
	
	public static void base64ToFile(String base64, String path, String fileName) {
		byte[] data = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
		try (OutputStream stream = new FileOutputStream(path + "/" + fileName)) {
		    stream.write(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
