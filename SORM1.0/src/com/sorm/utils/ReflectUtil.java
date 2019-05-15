package com.sorm.utils;

import java.lang.reflect.Method;

/**
 * ��װ���õķ������
* @author: liao
* @date: 2019��5��9�� ����8:54:50
 */
public class ReflectUtil {

	/**
	 * ͨ��������ö����Get����
	 * @param obj  ��Ҫ���õĶ���
	 * @param fieldName  get��������������
	 */
	public static Object invokeGet(Object obj, String fieldName){
		try {
			Class c = obj.getClass();
			//ͨ��������ƣ��������Զ�Ӧ��get������set����
			Method m = c.getDeclaredMethod("get" + StringUtil.firstCharToUpperCase(fieldName), null);
			Object object = m.invoke(obj, null);   //��obj�������������������null��ʾ�ķ���û�в���
			
			return object;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
	}
	
	/**
	 * ͨ��������ö����Set����
	 */
	public static void invokeSet(Object obj,String fieldName,Object value){
		Class c = obj.getClass();
		try {
			Method s = c.getDeclaredMethod("set" + StringUtil.firstCharToUpperCase(fieldName), value.getClass());
			s.invoke(obj, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}










