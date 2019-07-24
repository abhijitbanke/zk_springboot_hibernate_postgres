package ar.gfritz.com.org.sk;

import java.io.Serializable;

import com.googlecode.genericdao.search.Search;

/**
 * EN: SearchObject depending on the Search Object from the
 * Hibernate-Generic-DAO framework. <br>
 * DE: SearchObject aufbauend auf dem Search object des
 * <b>Hibernate-Generic-DAO</b> frameworks.<br>
 * 
 * @see http://code.google.com/p/hibernate-generic-dao/ <br>
 *      Many thanks to David Wolverton.
 * 
 * @author bbruhns
 * @author sgerth
 * 
 * @param <E>
 */
public class HibernateSearchObject<E> extends Search implements Serializable {

	private static final long serialVersionUID = 1L;

	public HibernateSearchObject(Class<E> entityClass) {
		super(entityClass);
	}

	public HibernateSearchObject(Class<E> entityClass, int pageSize) {
		super(entityClass);
		setFirstResult(0);
		setMaxResults(pageSize);
	}
}
