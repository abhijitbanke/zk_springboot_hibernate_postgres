package ar.gfritz.com.org.sk;

import java.util.List;

import org.hibernate.NonUniqueResultException;

import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

public interface HibernateSearchSupport {

	int count(Class<?> searchClass, ISearch search);

	int count(ISearch search);

	String generateQL(Class<?> entityClass, ISearch search, List<Object> paramList);

	String generateRowCountQL(Class<?> entityClass, ISearch search, List<Object> paramList);

	Filter getFilterFromExample(Object example, ExampleOptions options);

	Filter getFilterFromExample(Object example);

	<T> List<T> search(Class<T> searchClass, ISearch search);

	<T> List<T> search(ISearch search);

	<T> SearchResult<T> searchAndCount(Class<T> searchClass, ISearch search);

	<T> SearchResult<T> searchAndCount(ISearch search);

	<T> T searchUnique(Class<T> entityClass, ISearch search) throws NonUniqueResultException;

	Object searchUnique(ISearch search) throws NonUniqueResultException;

}