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
 * �����ȡ�������ݿ����б�ṹ����ṹ�Ĺ�ϵ�������Ը��ݱ�ṹ������ṹ��
 * 
* @author: liao
* @date: 2019��5��9�� ����8:50:48
 */
public class TableContext {
	
	/**
	 * ����Ϊkey������Ϣ����Ϊvalue
	 */
	public static Map<String, TableInfo> tables = new HashMap<String,TableInfo>();
	
	/**
	 * ��po��class����ͱ���Ϣ���������������������!
	 */
	public static Map<Class, TableInfo> poClassTableMap = new HashMap<Class,TableInfo>();

	/**
	 * ����ģʽ
	 */
	private TableContext(){}
	
	static {
		try {
			//��ʼ����ñ���Ϣ
			Connection con = DBManager.getConnection();
			DatabaseMetaData dbmd = con.getMetaData();
			
			ResultSet tableRet = dbmd.getTables(null, "%", "%", new String[]{"TABLE"});
			
			while(tableRet.next()){
				String tableName = (String)tableRet.getObject("TABLE_NAME");
				
				TableInfo ti = new TableInfo(tableName, new HashMap<String, ColumnInfo>(), new ArrayList<ColumnInfo>());
				tables.put(tableName, ti);
				
				ResultSet set = dbmd.getColumns(null, "%", tableName, "%");   //��ѯ���е������ֶ�
				
				while(set.next()){
					ColumnInfo ci = new ColumnInfo(set.getString("COLUMN_NAME"), set.getString("TYPE_NAME"), 0);
					ti.getColumns().put(set.getString("COLUMN_NAME"), ci);
				}
			
				ResultSet set2 = dbmd.getPrimaryKeys(null, "%", tableName);  //��ѯt_user���е�����
				while(set2.next()){
					ColumnInfo ci2 = ti.getColumns().get(set2.getObject("COLUMN_NAME"));
					ci2.setKeyType(1);   //����Ϊ��������
					ti.getPriKeys().add(ci2);
				}
				
				if (ti.getPriKeys().size() > 0) {   //ȡΨһ������������ʹ�ã������������������Ϊ�գ�
					ti.setOnlyPriKey(ti.getPriKeys().get(0));
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//������ṹ
		updateJavaPOFile();
		
		//����po���class����������ã�
		loadPOTables();
	}
	
	/**
	 * ����po��������࣬ʹClass����ͱ����Ӧ����
	 */
	private static void loadPOTables() {
		//�������б�
		for(TableInfo table : tables.values()){
			try {
				Class c = Class.forName(DBManager.getConf().getPoPackage() + "." +
						StringUtil.firstCharToUpperCase(table.getTname()));
				
				//��po��Class����ͱ�ӳ��
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

	//���ݱ�ṹ������ָ��po����PO��ṹ��
	public static void updateJavaPOFile(){
		Map<String, TableInfo> tables = TableContext.tables;
		for(TableInfo table : tables.values()){
			JavaFileUtil.createJavaPoFile(table, new MySqlTypeConvertor());
		}
	}
	
}

























