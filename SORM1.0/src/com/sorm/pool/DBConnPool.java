package com.sorm.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sorm.core.DBManager;

/**
 * ���ӳ��ࣺ
 *  - ���ǽ�Connection�������List�У��������ã�
	- ���ӳصĳ�ʼ���� ���ȷ��������Ӷ���
	- �����ӳ���ȡ���Ӷ���
		��������п������ӣ��򽫳������һ�����أ�ͬʱ���������Ӵӳ���remove����ʾ����ʹ�á�
		��������޿������ӣ��򴴽�һ���µġ�
	-�ر����ӣ����������Ĺر����ӣ����ǽ���������ӷ�����С�
 * 
* @author: liao
* @date: 2019��5��14�� ����3:03:53
 */
public class DBConnPool {

	/**
	 * ���ӳض���
	 */
	private List<Connection> pool;
	
	/**
	 * ���������
	 */
	private final int POOL_MAX_SIZE = DBManager.getConf().getPoolMaxSize();
	
	/**
	 * ��С������
	 */
	private final int POOL_MIN_SIZE = DBManager.getConf().getPoolMinSize();
	
	public DBConnPool() {
		init();
	}
	
	//��ʼ������������
	public void init(){
		if (pool == null) {
			pool = new ArrayList<Connection>();
		}
		while(pool.size() < POOL_MIN_SIZE){
			pool.add(DBManager.createConnection());
			System.out.println("��ʼ���ɹ������ӳ�����Ϊ��" + pool.size());
		}
	}
	
	/**
	 * ȡ������ : ȡ�����һ������
	 */
	public synchronized Connection getConnetion(){
		Connection con = pool.get(pool.size() - 1);
		pool.remove(con);
		return con;
	}
	
	/**
	 * �ر����ӣ����������Ĺرգ����ǷŻص����ӳ��У�
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












