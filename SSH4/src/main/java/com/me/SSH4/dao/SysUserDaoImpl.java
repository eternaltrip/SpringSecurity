package com.me.SSH4.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.me.SSH4.modal.SysUser;

@Repository
public class SysUserDaoImpl extends AbstractDao<Integer, SysUser> implements SysUserDao {
	static final Logger logger = LoggerFactory.getLogger(SysUserDaoImpl.class);

	public SysUser findById(int id) {
		SysUser SysUser = getByKey(id);
		if (SysUser != null) {
			Hibernate.initialize(SysUser.getUserProfiles());
		}
		return SysUser;
	}

	public SysUser findBySSO(String sso) {
		logger.info("SSO : {}", sso);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("ssoId", sso));
		SysUser SysUser = (SysUser) crit.uniqueResult();
		List<SysUser> users = (List<SysUser>) crit.list();
		if (SysUser != null) {
			Hibernate.initialize(SysUser.getUserProfiles());
		}
		return SysUser;
	}

	@SuppressWarnings("unchecked")
	public List<SysUser> findAllUsers() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstName"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid duplicates.
		List<SysUser> users = (List<SysUser>) criteria.list();

		// No need to fetch userProfiles since we are not showing them on list page. Let
		// them lazy load.
		// Uncomment below lines for eagerly fetching of userProfiles if you want.
		/*
		 * for(SysUser SysUser : users){ Hibernate.initialize(SysUser.getUserProfiles()); }
		 */
		return users;
	}

	public void save(SysUser SysUser) {
		persist(SysUser);
	}

	public void deleteBySSO(String sso) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("ssoId", sso));
		SysUser SysUser = (SysUser) crit.uniqueResult();
		delete(SysUser);
	}

}
