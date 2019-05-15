package com.sorm.core;

import com.sorm.core.queryImpl.MySQLQuery;

/**
 * �������������Ϣ����query����
 * 
* @author: liao
* @date: 2019��5��9�� ����8:51:37
 */
public class QueryFactory {

	private static Query prototypeObj;  //ԭ�Ͷ���

	//����ģʽ������ֻ�ܱ�����һ��
	private QueryFactory(){}
	
	static{
		String usingDB = DBManager.getConf().getUsingDB();
		if ("mysql".equalsIgnoreCase(usingDB)) {
			prototypeObj = new MySQLQuery();
		}
	}
	
	public static Query createQuery(){
		return prototypeObj;
	}
	
}














