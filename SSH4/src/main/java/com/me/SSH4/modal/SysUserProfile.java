package com.me.SSH4.modal;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 用户资料（描述用户的角色类型）
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "SYS_PROFILE")
public class SysUserProfile  implements Serializable{

	private static final long serialVersionUID = 8282046624951976539L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;	

	/**
	 * 用户类型
	 */
	@Column(name="TYPE", length=15, unique=true, nullable=false)
	private String type = SysUserProfileType.USER.getUserProfileType();
	
	
	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "SYS_PERMISSION_PROFILE", 
             joinColumns = { @JoinColumn(name = "SYS_PROFILE_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "SYS_PERMISSION_ID") })
	private Set<SysPermission> permissions = new HashSet<SysPermission>();

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	/**用户类型 ADMIN/DBA/USER... */
	public String getType() {
		return type;
	}
	/**用户类型 ADMIN/DBA/USER...*/
	public void setType(String type) {
		this.type = type;
	}

	
	public Set<SysPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<SysPermission> permissions) {
		this.permissions = permissions;
	}

	
	
}
