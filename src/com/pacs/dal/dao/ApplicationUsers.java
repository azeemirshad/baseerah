package com.pacs.dal.dao;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "users")
public class ApplicationUsers 
{
	
	@Id // @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "user_id")
	private String userId;
	
	@Column(name="passwd")
	private String password;
	
	@Column(name="THEME", insertable=false)
	private String theme;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="appt")
	private String appt;
	
	@Column(name="dept")
	private String dept;
	
	@Column(name="role_admin", nullable=false)
	private String roleAdmin;
	
	
	
	public ApplicationUsers() 
	{
		// TODO Auto-generated constructor stub
	}

	
	public String getLoggedUserString()
	{
		return this.appt+"-"+this.userName;
	}
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getTheme() {
		return theme;
	}


	public void setTheme(String theme) {
		this.theme = theme;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getAppt() {
		return appt;
	}


	public void setAppt(String appt) {
		this.appt = appt;
	}


	public String getDept() {
		return dept;
	}


	public void setDept(String dept) {
		this.dept = dept;
	}


	public String getRoleAdmin() {
		return roleAdmin;
	}


	public void setRoleAdmin(String roleAdmin) {
		this.roleAdmin = roleAdmin;
	}
	

}
