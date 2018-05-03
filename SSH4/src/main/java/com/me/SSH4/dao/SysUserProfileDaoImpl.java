package com.me.SSH4.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.me.SSH4.modal.SysUserProfile;



@Repository
public class SysUserProfileDaoImpl extends AbstractDao<Integer, SysUserProfile> implements SysUserProfileDao {

	@Override
	public List<SysUserProfile> findAll() {
		Criteria crit = createEntityCriteria();
		crit.addOrder(Order.asc("type"));
		return (List<SysUserProfile>) crit.list();
	}

	@Override
	public SysUserProfile findById (int id) {
		return getByKey(id);
	}

	@Override
	public SysUserProfile findByType(String type) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("type", type));
		return (SysUserProfile) crit.uniqueResult();
	}

}
