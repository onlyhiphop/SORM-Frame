package com.sorm.core;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.sorm.bean.Configuration;
import com.sorm.pool.DBConnPool;

/**
 * 根据配置信息，维持连接对象的管理（增加连接池功能）
 * 
* @author: liao
* @date: 2019年5月9日 下午8:51:20
 */
public class DBManager {

	/**
	 * 配置信息对象
	 */
	private static Configuration conf = new Configuration();
	
	/**
	 * 连接池对象
	 */
	private static DBConnPool pool;
	
	static {
		Properties pp = new Properties();
		try {
			pp.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
			conf.setUsingDB(pp.getProperty("usingDB"));
			conf.setDriver(pp.getProperty("driver"));
			conf.setPoPackage(pp.getProperty("poPackage"));
			conf.setSrcPath(pp.getProperty("srcPath"));
			conf.setUrl(pp.getProperty("url"));
			conf.setUser(pp.getProperty("user"));
			conf.setPwd(pp.getProperty("pwd"));
			conf.setPoolMaxSize(Integer.parseInt(pp.getProperty("poolMaxSize")));
			conf.setPoolMinSize(Integer.parseInt(pp.getProperty("poolMinSize")));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到连接，从连接池中取
	 */
	public static Connection getConnection(){
		if (pool == null) {
			pool = new DBConnPool();
		}
		return pool.getConnetion();
	}
	
	/**
	 * 创建新的连接
	 */
	public static Connection createConnection(){
		Connection connection = null;
		try {
			Class.forName(conf.getDriver());
			connection= DriverManager.getConnection(conf.getUrl(), conf.getUser(), conf.getPwd());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	/**
	 * 得到configuration对象
	 */
	public static Configuration getConf(){
		return conf;
	}
	
	
	/**
	 * 关闭资源
	 */
	public static void close(AutoCloseable ...c){
		for (AutoCloseable c1 : c) {
			if (c1.getClass().getName().equals("java.sql.Connection")) {
				pool.close((Connection) c1);
			}else{
				try {
					c1.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 关闭资源
	 */
	public static void close(Connection con, Statement smt, ResultSet rs){
		pool.close(con);
		if (smt != null) {
			try {
				smt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}














