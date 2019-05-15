package com.sorm.utils;

/**
 * 封装常用字符串操作
* @author: liao
* @date: 2019年5月9日 下午8:54:45
 */
public class StringUtil {

	/**
	 * 将字符串首字符大写
	 */
	public static String firstCharToUpperCase(String str){
		
		char first = str.toUpperCase().charAt(0);
		return first + str.substring(1);
	}
	
	/**
	 * 将包名 com.sorm.po 变成文件路径 com/sorm/po
	 */
	public static String packageToFilePath(String str){
		return str.replaceAll("\\.", "/");   //第一个参数  正则表达式
	}
	
	public static void main(String[] args) {
		System.out.println(packageToFilePath("com.sorm.po"));
	}
}
