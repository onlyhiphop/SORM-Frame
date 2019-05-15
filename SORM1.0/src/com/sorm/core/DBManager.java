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
 * ����������Ϣ��ά�����Ӷ���Ĺ����������ӳع��ܣ�
 * 
* @author: liao
* @date: 2019��5��9�� ����8:51:20
 */
public class DBManager {

	/**
	 * ������Ϣ����
	 */
	private static Configuration conf = new Configuration();
	
	/**
	 * ���ӳض���
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
	 * �õ����ӣ������ӳ���ȡ
	 */
	public static Connection getConnection(){
		if (pool == null) {
			pool = new DBConnPool();
		}
		return pool.getConnetion();
	}
	
	/**
	 * �����µ�����
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
	 * �õ�configuration����
	 */
	public static Configuration getConf(){
		return conf;
	}
	
	
	/**
	 * �ر���Դ
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
	 * �ر���Դ
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














