package gs.sidartatech.safecheckout.domain;

import java.io.Serializable;
import java.util.Date;

public class AppVersion implements Serializable{

	private static final long serialVersionUID = 6959254125073882172L;
	private Integer appVersionId;
	private App app;
	private String versionNumber;
	private Date versionDate;
	private String versionPath;
	private String versionFileName;
	private String fileBase64;
	
    public AppVersion() {
    	
    }

	public Integer getAppVersionId() {
		return appVersionId;
	}

	public void setAppVersionId(Integer appVersionId) {
		this.appVersionId = appVersionId;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public Date getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}

	public String getVersionPath() {
		return versionPath;
	}

	public void setVersionPath(String versionPath) {
		this.versionPath = versionPath;
	}

	public String getVersionFileName() {
		return versionFileName;
	}

	public void setVersionFileName(String versionFileName) {
		this.versionFileName = versionFileName;
	}

	public String getFileBase64() {
		return fileBase64;
	}

	public void setFileBase64(String fileBase64) {
		this.fileBase64 = fileBase64;
	}
}