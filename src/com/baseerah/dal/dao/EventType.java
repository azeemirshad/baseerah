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
@Table(name = "event_type")
public class EventType {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="event_type")
	private String eventType;
	

    public EventType() {
		

	}
    
   

   
    @Override
    public String toString()
    {
        return  eventType;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}




	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}




	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}




}
