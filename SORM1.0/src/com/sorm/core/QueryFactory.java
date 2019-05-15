package com.sorm.core;

import com.sorm.core.queryImpl.MySQLQuery;

/**
 * 负责根据配置信息创建query对象
 * 
* @author: liao
* @date: 2019年5月9日 下午8:51:37
 */
public class QueryFactory {

	private static Query prototypeObj;  //原型对象

	//单例模式，工厂只能被创建一个
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














