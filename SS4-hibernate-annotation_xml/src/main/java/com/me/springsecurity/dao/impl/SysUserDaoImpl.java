package com.me.springsecurity.dao.impl;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.me.springsecurity.dao.AbstractDao;
import com.me.springsecurity.dao.SysUserDao;
import com.me.springsecurity.model.SysUser;


@Repository
public class SysUserDaoImpl extends AbstractDao<Integer , SysUser> implements SysUserDao {

	@Autowired
	private HibernateTemplate template;
	
	
	
	@Override
	public SysUser findById(int id) {
		//return template.get(SysUser.class, id);
		return getByKey(id);
	}

	@Override
	public SysUser findBySSO(String ssoId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("ssoId", ssoId));
		return (SysUser) criteria.uniqueResult();
	}

}
