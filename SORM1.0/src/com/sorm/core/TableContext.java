package com.sorm.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sorm.bean.ColumnInfo;
import com.sorm.bean.TableInfo;
import com.sorm.core.convertImpl.MySqlTypeConvertor;
import com.sorm.utils.JavaFileUtil;
import com.sorm.utils.StringUtil;

/**
 * 负责获取管理数据库所有表结构和类结构的关系，并可以根据表结构生成类结构。
 * 
* @author: liao
* @date: 2019年5月9日 下午8:50:48
 */
public class TableContext {
	
	/**
	 * 表名为key，表信息对象为value
	 */
	public static Map<String, TableInfo> tables = new HashMap<String,TableInfo>();
	
	/**
	 * 将po的class对象和表信息对象关联起来，便于重用!
	 */
	public static Map<Class, TableInfo> poClassTableMap = new HashMap<Class,TableInfo>();

	/**
	 * 单例模式
	 */
	private TableContext(){}
	
	static {
		try {
			//初始化获得表信息
			Connection con = DBManager.getConnection();
			DatabaseMetaData dbmd = con.getMetaData();
			
			ResultSet tableRet = dbmd.getTables(null, "%", "%", new String[]{"TABLE"});
			
			while(tableRet.next()){
				String tableName = (String)tableRet.getObject("TABLE_NAME");
				
				TableInfo ti = new TableInfo(tableName, new HashMap<String, ColumnInfo>(), new ArrayList<ColumnInfo>());
				tables.put(tableName, ti);
				
				ResultSet set = dbmd.getColumns(null, "%", tableName, "%");   //查询表中的所有字段
				
				while(set.next()){
					ColumnInfo ci = new ColumnInfo(set.getString("COLUMN_NAME"), set.getString("TYPE_NAME"), 0);
					ti.getColumns().put(set.getString("COLUMN_NAME"), ci);
				}
			
				ResultSet set2 = dbmd.getPrimaryKeys(null, "%", tableName);  //查询t_user表中的主键
				while(set2.next()){
					ColumnInfo ci2 = ti.getColumns().get(set2.getObject("COLUMN_NAME"));
					ci2.setKeyType(1);   //设置为主键类型
					ti.getPriKeys().add(ci2);
				}
				
				if (ti.getPriKeys().size() > 0) {   //取唯一主键。。方便使用，如果是联合主键。则为空！
					ti.setOnlyPriKey(ti.getPriKeys().get(0));
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//更新类结构
		updateJavaPOFile();
		
		//加载po类的class对象便于重用！
		loadPOTables();
	}
	
	/**
	 * 加载po包下面的类，使Class对象和表相对应起来
	 */
	private static void loadPOTables() {
		//遍历所有表
		for(TableInfo table : tables.values()){
			try {
				Class c = Class.forName(DBManager.getConf().getPoPackage() + "." +
						StringUtil.firstCharToUpperCase(table.getTname()));
				
				//将po的Class对象和表映射
				poClassTableMap.put(c, table);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static Map<String, TableInfo> getTableInfos(){
		return tables;
	}

	//根据表结构，更新指定po包的PO类结构。
	public static void updateJavaPOFile(){
		Map<String, TableInfo> tables = TableContext.tables;
		for(TableInfo table : tables.values()){
			JavaFileUtil.createJavaPoFile(table, new MySqlTypeConvertor());
		}
	}
	
}

























