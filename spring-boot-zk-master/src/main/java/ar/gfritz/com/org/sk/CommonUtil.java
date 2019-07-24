package ar.gfritz.com.org.sk;

public class CommonUtil {
	public static java.sql.Date convertUtilToSql(java.util.Date uDate){
		return new java.sql.Date(uDate.getTime());
	}
	
	public static java.util.Date convertSQLToUTIL(java.sql.Date sDate){
		return new java.util.Date(sDate.getTime());
	}
}
