package ar.gfritz.com.org.sk.webui.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDBConnection {
	static MyDBConnection myDBConnection;
	public Connection con;
	private MyDBConnection() {
		if(myDBConnection == null) {
			
			
			try {

				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			}
			catch(ClassNotFoundException cnfex) {

				System.out.println("Problem in loading or registering MS Access JDBC driver");
				cnfex.printStackTrace();
			}
			
			String msAccessDBName = "C:/Users/user/Documents/SK_DB.accdb";
			String dbURL = "jdbc:ucanaccess://" + msAccessDBName; 

			// Step 2.A: Create and get connection using DriverManager class
			try {
				con = DriverManager.getConnection(dbURL);
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
	}
	
	public static synchronized MyDBConnection getConnection() {
		if(myDBConnection == null) {
			myDBConnection = new MyDBConnection();
		}
		return myDBConnection;
	}
	
	public static void closeConnectgion(Connection con, Statement statement) {
		try {
			if(statement != null) {
				statement.close();
			}
//			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
