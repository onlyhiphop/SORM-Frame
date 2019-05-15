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
 * 所有数据库相同的操作
 * 
* @author: liao
* @date: 2019年5月12日 上午7:33:56
 */
public abstract class BasicQuery implements Query{

	
	/**
	 * 将复杂的查询的相同的代码提取出来
	 */
	public static Object executeQueryTemp(String sql,Class clazz,Object[] params, CallBack back){
		Connection con = DBManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			//设置传入的条件参数
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
		//放需要传入的值
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
		
		//通过class对象找TableInfo
		TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
		//获得主键
		ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();
		
		String sql = "delete from " + tableInfo.getTname() + " where " + onlyPriKey.getName() + "=?";
		executeDML(sql, new Object[]{priKeyValue});
	}

	@Override
	public void delete(Object obj) {
		Class c = obj.getClass();
		TableInfo tableInfo = TableContext.poClassTableMap.get(c);
		ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();  //主键
		
		//通过反射调用get方法获取到主键的值
		Object priKeyValue = ReflectUtil.invokeGet(obj, onlyPriKey.getName());
		
		delete(c, priKeyValue);
	}

	@Override
	public int update(Object obj, String[] fieldNames) {
		// update 表名 set uname=?,pwd=? where id=?
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
////		List<Object> lists = new ArrayList<>();   //存储结果
//		
//		try {
//			ps = con.prepareStatement(sql);
//			
//			//设置传入的条件参数
//			psSetParmas(ps, params);
//			
//			rs = ps.executeQuery();
			
//			//获取结果集的元数据
//			ResultSetMetaData metaData = rs.getMetaData();
//			
//			while(rs.next()){
//				Object object = clazz.newInstance();  //创建实例对象，调用无参构造器
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
				//存储结果集
				List<Object> lists = new ArrayList<>();
				try {
					ResultSetMetaData metaData = rs.getMetaData();
					while(rs.next()){
						Object object = clazz.newInstance();  //创建实例对象，调用无参构造器
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
		//返回一行
		List lists = queryRows(sql, clazz, params);
		return (lists == null && lists.size() < 1)?null:lists.get(0);
	}

	@Override
	public Object queryUniqueValue(String sql, Object[] params) {
		// 返回一个值  select dname from dept where id=?
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
