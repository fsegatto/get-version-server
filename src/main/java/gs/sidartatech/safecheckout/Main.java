package gs.sidartatech.safecheckout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.jutils.jprocesses.JProcesses;

import gs.sidartatech.safecheckout.api.AppVersionAPI;
import gs.sidartatech.safecheckout.api.AuthenticationAPI;
import gs.sidartatech.safecheckout.domain.App;
import gs.sidartatech.safecheckout.domain.AppVersion;
import gs.sidartatech.safecheckout.domain.ControlExecution;
import gs.sidartatech.safecheckout.domain.Routine;
import gs.sidartatech.safecheckout.domain.RoutineSituation;
import gs.sidartatech.safecheckout.service.AppService;
import gs.sidartatech.safecheckout.service.AppVersionService;
import gs.sidartatech.safecheckout.service.ControlExecutionService;
import gs.sidartatech.safecheckout.service.RoutineService;
import gs.sidartatech.safecheckout.service.RoutineSituationService;
import gs.sidartatech.safecheckout.service.ServiceFactory;

public class Main {
	private static Logger logger = Logger.getLogger(Main.class.getName());
	public static final String API_URL = "http://191.252.222.32:9797/safe-checkout/api/";
	private static final Integer ROUTINE_ID = 1;
	private static final String VERSION = "1.0.0.1";
	public static String token = "";
	public static String versionPath;
	public static String appPath;
	private static RoutineService routineService;
	private static RoutineSituationService routineSituationService;
	private static ControlExecutionService controlExecutionService;
	
	public static void main(String[] args) {
		boolean endOK = true;
		ControlExecution controlExecution = null;
		String routineName = "";
		String messageError = "";
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
	        
	        logger.log(Level.INFO, "Remove File Version...");
			File versionFile = new File(versionPath + "/get-version.txt");
			versionFile.delete();
			logger.log(Level.INFO, "Remove File Version...(OK)");
			logger.log(Level.INFO, "Create new File Version...");
			versionFile.createNewFile();
			FileWriter fw = new FileWriter(versionFile);
			fw.write(VERSION);
			fw.close();
			logger.log(Level.INFO, "Create new File Version...(OK)");
	        
	        routineService = ServiceFactory.getInstance().getRoutineService();
	        Routine routine = routineService.getByKey(ROUTINE_ID);
	        routineName = routine.getRoutineName();
	        routineSituationService = ServiceFactory.getInstance().getRoutineSituationService();
	        RoutineSituation routineSituation = routineSituationService.getByKey(2);
	        controlExecution = new ControlExecution();
	        controlExecution.setRoutine(routine);
	        controlExecution.setRoutineSituation(routineSituation);
	        controlExecution.setStartTime(new Date());
	        controlExecution.setDescription("Starting Routine: " + routine.getRoutineName());
	        controlExecutionService = ServiceFactory.getInstance().getControlExecutionService();
	        controlExecution = controlExecutionService.saveWithReturn(controlExecution);
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
	                if(!f.isDirectory() && !f.getName().equals("version.txt") && !f.getName().equals("get-version.txt") && !f.getName().equals("send-sale.txt")) {
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
	        
	        for (App app: appService.getAll()) {
	        	if (app.getReplaceNow().equals("Y")) {
	        		String appVersionFile = path + "/version/" + app.getFileName().split("\\.")[0] + ".txt";
	        		if(Files.exists(Paths.get(appVersionFile))) {
	        			try(BufferedReader br = new BufferedReader(new FileReader(new File(appVersionFile)))){
		        			String currentVersion = "";
		    				String line = "";
		    				while((line = br.readLine()) != null) {
		    					if (line != null) {
		    						currentVersion = line.trim();
		    					}
		    				}
		    				AppVersion appVersion = appVersionService.getLastVersion(app.getAppId());
							if (!appVersion.getVersionNumber().equals(currentVersion)) {
								if (app.getAppId().equals(3)) {
									String changeVersionFile = path + "/version/change-version-file.txt";
									Files.createFile(Paths.get(changeVersionFile));
									FileWriter fileWriter = new FileWriter(changeVersionFile);
									fileWriter.write(appVersion.getVersionPath());
									fileWriter.close();
								} else {
									Files.delete(Paths.get(path + "/" + app.getFileName()));
									File appFileVersion = new File(appVersion.getVersionPath());
						    	    File newAppFileVersion = new File(path + "/" + app.getFileName());
						    		
						    	    InputStream fis = new FileInputStream(appFileVersion);
						    	    OutputStream os = new FileOutputStream(newAppFileVersion);
						        	
						    	    byte[] buffer = new byte[1024];
						    	    int length; 
						    	    while ((length = fis.read(buffer)) > 0){
						    	    	os.write(buffer, 0, length);
						    	    }
						    	    fis.close();
						    	    os.close();
								}
							}
		        		} catch (FileNotFoundException e) {
		    				endOK = false;
		    				messageError = e.getMessage();
		    				logger.log(Level.SEVERE, e.getMessage(), e);
		    			} catch (IOException e) {
		    				endOK = false;
		    				messageError = e.getMessage();
		    				logger.log(Level.SEVERE, e.getMessage(), e);
		    			}
	        		}
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
			    	    	messageError = e.getMessage();
			    	    	endOK = false;
			    	    	logger.log(Level.SEVERE, e.getMessage(), e);
			    	    }
			    	}catch(IOException e){
			    		endOK = false;
			    		messageError = e.getMessage();
			    		logger.log(Level.SEVERE, e.getMessage(), e);
			    	}
				}
			} catch (FileNotFoundException e) {
				endOK = false;
				messageError = e.getMessage();
				logger.log(Level.SEVERE, e.getMessage(), e);
			} catch (IOException e) {
				endOK = false;
				messageError = e.getMessage();
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
	        logger.log(Level.INFO, "Checking Current Version...(OK)");
	        logger.log(Level.INFO, "Stop Safe-CheckOut");
		} catch (SecurityException | IOException e) {
			endOK = false;
			messageError = e.getMessage();
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		if (endOK) {
			stopOK(controlExecution, routineName);
		} else {
			stopNOK(controlExecution, messageError);
		}
	}
	
	private static void stopOK(ControlExecution controlExecution, String routineName) {
		RoutineSituation routineSituation = routineSituationService.getByKey(1);
        controlExecution.setRoutineSituation(routineSituation);
        controlExecution.setEndTime(new Date());
        controlExecution.setDescription("Routine: " + routineName + " END OK");
        controlExecutionService.update(controlExecution);
	}
	
	private static void stopNOK(ControlExecution controlExecution, String message) {
		RoutineSituation routineSituation = routineSituationService.getByKey(1);
        controlExecution.setRoutineSituation(routineSituation);
        controlExecution.setEndTime(new Date());
        controlExecution.setDescription(message);
        controlExecutionService.update(controlExecution);
	}
}
