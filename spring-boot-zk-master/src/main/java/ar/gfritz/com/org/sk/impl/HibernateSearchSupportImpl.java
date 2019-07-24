package ar.gfritz.com.org.sk.impl;

import java.util.List;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.gfritz.com.org.sk.HibernateSearchSupport;

import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.hibernate.HibernateSearchProcessor;



/**
 * @author bbruhns
 * 
 */
@Repository("hibernateSearchSupport")
@Transactional
public class HibernateSearchSupportImpl implements HibernateSearchSupport {
	@Autowired
	private HibernateSearchProcessor hibernateSearchProcessor;
	@Autowired
	private SessionFactory sessionFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.forsthaus.backend.dao.impl.HibernateSearchSupport#count(java.lang.
	 * Class, com.googlecode.genericdao.search.ISearch)
	 */
	public int count(Class<?> searchClass, ISearch search) {
		return hibernateSearchProcessor.count(getCurrentSession(), searchClass, search);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.forsthaus.backend.dao.impl.HibernateSearchSupport#count(com.googlecode.genericdao.search
	 * .ISearch)
	 */
	public int count(ISearch search) {
		return hibernateSearchProcessor.count(getCurrentSession(), search);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.forsthaus.backend.dao.impl.HibernateSearchSupport#generateQL(java.
	 * lang.Class, com.googlecode.genericdao.search.ISearch, java.util.List)
	 */
	public String generateQL(Class<?> entityClass, ISearch search, List<Object> paramList) {
		return hibernateSearchProcessor.generateQL(entityClass, search, paramList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.forsthaus.backend.dao.impl.HibernateSearchSupport#generateRowCountQL
	 * (java.lang.Class, com.googlecode.genericdao.search.ISearch, java.util.List)
	 */
	public String generateRowCountQL(Class<?> entityClass, ISearch search, List<Object> paramList) {
		return hibernateSearchProcessor.generateRowCountQL(entityClass, search, paramList);
	}

	private Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.forsthaus.backend.dao.impl.HibernateSearchSupport#getFilterFromExample
	 * (java.lang.Object)
	 */
	public Filter getFilterFromExample(Object example) {
		return hibernateSearchProcessor.getFilterFromExample(example);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.forsthaus.backend.dao.impl.HibernateSearchSupport#getFilterFromExample
	 * (java.lang.Object, com.googlecode.genericdao.search.ExampleOptions)
	 */
	public Filter getFilterFromExample(Object example, ExampleOptions options) {
		return hibernateSearchProcessor.getFilterFromExample(example, options);
	}

	private SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.forsthaus.backend.dao.impl.HibernateSearchSupport#search(java.lang
	 * .Class, com.googlecode.genericdao.search.ISearch)
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> search(Class<T> searchClass, ISearch search) {
		return hibernateSearchProcessor.search(getCurrentSession(), searchClass, search);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.forsthaus.backend.dao.impl.HibernateSearchSupport#search(com.googlecode.genericdao.search
	 * .ISearch)
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> search(ISearch search) {
		return hibernateSearchProcessor.search(getCurrentSession(), search);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.forsthaus.backend.dao.impl.HibernateSearchSupport#searchAndCount(java
	 * .lang.Class, com.googlecode.genericdao.search.ISearch)
	 */
	@SuppressWarnings("unchecked")
	public <T> SearchResult<T> searchAndCount(Class<T> searchClass, ISearch search) {
		return hibernateSearchProcessor.searchAndCount(getCurrentSession(), searchClass, search);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.forsthaus.backend.dao.impl.HibernateSearchSupport#searchAndCount(com
	 * .trg.search.ISearch)
	 */
	@SuppressWarnings("unchecked")
	public <T> SearchResult<T> searchAndCount(ISearch search) {
		return hibernateSearchProcessor.searchAndCount(getCurrentSession(), search);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.forsthaus.backend.dao.impl.HibernateSearchSupport#searchUnique(java
	 * .lang.Class, com.googlecode.genericdao.search.ISearch)
	 */
	@SuppressWarnings("unchecked")
	public <T> T searchUnique(Class<T> entityClass, ISearch search) throws NonUniqueResultException {
		return (T) hibernateSearchProcessor.searchUnique(getCurrentSession(), entityClass, search);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.forsthaus.backend.dao.impl.HibernateSearchSupport#searchUnique(com
	 * .trg.search.ISearch)
	 */
	public Object searchUnique(ISearch search) throws NonUniqueResultException {
		return hibernateSearchProcessor.searchUnique(getCurrentSession(), search);
	}

	public void setHibernateSearchProcessor(HibernateSearchProcessor hibernateSearchProcessor) {
		this.hibernateSearchProcessor = hibernateSearchProcessor;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
