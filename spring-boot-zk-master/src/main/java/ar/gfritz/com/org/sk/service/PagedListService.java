package ar.gfritz.com.org.sk.service;

import java.util.List;

import ar.gfritz.com.org.sk.HibernateSearchObject;

import com.googlecode.genericdao.search.SearchResult;


/**
 * EN: Service methods Interface for working with <b>pagedListWrappers</b>
 * dependend DAOs.<br>
 * DE: Service Methoden Implementierung fuer die <b>pagedListWrappers</b>
 * betreffenden DAOs.<br>
 * 
 * @author bbruhns
 * @author sgerth
 */
public interface PagedListService {

	public <T> List<T> getBySearchObject(HibernateSearchObject<T> so);

	public <T> SearchResult<T> getSRBySearchObject(HibernateSearchObject<T> so);
}