package ar.gfritz.com.org.sk;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.googlecode.genericdao.search.hibernate.HibernateSearchProcessor;

@Configuration
public class HibernateSearchProcesserHelper {

	@Autowired
	SessionFactory sessionFactory;
	
	@Bean(name="hibernateSearchProcessor")
	public HibernateSearchProcessor getHibernateSearchProcessorObject(){
		return HibernateSearchProcessor.getInstanceForSessionFactory(sessionFactory);
	}
}
