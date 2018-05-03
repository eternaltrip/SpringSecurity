package com.me.springsecurity.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.me.springsecurity.dao.AbstractDao;
import com.me.springsecurity.dao.SysUserDao;
import com.me.springsecurity.model.SysUser;


@Repository
public class SysUserDaoImpl  extends AbstractDao<Integer, SysUser> implements SysUserDao {

	@Override
	public SysUser findById(int id) {
		return getByKey(id);
	}

	@Override
	public SysUser findBySSO(String sso) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("ssoId", sso));
		return (SysUser) criteria.uniqueResult();
	}

	@Override
	public void save(SysUser user) {
		persist(user);
		
	}

}
