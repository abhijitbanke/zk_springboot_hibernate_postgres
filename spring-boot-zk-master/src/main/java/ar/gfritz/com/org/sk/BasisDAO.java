package ar.gfritz.com.org.sk;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
 
/**
 * @author bj
 * 
 */
public abstract class BasisDAO<T> {
    private HibernateTemplate hibernateTemplate;
 
    /**
     * constructor
     */
    protected BasisDAO() {
    }
 
    protected HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }
 
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }
 
    protected void initialize(final T proxy) throws DataAccessException {
        hibernateTemplate.initialize(proxy);
    }
 
    protected void initialize(final Collection<T> proxy) throws DataAccessException {
        hibernateTemplate.initialize(proxy);
    }
 
    @SuppressWarnings("unchecked")
    protected T merge(T entity) throws DataAccessException {
        return (T) hibernateTemplate.merge(entity);
    }
 
    protected void persist(T entity) throws DataAccessException {
        hibernateTemplate.persist(entity);
    }
 
    protected void refresh(T entity) throws DataAccessException {
        hibernateTemplate.refresh(entity);
    }
 
    protected void save(T entity) throws DataAccessException {
        hibernateTemplate.save(entity);
    }
 
    // public void saveOrUpdate(T entity) throws DataAccessException {
    // hibernateTemplate.saveOrUpdate(entity);
    // }
 
    // public void saveOrUpdateAll(Collection<T> entities) throws
    // DataAccessException {
    // for (T entity : entities) {
    // saveOrUpdate(entity);
    // }
    // }
 
    protected void update(T entity) throws DataAccessException {
        hibernateTemplate.update(entity);
    }
 
    protected void delete(T entity) throws DataAccessException {
        hibernateTemplate.delete(entity);
    }
 
    protected void delete(final Collection<T> proxy) throws DataAccessException {
        hibernateTemplate.deleteAll(proxy);
    }
}