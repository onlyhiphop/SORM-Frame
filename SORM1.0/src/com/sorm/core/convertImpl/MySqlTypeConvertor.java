package com.sorm.core.convertImpl;

import com.sorm.core.TypeConvertor;

public class MySqlTypeConvertor implements TypeConvertor{

	@Override
	public String dbTyepToJavaType(String columnType) {
		
		if ("varchar".equalsIgnoreCase(columnType) || "char".equalsIgnoreCase(columnType)) {
			return "String";
		}else if("int".equalsIgnoreCase(columnType) 
				|| "tinyint".equalsIgnoreCase(columnType)
				|| "smallint".equalsIgnoreCase(columnType)
				|| "integer".equalsIgnoreCase(columnType)
				){
			return "Integer";
		}else if("bigint".equalsIgnoreCase(columnType)){
			return "Long";
		}else if("double".equalsIgnoreCase(columnType)){
			return "Double";
		}else if("float".equalsIgnoreCase(columnType)){
			return "Float";
		}else if("text".equalsIgnoreCase(columnType)){
			return "java.sql.Clob";
		}else if("mediumblob".equalsIgnoreCase(columnType)){
			return "java.sql.Blob";
		}else if("date".equalsIgnoreCase(columnType)){
			return "java.sql.Date";
		}else if("time".equalsIgnoreCase(columnType)){
			return "java.sql.Time";
		}else if("timestamp".equalsIgnoreCase(columnType)){
			return "java.sql.Timestamp";
		}
		
		return null;
	}

	@Override
	public String javaTypeToDbType(String javaType) {
		// TODO Auto-generated method stub
		return null;
	}

}
