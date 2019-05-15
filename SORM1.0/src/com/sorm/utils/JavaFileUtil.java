package com.sorm.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sorm.bean.ColumnInfo;
import com.sorm.bean.JavaFieldGetSet;
import com.sorm.bean.TableInfo;
import com.sorm.core.DBManager;
import com.sorm.core.TableContext;
import com.sorm.core.TypeConvertor;
import com.sorm.core.convertImpl.MySqlTypeConvertor;

/**
 * ��װ��java�ļ��������ò���
* @author: liao
* @date: 2019��5��9�� ����8:55:41
 */
public class JavaFileUtil {

	private static final String BLANK = " "; 
	
	/**
	 * �����ֶ���Ϣ����java������Ϣ���� username varchar --> private String username;�Լ���Ӧ��set��get����Դ��
	 * @param column �ֶ���Ϣ
	 * @param convertor  ����װ����
	 * @return  java���Ժ�set/get����Դ��
	 */
	public static JavaFieldGetSet createFieldGetSetSRC(ColumnInfo column, TypeConvertor convertor){
		
		JavaFieldGetSet jfgs = new JavaFieldGetSet();
		
		String javaFieldType = convertor.dbTyepToJavaType(column.getDataType());
		String fieldName = column.getName();
		
		jfgs.setFieldInfo("\tprivate " + javaFieldType + BLANK + fieldName + ";\n");
		
		//����set����  public int setId(int a){this.a=a}
		StringBuilder setStr = new StringBuilder();
		setStr.append("\tpublic" + BLANK).append("void" + BLANK).append("set" + StringUtil.firstCharToUpperCase(fieldName));
		setStr.append("(" + javaFieldType + BLANK + fieldName + ")").append("{this.").append(fieldName).append("=").append(fieldName + ";}");
		jfgs.setSetInfo(setStr.toString());
		
		//����get����  public void getId(){return a}
		StringBuilder getStr = new StringBuilder();
		getStr.append("\tpublic" + BLANK + javaFieldType + BLANK).append("get" + StringUtil.firstCharToUpperCase(fieldName));
		getStr.append("()").append("{").append("return" + BLANK + fieldName).append(";}");
		jfgs.setGetInfo(getStr.toString());
		
		return jfgs;
	}
	
	/**
	 * ���ݱ���Ϣ���� java���Դ��
	 */
	public static String createJavaSrc(TableInfo tableInfo, TypeConvertor convertor){
		String tableName = tableInfo.getTname();
		String ClassName = StringUtil.firstCharToUpperCase(tableName);
		Map<String, ColumnInfo> columnMaps = tableInfo.getColumns();
		
		//�õ����Ժ�get set����
		List<JavaFieldGetSet> lists = new ArrayList<JavaFieldGetSet>();
		for(ColumnInfo co : columnMaps.values()){
			lists.add(createFieldGetSetSRC(co, convertor));
		}
		StringBuilder fieldSetGetStr = new StringBuilder();
		for(JavaFieldGetSet s : lists){
			fieldSetGetStr.append(s.toString() + "\n");
		}
		
		//�õ�����
		StringBuilder classStr = new StringBuilder();
		//����package���
		String poPackage = DBManager.getConf().getPoPackage();
		classStr.append("package" + BLANK +poPackage + ";\n\n");
		//����import���
		classStr.append("import java.sql.*;\n");
		classStr.append("import java.util.*;\n\n");
		classStr.append("public class "+ ClassName + "{" + "\n");
		
		//�ϲ�
		classStr.append(fieldSetGetStr.toString()).append("}");
		return classStr.toString();
	}
	
	/**
	 * ����java�ļ�
	 */
	public static void createJavaPoFile(TableInfo tableInfo, TypeConvertor convertor){
		String javaStr = createJavaSrc(tableInfo, convertor);
		
		//��Ҫ���ɵ�Դ·��
		String srcPath = DBManager.getConf().getSrcPath() + "/";
		//��·��
		String packagePath = StringUtil.packageToFilePath(DBManager.getConf().getPoPackage());
		//����
		String className = StringUtil.firstCharToUpperCase(tableInfo.getTname());
		//�ļ�����
		String filePath = srcPath + packagePath;
		String fileName = className + ".java";
		
		//�����ļ�
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(file, fileName);
		
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(javaStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}









