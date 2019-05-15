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
 * 封装了java文件操作常用操作
* @author: liao
* @date: 2019年5月9日 下午8:55:41
 */
public class JavaFileUtil {

	private static final String BLANK = " "; 
	
	/**
	 * 根据字段信息生成java属性信息。如 username varchar --> private String username;以及相应的set和get方法源码
	 * @param column 字段信息
	 * @param convertor  类型装换器
	 * @return  java属性和set/get方法源码
	 */
	public static JavaFieldGetSet createFieldGetSetSRC(ColumnInfo column, TypeConvertor convertor){
		
		JavaFieldGetSet jfgs = new JavaFieldGetSet();
		
		String javaFieldType = convertor.dbTyepToJavaType(column.getDataType());
		String fieldName = column.getName();
		
		jfgs.setFieldInfo("\tprivate " + javaFieldType + BLANK + fieldName + ";\n");
		
		//构造set方法  public int setId(int a){this.a=a}
		StringBuilder setStr = new StringBuilder();
		setStr.append("\tpublic" + BLANK).append("void" + BLANK).append("set" + StringUtil.firstCharToUpperCase(fieldName));
		setStr.append("(" + javaFieldType + BLANK + fieldName + ")").append("{this.").append(fieldName).append("=").append(fieldName + ";}");
		jfgs.setSetInfo(setStr.toString());
		
		//构造get方法  public void getId(){return a}
		StringBuilder getStr = new StringBuilder();
		getStr.append("\tpublic" + BLANK + javaFieldType + BLANK).append("get" + StringUtil.firstCharToUpperCase(fieldName));
		getStr.append("()").append("{").append("return" + BLANK + fieldName).append(";}");
		jfgs.setGetInfo(getStr.toString());
		
		return jfgs;
	}
	
	/**
	 * 根据表信息生成 java类的源码
	 */
	public static String createJavaSrc(TableInfo tableInfo, TypeConvertor convertor){
		String tableName = tableInfo.getTname();
		String ClassName = StringUtil.firstCharToUpperCase(tableName);
		Map<String, ColumnInfo> columnMaps = tableInfo.getColumns();
		
		//得到属性和get set方法
		List<JavaFieldGetSet> lists = new ArrayList<JavaFieldGetSet>();
		for(ColumnInfo co : columnMaps.values()){
			lists.add(createFieldGetSetSRC(co, convertor));
		}
		StringBuilder fieldSetGetStr = new StringBuilder();
		for(JavaFieldGetSet s : lists){
			fieldSetGetStr.append(s.toString() + "\n");
		}
		
		//得到类体
		StringBuilder classStr = new StringBuilder();
		//生成package语句
		String poPackage = DBManager.getConf().getPoPackage();
		classStr.append("package" + BLANK +poPackage + ";\n\n");
		//生成import语句
		classStr.append("import java.sql.*;\n");
		classStr.append("import java.util.*;\n\n");
		classStr.append("public class "+ ClassName + "{" + "\n");
		
		//合并
		classStr.append(fieldSetGetStr.toString()).append("}");
		return classStr.toString();
	}
	
	/**
	 * 生成java文件
	 */
	public static void createJavaPoFile(TableInfo tableInfo, TypeConvertor convertor){
		String javaStr = createJavaSrc(tableInfo, convertor);
		
		//需要生成的源路径
		String srcPath = DBManager.getConf().getSrcPath() + "/";
		//包路径
		String packagePath = StringUtil.packageToFilePath(DBManager.getConf().getPoPackage());
		//类名
		String className = StringUtil.firstCharToUpperCase(tableInfo.getTname());
		//文件总名
		String filePath = srcPath + packagePath;
		String fileName = className + ".java";
		
		//创建文件
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









