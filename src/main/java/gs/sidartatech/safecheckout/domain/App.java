package gs.sidartatech.safecheckout.domain;

import java.io.Serializable;

public class App implements Serializable{

	private static final long serialVersionUID = -9078035620617202631L;
	private Integer appId;
	private String appName;
	private String fileName;
	private String replaceNow;
    
    public App() {
    	
    }

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getReplaceNow() {
		return replaceNow;
	}

	public void setReplaceNow(String replaceNow) {
		this.replaceNow = replaceNow;
	}

}