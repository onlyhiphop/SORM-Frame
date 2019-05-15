package com.sorm.bean;

/**
 * ����������Ϣ����������ʹ�ò�ͬ�������ļ����ͣ�
* @author: liao
* @date: 2019��5��9�� ����8:59:48
 */
public class Configuration {

	/**
	 * ʹ�õ����ݿ�
	 */
	private String usingDB;  
	/**
	 * Դ��·��
	 */
	private String srcPath;  
	/**
	 * ɨ������java��İ���po����˼�ǣ�Persistence object �־û�����
	 */
	private String poPackage;   
	/**
	 * ʹ��jdbc������
	 */
	private String driver;   
	/**
	 * jdbc��url
	 */
	private String url;    
	/**
	 * �û���
	 */
	private String user;    
	/**
	 * ����
	 */
	private String pwd;    
	/**
	 * ���ӳ����������
	 */
	private Integer poolMaxSize;  
	/**
	 * ���ӳ���С������
	 */
	private Integer poolMinSize;  
	
	
	public Configuration() {
		// TODO Auto-generated constructor stub
	}


	public Integer getPoolMaxSize() {
		return poolMaxSize;
	}

	public void setPoolMaxSize(Integer poolMaxSize) {
		this.poolMaxSize = poolMaxSize;
	}


	public Integer getPoolMinSize() {
		return poolMinSize;
	}





	public void setPoolMinSize(Integer poolMinSize) {
		this.poolMinSize = poolMinSize;
	}





	public Configuration(String usingDB, String srcPath, String poPackage, String driver, String url, String user,
			String pwd, Integer poolMaxSize, Integer poolMinSize) {
		super();
		this.usingDB = usingDB;
		this.srcPath = srcPath;
		this.poPackage = poPackage;
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.pwd = pwd;
		this.poolMaxSize = poolMaxSize;
		this.poolMinSize = poolMinSize;
	}





	public String getUsingDB() {
		return usingDB;
	}

	public void setUsingDB(String usingDB) {
		this.usingDB = usingDB;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public String getPoPackage() {
		return poPackage;
	}

	public void setPoPackage(String poPackage) {
		this.poPackage = poPackage;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
}









