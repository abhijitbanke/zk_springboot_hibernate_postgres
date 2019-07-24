package ar.gfritz.com.org.sk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gfritz.com.org.sk.HibernateSearchObject;
import ar.gfritz.com.org.sk.HibernateSearchSupport;
import ar.gfritz.com.org.sk.service.PagedListService;

import com.googlecode.genericdao.search.SearchResult;

@Service("pagedListService")
public class PagedListServiceImpl implements PagedListService {
	@Autowired
	private HibernateSearchSupport hibernateSearchSupport;

	public HibernateSearchSupport getHibernateSearchSupport() {
		return hibernateSearchSupport;
	}

	public void setHibernateSearchSupport(HibernateSearchSupport hibernateSearchSupport) {
		this.hibernateSearchSupport = hibernateSearchSupport;
	}

	@SuppressWarnings("unused")
	private <T> void initSearchObject(HibernateSearchObject<T> so, int start, int pageSize) {
		so.setFirstResult(start);
		so.setMaxResults(pageSize);
	}

	@Override
	public <T> List<T> getBySearchObject(HibernateSearchObject<T> so) {
		return getHibernateSearchSupport().search(so);
	}

	@Override
	public <T> SearchResult<T> getSRBySearchObject(HibernateSearchObject<T> so) {
		return getHibernateSearchSupport().searchAndCount(so);
	}

}
