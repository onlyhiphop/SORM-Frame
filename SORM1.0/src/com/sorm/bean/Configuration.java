package com.sorm.bean;

/**
 * 管理配置信息（这样可以使用不同的配置文件类型）
* @author: liao
* @date: 2019年5月9日 下午8:59:48
 */
public class Configuration {

	/**
	 * 使用的数据库
	 */
	private String usingDB;  
	/**
	 * 源码路径
	 */
	private String srcPath;  
	/**
	 * 扫描生成java类的包（po的意思是：Persistence object 持久化对象）
	 */
	private String poPackage;   
	/**
	 * 使用jdbc的驱动
	 */
	private String driver;   
	/**
	 * jdbc的url
	 */
	private String url;    
	/**
	 * 用户名
	 */
	private String user;    
	/**
	 * 密码
	 */
	private String pwd;    
	/**
	 * 连接池最大连接数
	 */
	private Integer poolMaxSize;  
	/**
	 * 连接池最小连接数
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









