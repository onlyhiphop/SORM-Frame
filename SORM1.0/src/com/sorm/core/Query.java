package com.sorm.core;

import java.util.List;

/**
 * �����ѯ�������ṩ����ĺ����ࣩ
 * 
* @author: liao
* @date: 2019��5��9�� ����8:06:11
 */
public interface Query{

	/**
	 * ֱ��ִ��һ��DML���
	 * @param sql  sql���
	 * @param params sql����?����
	 * @return ִ��sql����Ӱ���¼������
	 */
	public int executeDML(String sql, Object[] params);
	
	/**
	 * ��һ������洢�����ݿ���
	 * @param obj  Ҫ�洢�Ķ���
	 */
	public void insert(Object obj);
	
	/**
	 * ɾ��clazz��ʾ�����Ӧ�ı��еļ�¼����������ֵ��
	 * ����Ϊ����ֵ��Ψһ�ģ�����ֻ��Ҫ����ƥ��ɹ�������Ͷ�Ӧ�ü�¼���ɴ���֪��ɾ��������¼��
	 * @param clazz �����Ӧ�����Class����
	 * @param priKey  ������ֵ
	 */
	public void delete(Class clazz, Object priKeyValue);
	
	/**
	 * ɾ�����������ݿ��ж�Ӧ�ļ�¼���������ڵ����Ӧ���������������Ӧ����¼��
	 * @param obj 
	 */
	public void delete(Object obj);
	
	/**
	 * ���¶����Ӧ�ļ�¼������ֻ�ܸ���ָ������������Ӧ�ļ�¼��ֵ
	 * @param obj  ���µĶ���
	 * @param fieldNames  ����Ҫ���µ�����
	 * @return ִ��sql���Ӱ�������
	 */
	public int update(Object obj, String[] fieldNames);
	
	/**
	 * ��ѯ���ض��м�¼������ÿ�м�¼��װ��clazzָ������Ķ�����
	 * @param sql  ��ѯ���
	 * @param clazz  ��װ���ݵ�Javabean���Class����
	 * @param params  sql��������
	 * @return  ���ز�ѯ�������н��
	 */
	public List queryRows(String sql, Class clazz, Object[] params);
	
	/**
	 * ��ѯ����һ�м�¼������ÿ�з�װ��clazzָ������Ķ�����
	 * @param sql  ��ѯ���
	 * @param clazz  ��װ���ݵ�Javabean���Class����
	 * @param params  sql��������
	 * @return  ���ز�ѯ�������н��
	 */
	public Object queryUniqueRow(String sql, Class clazz, Object[] params);
	
	
	/**
	 * ��ѯ����һ��ֵ������װ��clazzָ������Ķ�����
	 * @param sql  ��ѯ���
	 * @param params  sql��������
	 * @return  ���ز�ѯ���Ľ��
	 */
	public Object queryUniqueValue(String sql,Object[] params);
	
	/**
	 * ��ѯ����
	 */
	public Number queryNumber(String sql, Object[] params);
}











