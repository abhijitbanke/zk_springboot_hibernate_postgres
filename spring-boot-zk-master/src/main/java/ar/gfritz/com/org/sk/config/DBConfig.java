package ar.gfritz.com.org.sk.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class DBConfig {
//    @Bean(name="dataSource")
	public DataSource dataSource() {
	        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
	        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
	        dataSourceBuilder.url("jdbc:sqlite:/home/abhijit/MyWork/sqlite_DB_repo/sk_pms.db");
	        return dataSourceBuilder.build();   
	}
    
}