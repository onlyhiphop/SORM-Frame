package com.sorm.core.queryImpl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sorm.bean.ColumnInfo;
import com.sorm.bean.TableInfo;
import com.sorm.core.CallBack;
import com.sorm.core.DBManager;
import com.sorm.core.Query;
import com.sorm.core.TableContext;
import com.sorm.utils.ReflectUtil;

/**
 * �������ݿ���ͬ�Ĳ���
 * 
* @author: liao
* @date: 2019��5��12�� ����7:33:56
 */
public abstract class BasicQuery implements Query{

	
	/**
	 * �����ӵĲ�ѯ����ͬ�Ĵ�����ȡ����
	 */
	public static Object executeQueryTemp(String sql,Class clazz,Object[] params, CallBack back){
		Connection con = DBManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			//���ô������������
			psSetParmas(ps, params);
			
			rs = ps.executeQuery();
			
			return back.doExecute(con, ps, rs);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally{
			DBManager.close(con, ps, rs);
		}
	}
	
	@Override
	public int executeDML(String sql, Object[] params) {
		Connection con = DBManager.getConnection();
		PreparedStatement ps = null;
		int count = 0;
		try {
			ps = con.prepareStatement(sql);
			
			psSetParmas(ps, params);
			
			count = ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBManager.close(con, ps);
		}
		return count;
	}

	@Override
	public void insert(Object obj) {
		Class c = obj.getClass();
		TableInfo tableInfo = TableContext.poClassTableMap.get(c);
		//  insert into t_user (name,..) values (?,?,?)
		StringBuilder sb = new StringBuilder("insert into " + tableInfo.getTname() + " (");
		StringBuilder sb2 = new StringBuilder("(");
		
		Field[] fields = c.getDeclaredFields();  
		//����Ҫ�����ֵ
		List<Object> fieldValues = new ArrayList<>();
		
		for(int i = 0; i < fields.length; i++){
			String fieldName = fields[i].getName();
			Object fieldValue = ReflectUtil.invokeGet(obj, fieldName);
			if (fieldValue != null) {
				sb.append(fieldName + ",");
				sb2.append("?" + ",");
				fieldValues.add(fieldValue);
			}
		}
		sb.setCharAt(sb.length() - 1, ')');
		sb.append(" values ");
		sb2.setCharAt(sb2.length() - 1, ')');
		String sql = sb.toString() + sb2.toString();
		
		executeDML(sql, fieldValues.toArray());
	}

	@Override
	public void delete(Class clazz, Object priKeyValue) {
		// delete from Emp where id = 2
		
		//ͨ��class������TableInfo
		TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
		//�������
		ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();
		
		String sql = "delete from " + tableInfo.getTname() + " where " + onlyPriKey.getName() + "=?";
		executeDML(sql, new Object[]{priKeyValue});
	}

	@Override
	public void delete(Object obj) {
		Class c = obj.getClass();
		TableInfo tableInfo = TableContext.poClassTableMap.get(c);
		ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();  //����
		
		//ͨ���������get������ȡ��������ֵ
		Object priKeyValue = ReflectUtil.invokeGet(obj, onlyPriKey.getName());
		
		delete(c, priKeyValue);
	}

	@Override
	public int update(Object obj, String[] fieldNames) {
		// update ���� set uname=?,pwd=? where id=?
		Class c = obj.getClass();
		TableInfo table = TableContext.poClassTableMap.get(c);
		String tableName = table.getTname();
		String priKey = table.getOnlyPriKey().getName();
		Object priKeyValue = ReflectUtil.invokeGet(obj, priKey);  
		
		StringBuilder sb = new StringBuilder("update " + tableName + " set ");
		StringBuilder sb2 = new StringBuilder();
		
		List<Object> fieldValues = new ArrayList<>();
		for(String f : fieldNames){
			sb.append(f + "=?,");
			fieldValues.add(ReflectUtil.invokeGet(obj, f));
		}
		sb.setCharAt(sb.length() - 1,' ');
		sb.append(" where " + priKey + "=" + priKeyValue);
		
		return executeDML(sb.toString(), fieldValues.toArray());
	}

	@Override
	public List queryRows(String sql, final Class clazz, Object[] params) {
		// select dname,address from dept where id>? and age<?
//		Connection con = DBManager.getConnection();
//		PreparedStatement ps = null;
//		ResultSet rs = null;
////		List<Object> lists = new ArrayList<>();   //�洢���
//		
//		try {
//			ps = con.prepareStatement(sql);
//			
//			//���ô������������
//			psSetParmas(ps, params);
//			
//			rs = ps.executeQuery();
			
//			//��ȡ�������Ԫ����
//			ResultSetMetaData metaData = rs.getMetaData();
//			
//			while(rs.next()){
//				Object object = clazz.newInstance();  //����ʵ�����󣬵����޲ι�����
//				for(int i=0; i < metaData.getColumnCount(); i++){
//					String columnName = metaData.getColumnLabel(i + 1);
//					Object columnValue = rs.getObject(columnName);
//					ReflectUtil.invokeSet(object, columnName, columnValue);
//				}
//				lists.add(object);
//			}	
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return null;
//			} finally{
//				DBManager.close(con, ps, rs);
//			}
		
			
		return (List) executeQueryTemp(sql, clazz, params, new CallBack() {
			
			@Override
			public Object doExecute(Connection con, PreparedStatement ps, ResultSet rs) {
				//�洢�����
				List<Object> lists = new ArrayList<>();
				try {
					ResultSetMetaData metaData = rs.getMetaData();
					while(rs.next()){
						Object object = clazz.newInstance();  //����ʵ�����󣬵����޲ι�����
						for(int i=0; i < metaData.getColumnCount(); i++){
							String columnName = metaData.getColumnLabel(i + 1);
							Object columnValue = rs.getObject(columnName);
							ReflectUtil.invokeSet(object, columnName, columnValue);
						}
						lists.add(object);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				return lists;
			}
		});
		
	}

	@Override
	public Object queryUniqueRow(String sql, Class clazz, Object[] params) {
		//����һ��
		List lists = queryRows(sql, clazz, params);
		return (lists == null && lists.size() < 1)?null:lists.get(0);
	}

	@Override
	public Object queryUniqueValue(String sql, Object[] params) {
		// ����һ��ֵ  select dname from dept where id=?
		Connection con = DBManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Object object = new Object();
		try {
			ps = con.prepareStatement(sql);
			
			psSetParmas(ps, params);
			
			rs = ps.executeQuery();
			while(rs.next()){
				object = rs.getObject(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	
	@Override
	public Number queryNumber(String sql, Object[] params) {
		return (Number) queryUniqueValue(sql, params);
	}

	public static void psSetParmas(PreparedStatement ps,Object[] params){
		if (params != null) {
			for(int i = 0;i<params.length; i++){
				try {
					ps.setObject(i + 1, params[i]);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
