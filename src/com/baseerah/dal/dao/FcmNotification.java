package com.baseerah.dal.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 */

/**
 * @author Analysis
 *
 */
public class FcmNotification {
	private String to;
	private Notification notification;
	private List<KeyValuePair> data;
	
	public FcmNotification() {
		notification = new Notification();
		data = new ArrayList();
	}
	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}
	/**
	 * @return the notification
	 */
	public Notification getNotification() {
		return notification;
	}
	/**
	 * @param notification the notification to set
	 */
	public void setNotification(Notification notification) {
		this.notification = notification;
	}
	/**
	 * @return the data
	 */
	public List<KeyValuePair> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<KeyValuePair> data) {
		this.data = data;
	}
	
	
	

}
