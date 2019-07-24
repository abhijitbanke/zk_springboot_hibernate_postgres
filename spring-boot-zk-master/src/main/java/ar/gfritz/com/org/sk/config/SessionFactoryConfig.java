package ar.gfritz.com.org.sk.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

@Configuration
public class SessionFactoryConfig {
//	@Value("${hibernate.dialect}")
	private String DIALECT;
//	@Autowired
	EntityManagerFactory entityManagerFactory;
	
//	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
	    if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
	        throw new NullPointerException("factory is not a hibernate factory");
	    }
	    LocalSessionFactoryBean localSessionFactoryBean = entityManagerFactory.unwrap(LocalSessionFactoryBean.class);
	    Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", DIALECT);
	    localSessionFactoryBean.setHibernateProperties(hibernateProperties);
	    
	    return localSessionFactoryBean;
	}
	
	
	@Bean(name="sessionFactory")
	public HibernateJpaSessionFactoryBean sessionFactory() {
	    return new HibernateJpaSessionFactoryBean();
	}
}
