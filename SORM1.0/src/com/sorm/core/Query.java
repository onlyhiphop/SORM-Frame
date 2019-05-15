package com.sorm.core;

import java.util.List;

/**
 * 负责查询（对外提供服务的核心类）
 * 
* @author: liao
* @date: 2019年5月9日 下午8:06:11
 */
public interface Query{

	/**
	 * 直接执行一个DML语句
	 * @param sql  sql语句
	 * @param params sql语句的?参数
	 * @return 执行sql语句后影响记录的行数
	 */
	public int executeDML(String sql, Object[] params);
	
	/**
	 * 将一个对象存储到数据库中
	 * @param obj  要存储的对象
	 */
	public void insert(Object obj);
	
	/**
	 * 删除clazz表示的类对应的表中的记录（根据主键值）
	 * （因为主键值是唯一的，所以只需要主键匹配成功，对象就对应该记录，由此来知道删除哪条记录）
	 * @param clazz 跟表对应的类的Class对象
	 * @param priKey  主键的值
	 */
	public void delete(Class clazz, Object priKeyValue);
	
	/**
	 * 删除对象在数据库中对应的记录（对象所在的类对应到表，对象的主键对应到记录）
	 * @param obj 
	 */
	public void delete(Object obj);
	
	/**
	 * 更新对象对应的记录，并且只能更新指定的主键所对应的记录的值
	 * @param obj  更新的对象
	 * @param fieldNames  所需要更新的属性
	 * @return 执行sql语句影响的行数
	 */
	public int update(Object obj, String[] fieldNames);
	
	/**
	 * 查询返回多行记录，并将每行记录封装到clazz指定的类的对象中
	 * @param sql  查询语句
	 * @param clazz  封装数据的Javabean类的Class对象
	 * @param params  sql条件参数
	 * @return  返回查询到的所有结果
	 */
	public List queryRows(String sql, Class clazz, Object[] params);
	
	/**
	 * 查询返回一行记录，并将每列封装到clazz指定的类的对象中
	 * @param sql  查询语句
	 * @param clazz  封装数据的Javabean类的Class对象
	 * @param params  sql条件参数
	 * @return  返回查询到的所有结果
	 */
	public Object queryUniqueRow(String sql, Class clazz, Object[] params);
	
	
	/**
	 * 查询返回一个值，并封装到clazz指定的类的对象中
	 * @param sql  查询语句
	 * @param params  sql条件参数
	 * @return  返回查询到的结果
	 */
	public Object queryUniqueValue(String sql,Object[] params);
	
	/**
	 * 查询数字
	 */
	public Number queryNumber(String sql, Object[] params);
}











