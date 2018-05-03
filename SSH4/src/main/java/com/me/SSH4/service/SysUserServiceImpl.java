package com.me.SSH4.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.me.SSH4.dao.SysUserDao;
import com.me.SSH4.modal.SysUser;



@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {

		
	@Autowired
	private SysUserDao sysUserDao;
	
	@Autowired  
    private PasswordEncoder passwordEncoder; 
	
	@Override
	public SysUser findById(int id) {
		return sysUserDao.findById(id);
	}

	@Override
	public SysUser findBySSO(String sso) {
		return sysUserDao.findBySSO(sso);
	}
	@Override
	public void saveUser(SysUser sysUser) {
		sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
		sysUserDao.save(sysUser);
	}
	  /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends. 
     */
    public void updateUser(SysUser SysUser) {
        SysUser entity = sysUserDao.findById(SysUser.getId());
        if(entity!=null){
            entity.setSsoId(SysUser.getSsoId());
            if(!SysUser.getPassword().equals(entity.getPassword())){
                entity.setPassword(passwordEncoder.encode(SysUser.getPassword()));
            }
            entity.setFirstName(SysUser.getFirstName());
            entity.setLastName(SysUser.getLastName());
            entity.setEmail(SysUser.getEmail());
            entity.setUserProfiles(SysUser.getUserProfiles());
        }
    }
 
     
    public void deleteUserBySSO(String sso) {
    	sysUserDao.deleteBySSO(sso);
    }
 
    public List<SysUser> findAllUsers() {
        return sysUserDao.findAllUsers();
    }
 
    public boolean isUserSSOUnique(Integer id, String sso) {
        SysUser SysUser = findBySSO(sso);
        return ( SysUser == null || ((id != null) && (SysUser.getId() == id)));
    }




}
