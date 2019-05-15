package com.sorm.bean;

/**
 * ��װ��java���Ժ�get��set������Դ����
* @author: liao
* @date: 2019��5��11�� ����10:13:22
 */
public class JavaFieldGetSet {

	/**
	 * ���Ե�Դ�롣�磺private int userId;
	 */
	private String fieldInfo;
	
	/**
	 * get������Դ����Ϣ���磺public int getUserId(){}
	 */
	private String getInfo;
	/**
	 * set������Դ����Ϣ���磺public void setUserId(int userid){}
	 */
	private String setInfo;
	
	public JavaFieldGetSet() {
		// TODO Auto-generated constructor stub
	}

	public JavaFieldGetSet(String fieldInfo, String getInfo, String setInfo) {
		super();
		this.fieldInfo = fieldInfo;
		this.getInfo = getInfo;
		this.setInfo = setInfo;
	}

	public String getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(String fieldInfo) {
		this.fieldInfo = fieldInfo;
	}

	public String getGetInfo() {
		return getInfo;
	}

	public void setGetInfo(String getInfo) {
		this.getInfo = getInfo;
	}

	public String getSetInfo() {
		return setInfo;
	}

	public void setSetInfo(String setInfo) {
		this.setInfo = setInfo;
	}

	@Override
	public String toString() {
		return fieldInfo + "\n" + setInfo + "\n" + getInfo + "\n";
	}
	
	
}





 





