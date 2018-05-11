/**
 * 
 */
package com.baseerah.dal.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Analysis
 *
 */
@Entity
@Table(name = "user_event")
public class UserEvent {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@ManyToOne 
	@JoinColumn (name = "user_profile_id")
	private UserProfile userProfile ;
	
	@ManyToOne 
	@JoinColumn (name = "event_id")
	private Event event ;
	
	@Column(name ="IS_DELETE")
	private Integer isDelete;

	 public UserEvent() {
			// TODO Auto-generated constructor stub
	    	this.isDelete=0;
	    	event = new Event();
	    	userProfile = new UserProfile();
	}
	 
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the userProfile
	 */
	public UserProfile getUserProfile() {
		return userProfile;
	}

	/**
	 * @param userProfile the userProfile to set
	 */
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	/**
	 * @return the isDelete
	 */
	public Integer getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	

}
