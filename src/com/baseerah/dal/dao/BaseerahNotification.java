package com.baseerah.dal.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.Gson;



@Entity
@Table(name = "baseerah_notification")
public class BaseerahNotification {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="notification_json")
	private String notificationJson;
	
	@Column(name="delivery_date")
	private Date deliveryDate;
	
	@Column(name="status")
	private String status;
	
	
	@Column(name ="IS_DELETE")
	private Integer isDelete;
	


    public BaseerahNotification() {
		
    	this.isDelete=0;
	}
    
   

   
    @Override
    public String toString()
    {
        return deliveryDate +" | " + notificationJson;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}




	/**
	 * @return the notificationJson
	 */
	public String getNotificationJson() {
		return notificationJson;
	}




	/**
	 * @param notificationJson the notificationJson to set
	 */
	public void setNotificationJson(String notificationJson) {
		this.notificationJson = notificationJson;
	}




	/**
	 * @return the deliveryDate
	 */
	public Date getDeliveryDate() {
		return deliveryDate;
	}




	/**
	 * @param deliveryDate the deliveryDate to set
	 */
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}




	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}




	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}



	
}
