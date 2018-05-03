package com.baseerah.dal.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "user_profile")
public class UserProfile {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DOB")
	private Date dateOfBirth;
	
	@Column(name="mobile_no")
	private String phone;
	
	@Column(name = "insert_date")
	private Date insertDate;
	
	@Column(name ="IS_DELETE")
	private Integer isDelete;
	
	

    public UserProfile() {
		// TODO Auto-generated constructor stub
    	this.isDelete=0;
	}
    
    public UserProfile(String name,String phone){
        
        this.name=name;
        this.phone=phone;
        this.isDelete=0;
    }

   
    @Override
    public String toString()
    {
        return name+" | "+phone;
    }

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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	
}
