package com.sorm.core;

/**
 * ����java�������ͺ����ݿ��������͵Ļ���ת��
 * 
* @author: liao
* @date: 2019��5��9�� ����8:38:12
 */
public interface TypeConvertor {

	/**
	 * �����ݿ�����ת����java����������
	 * @param columnType  ���ݿ��ֶε���������
	 * @return  java����������
	 */
	public String dbTyepToJavaType(String columnType);
	
	
	/**
	 * ��java��������ת�������ݿ���������
	 * @param javaType  java��������
	 * @return ���ݿ�����
	 */
	public String javaTypeToDbType(String javaType);
}








