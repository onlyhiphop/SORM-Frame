package com.sorm.core;

/**
 * 负责java数据类型和数据库数据类型的互相转换
 * 
* @author: liao
* @date: 2019年5月9日 下午8:38:12
 */
public interface TypeConvertor {

	/**
	 * 将数据库类型转化成java的数据类型
	 * @param columnType  数据库字段的数据类型
	 * @return  java的数据类型
	 */
	public String dbTyepToJavaType(String columnType);
	
	
	/**
	 * 将java数据类型转化成数据库数据类型
	 * @param javaType  java数据类型
	 * @return 数据库类型
	 */
	public String javaTypeToDbType(String javaType);
}








