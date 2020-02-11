package gs.sidartatech.safecheckout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.jutils.jprocesses.JProcesses;

import gs.sidartatech.safecheckout.api.AppVersionAPI;
import gs.sidartatech.safecheckout.api.AuthenticationAPI;
import gs.sidartatech.safecheckout.domain.AppVersion;
import gs.sidartatech.safecheckout.service.AppService;
import gs.sidartatech.safecheckout.service.AppVersionService;
import gs.sidartatech.safecheckout.service.ServiceFactory;

public class Main {
	private static Logger logger = Logger.getLogger(Main.class.getName());
	public static final String API_URL = "http://127.0.0.1:9797/safe-checkout/api/";
	public static String token = "";
	public static String versionPath;
	public static String appPath;
	public static void main(String[] args) {
		try {
			InputStream is = Main.class.getResourceAsStream("/logging.properties");
			LogManager.getLogManager().readConfiguration(is);
			String path = args[0];
			versionPath = path + "/version";
			appPath = path + "/safe-checkout.jar";
			FileHandler fh = new FileHandler(path + "/get-version.log");
	        SimpleFormatter sf = new SimpleFormatter();
	        fh.setFormatter(sf);
	        Logger.getLogger("").addHandler(fh);
	        logger.log(Level.INFO, "Starting Safe-CheckOut");
	        logger.log(Level.INFO, "Connect to Server...");
	        AuthenticationAPI authenticationAPI = new AuthenticationAPI();
	        authenticationAPI.authentication();
	        logger.log(Level.INFO, "Connect to Server...(OK)");
	        logger.log(Level.INFO, "Get Versions on Server...");
	        AppVersionAPI appVersionAPI = new AppVersionAPI();
	        List<AppVersion> appVersions = appVersionAPI.getAll();
	        logger.log(Level.INFO, "Get Versions on Server...(OK)");
	        logger.log(Level.INFO, "Update Versions...");
	        
	        logger.log(Level.INFO, "Clear Directory: " + versionPath + "...");
	        File[] files = new File(versionPath).listFiles();
	        if(files!=null) {
	            for(File f: files) {
	                if(!f.isDirectory() && !f.getName().equals("version.txt")) {
	                    f.delete();
	                }
	            }
	        }
	        logger.log(Level.INFO, "Clear Directory: " + versionPath + "...(OK)");
	        
	        AppService appService = ServiceFactory.getInstance().getAppService();
	        AppVersionService appVersionService = ServiceFactory.getInstance().getAppVersionService();
	       
	        for (AppVersion appVersion: appVersions) {
	        	if (appService.getByKey(appVersion.getApp().getAppId()) != null) {
					appService.update(appVersion.getApp());
				} else {
					appService.insert(appVersion.getApp());
				}
				if (appVersionService.getByKey(appVersion.getAppVersionId()) != null) {
					appVersionService.update(appVersion);
				} else {
					appVersionService.insert(appVersion);
				}
	        }
	        logger.log(Level.INFO, "Update Versions...(OK)");
	        
	        logger.log(Level.INFO, "Checking Current Version...");
	        try(BufferedReader br = new BufferedReader(new FileReader(new File(versionPath + "/version.txt")))){
				String currentVersion = "";
				String line = "";
				Integer pid = null;
				Integer companyId = null;
				while((line = br.readLine()) != null) {
					if (line != null) {
						currentVersion = line.trim();
						String[] s = currentVersion.split(";");
						currentVersion = s[0];
						pid = Integer.parseInt(s[1]);
						companyId = Integer.parseInt(s[2]);
					}
				}
				logger.log(Level.INFO, "Current Version: " + currentVersion);
				AppVersion appVersion = appVersionService.getLastVersion(1);
				if (!appVersion.getVersionNumber().equals(currentVersion)) {
					logger.log(Level.INFO, "Application version different from the latest version");
					logger.log(Level.INFO, "Application version: " + currentVersion);
					logger.log(Level.INFO, "Latest application version: " + appVersion.getVersionNumber());
					logger.log(Level.INFO, "Shut Down Application SafeCheckout Server...");
					JProcesses.killProcess(pid);
					logger.log(Level.INFO, "Shut Down Application SafeCheckout Server...(OK)");
					
					logger.log(Level.INFO, "Remove Old Version of Application...");
					
					Files.delete(Paths.get(appPath));
					
					logger.log(Level.INFO, "Remove Old Version of Application...(OK)");
					
					logger.log(Level.INFO, "Replace Application to new Version...");
					try{
			    		
			    	    File appFileVersion = new File(appVersion.getVersionPath());
			    	    File newAppFileVersion = new File(appPath);
			    		
			    	    InputStream fis = new FileInputStream(appFileVersion);
			    	    OutputStream os = new FileOutputStream(newAppFileVersion);
			        	
			    	    byte[] buffer = new byte[1024];
			    	    int length; 
			    	    while ((length = fis.read(buffer)) > 0){
			    	    	os.write(buffer, 0, length);
			    	    }
			    	    fis.close();
			    	    os.close();
			    	    logger.log(Level.INFO, "Replace Application to new Version...(OK)");
			    	    
			    	    logger.log(Level.INFO, "Starting new Version...");
			    	    
			    	    List<String> actualArgs = new ArrayList<String>();
			    	    actualArgs.add(0, "java");
			    	    actualArgs.add(1, "-jar");
			    	    actualArgs.add(2, appPath);
			    	    actualArgs.add(3, path);
			    	    actualArgs.add(4, companyId.toString());
			    	    
			    	    try {
			    	        final Runtime re = Runtime.getRuntime();
			    	        re.exec(actualArgs.toArray(new String[0]));
			    	        logger.log(Level.INFO, "Starting new Version...(OK)");
			    	    } catch (Exception e) {
			    	    	logger.log(Level.SEVERE, e.getMessage(), e);
			    	    }
			    	}catch(IOException e){
			    		logger.log(Level.SEVERE, e.getMessage(), e);
			    	}
					
				}
			} catch (FileNotFoundException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
	        logger.log(Level.INFO, "Checking Current Version...(OK)");
	        logger.log(Level.INFO, "Stop Safe-CheckOut");
		} catch (SecurityException | IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
