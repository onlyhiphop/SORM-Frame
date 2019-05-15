package com.sorm.utils;

import java.lang.reflect.Method;

/**
 * 封装常用的反射操作
* @author: liao
* @date: 2019年5月9日 下午8:54:50
 */
public class ReflectUtil {

	/**
	 * 通过反射调用对象的Get方法
	 * @param obj  需要调用的对象
	 * @param fieldName  get方法的属性名称
	 */
	public static Object invokeGet(Object obj, String fieldName){
		try {
			Class c = obj.getClass();
			//通过反射机制，调用属性对应的get方法或set方法
			Method m = c.getDeclaredMethod("get" + StringUtil.firstCharToUpperCase(fieldName), null);
			Object object = m.invoke(obj, null);   //在obj对象上运行这个方法，null表示改方法没有参数
			
			return object;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
	}
	
	/**
	 * 通过反射调用对象的Set方法
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










