package com.sorm.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 回调
 * 
* @author: liao
* @date: 2019年5月14日 上午8:13:53
 */
public interface CallBack {

	public Object doExecute(Connection con, PreparedStatement ps, ResultSet rs);
	
}

















