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

@Entity
@Table(name="SYS_PERMISSION")
public class SysPermission  implements Serializable{
	
	private static final long serialVersionUID = -5702570883126063842L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="NAME", nullable=false)
	private String name;
	
	@Column(name="DESCRITPION", nullable=false)
	private String descritpion;
	
	@Column(name="URL", nullable=false)
	private String url;
	
	@Column(name="PID", nullable=false)
	private Integer pid;
	
	@NotEmpty
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "SYS_PERMISSION_PROFILE", 
             joinColumns = { @JoinColumn(name = "SYS_PERMISSION_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "SYS_PROFILE_ID") })
	private Set<SysUserProfile> userProfiles = new HashSet<SysUserProfile>();
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescritpion() {
		return descritpion;
	}
	public void setDescritpion(String descritpion) {
		this.descritpion = descritpion;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Set<SysUserProfile> getUserProfiles() {
		return userProfiles;
	}
	public void setUserProfiles(Set<SysUserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}
	
	
}
