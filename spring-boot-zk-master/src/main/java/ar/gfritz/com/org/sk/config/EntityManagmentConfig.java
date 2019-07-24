package ar.gfritz.com.org.sk.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

//@Configuration
public class EntityManagmentConfig {
//	@Autowired
	DataSource dataSource;
	
//	@Autowired
	JpaVendorAdapter jpaVendorAdapter;
	
//	@Bean(name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {
    	LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    	emf.setDataSource(dataSource);
    	emf.setJpaVendorAdapter(jpaVendorAdapter);
    	emf.setPackagesToScan("ar.gfritz.com.org.sk.bean");
    	emf.setPersistenceUnitName("default");
//    	emf.setJpaDialect(new HibernateJpaDialect());
    	emf.afterPropertiesSet();
    	return emf.getObject();
    }
}
