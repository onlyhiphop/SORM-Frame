package com.sorm.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * �ص�
 * 
* @author: liao
* @date: 2019��5��14�� ����8:13:53
 */
public interface CallBack {

	public Object doExecute(Connection con, PreparedStatement ps, ResultSet rs);
	
}

















