package com.me.SSH4.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.me.SSH4.modal.SysPermission;


@Repository
@Transactional
public class SysPermissionDaoImpl extends AbstractDao<Integer, SysPermission>  implements SysPermissionDao {

	@Override
	public List<SysPermission> findAllSysPermission() {
		Criteria crit = createEntityCriteria();
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<SysPermission>) crit.list();
	}

}
