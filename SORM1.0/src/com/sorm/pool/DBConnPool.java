package com.sorm.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sorm.core.DBManager;

/**
 * 连接池类：
 *  - 就是将Connection对象放入List中，反复重用！
	- 连接池的初始化： 事先放入多个连接对象。
	- 从连接池中取连接对象
		如果池中有可用连接，则将池中最后一个返回，同时，将该连接从池中remove，表示正在使用。
		如果池中无可用连接，则创建一个新的。
	-关闭连接：不是真正的关闭连接，而是将用完的连接放入池中。
 * 
* @author: liao
* @date: 2019年5月14日 下午3:03:53
 */
public class DBConnPool {

	/**
	 * 连接池对象
	 */
	private List<Connection> pool;
	
	/**
	 * 最大连接数
	 */
	private final int POOL_MAX_SIZE = DBManager.getConf().getPoolMaxSize();
	
	/**
	 * 最小连接数
	 */
	private final int POOL_MIN_SIZE = DBManager.getConf().getPoolMinSize();
	
	public DBConnPool() {
		init();
	}
	
	//初始化，放入连接
	public void init(){
		if (pool == null) {
			pool = new ArrayList<Connection>();
		}
		while(pool.size() < POOL_MIN_SIZE){
			pool.add(DBManager.createConnection());
			System.out.println("初始化成功，连接池中数为：" + pool.size());
		}
	}
	
	/**
	 * 取出连接 : 取出最后一个连接
	 */
	public synchronized Connection getConnetion(){
		Connection con = pool.get(pool.size() - 1);
		pool.remove(con);
		return con;
	}
	
	/**
	 * 关闭连接（不是真正的关闭，而是放回到连接池中）
	 */
	public synchronized void close(Connection con){
		if (pool.size() >= POOL_MAX_SIZE) {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			pool.add(con);
		}
		
	}
}












