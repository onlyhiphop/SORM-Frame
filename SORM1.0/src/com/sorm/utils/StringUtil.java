package com.sorm.utils;

/**
 * ��װ�����ַ�������
* @author: liao
* @date: 2019��5��9�� ����8:54:45
 */
public class StringUtil {

	/**
	 * ���ַ������ַ���д
	 */
	public static String firstCharToUpperCase(String str){
		
		char first = str.toUpperCase().charAt(0);
		return first + str.substring(1);
	}
	
	/**
	 * ������ com.sorm.po ����ļ�·�� com/sorm/po
	 */
	public static String packageToFilePath(String str){
		return str.replaceAll("\\.", "/");   //��һ������  ������ʽ
	}
	
	public static void main(String[] args) {
		System.out.println(packageToFilePath("com.sorm.po"));
	}
}
