package com.baseerah.ui.beans;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.util.Base64;

import com.baseerah.bll.SearchBll;
import com.baseerah.dal.dao.BaseerahNotification;
import com.baseerah.dal.dao.KeyValuePair;
import com.baseerah.dal.dao.NotificationWFObject;
import com.baseerah.dal.dao.UserProfile;
import com.baseerah.ui.beans.UserBean;
import com.baseerah.ui.beans.admin.CriteriaBean;
import com.baseerah.utils.Environment;
import com.baseerah.utils.BaseerahConstants;
import com.baseerah.utils.MessageUtils;
import com.baseerah.utils.NavigationConstants;
import com.google.gson.Gson;
import com.iac.web.util.FacesUtils;
import com.pacs.bll.admin.AdminBll;
import com.pacs.dal.dao.ApplicationUsers;



@ManagedBean(name="notificationBean")
@SessionScoped
public class NotificationBean implements Serializable
{
	
	private String topic = Environment.getProperty("topic");
	private static final String FCM_API_KEY = Environment.getProperty("serverKey");
	private static final String FCM_URL = Environment.getProperty("fcmUrl"); 
	
//	private BaseerahNotification notification;
	private NotificationWFObject notificationWf;
	private KeyValuePair selectedPair;
	
	private List<NotificationWFObject> notificationList = new ArrayList<NotificationWFObject>();
	
	public NotificationBean() 
	{
		// TODO Auto-generated constructor stub
		this.notificationWf = new NotificationWFObject();
		
	}
	
	public String createNotification(){
		this.notificationWf = new NotificationWFObject();
		if(this.notificationWf.getFcmNotification().getData().size() == 0){
			KeyValuePair pair = new KeyValuePair("url", "");
			this.notificationWf.getFcmNotification().getData().add(pair);
		}
		return NavigationConstants.ADD_NOTIFICATION;
	}
	
	public String duplicateNotification(){
//		this.notificationWf = new NotificationWFObject();
//		if(this.notificationWf.getFcmNotification().getData().size() == 0){
//			KeyValuePair pair = new KeyValuePair("url", "");
//			this.notificationWf.getFcmNotification().getData().add(pair);
//		}
		if(this.notificationWf.getBasNotif()!=null && this.notificationWf.getBasNotif().getDeliveryDate()!=null){
			this.notificationWf.getBasNotif().setDeliveryDate(null);
		}
		return NavigationConstants.ADD_NOTIFICATION;
	}

	public void addCustomField(){
		
//		this.notificationWf.getFcmNotification().getData().put(
//				"Key-" + this.notificationWf.getFcmNotification().getData().size() + 1, "");
		this.notificationWf.getFcmNotification().getData().add(
				new KeyValuePair("Key-" + this.notificationWf.getFcmNotification().getData().size() + 1, ""));
		
	}
	
	public void deletePair() {
		this.notificationWf.getFcmNotification().getData().remove(selectedPair);
        selectedPair = null;
    }
	
	
	public String searchNotification()
	{
		SearchBll bll = new SearchBll();
		System.out.println("In searchNotification method");
		
		try {
			this.notificationList = bll.searchNotificationList();
			System.out.println("Notification list size :: " + this.notificationList.size());
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
			
//		return NavigationConstants.SEARCH_NOTIFICATION;
		PageNavigationBean cb = (PageNavigationBean)FacesUtils.getManagedBean("navBean");
		return cb.navSearchNotificationPage();
	}
	
	
	public String deleteNotification()
	{
		SearchBll bll = new SearchBll();
		System.out.println("In deleteNotification method");
		
		System.out.println(this.notificationWf.getFcmNotification().getNotification().getTitle());
		try {
			bll.deleteNotification(notificationWf.getBasNotif());
			MessageUtils.info("Notification deleted successfully");
			return this.searchNotification();
		} catch (Exception e) {
			
			e.printStackTrace();
			MessageUtils.error("Error deleting data " + e.getMessage());
		}
			
		
		return "";
	}
	
	public String sendNotification()
	{
		
		SearchBll bll =new SearchBll();
		
		
		if(notificationWf==null || notificationWf.getFcmNotification() ==null 
				|| notificationWf.getFcmNotification().getNotification().getTitle() == null)
		{
			MessageUtils.error("Invalid Notification");
			return "";
		}
		notificationWf.getFcmNotification().setTo(this.getTopic());
		
		try {
			if(bll.sendNotification(notificationWf))
			{
//			FacesUtils.addInfoMessage("Login credentials", BaseerahConstants.Messages.SAVE_SUCCESS);
				MessageUtils.info(BaseerahConstants.Messages.SAVE_SUCCESS);
				this.notificationWf = new NotificationWFObject();
				
			}
			else
			{
//			FacesUtils.addErrorMessage("Login credentials", BaseerahConstants.Messages.SAVE_FAILURE);
				MessageUtils.error(BaseerahConstants.Messages.SAVE_FAILURE);
			}
			return this.searchNotification();
		} catch (Exception e) {
			System.out.println("Exception in Bean  .... sendNotification &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			e.printStackTrace();
			MessageUtils.error(e.getMessage());
		}
		
		return "";
	}
	
	
	public void resendNotification()
	{
		
		SearchBll bll =new SearchBll();
		
		
		if(notificationWf==null || notificationWf.getFcmNotification() ==null 
				|| notificationWf.getFcmNotification().getNotification().getTitle() == null)
		{
			MessageUtils.error("Invalid Notification");
			
		}
//		notificationWf.getFcmNotification().setTo(this.getTopic());
		
		try {
			if(bll.resendNotification(notificationWf))
			{
//			FacesUtils.addInfoMessage("Login credentials", BaseerahConstants.Messages.SAVE_SUCCESS);
				MessageUtils.info("Notification sent successfully");
				this.notificationWf = new NotificationWFObject();
				
			}
			else
			{
//			FacesUtils.addErrorMessage("Login credentials", BaseerahConstants.Messages.SAVE_FAILURE);
				MessageUtils.error("Notification sending failed");
			}
			this.searchNotification();
		} catch (Exception e) {
			System.out.println("Exception in Bean  .... sendNotification &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			e.printStackTrace();
			MessageUtils.error(e.getMessage());
		}
		
//		return "";
	}
	
//	public void onCellEdit(CellEditEvent event) {
//		System.out.println("Cell Changed ************ " + event.getRowKey() + " :: Index : " + event.getRowIndex());
//				
//        Object oldValue = event.getOldValue();
//        Object newValue = event.getNewValue();
//         
//        if(newValue != null && !newValue.equals(oldValue)) {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//    }

	
//	/**
//	 * @return the notification
//	 */
//	public BaseerahNotification getNotification() {
//		return notification;
//	}
//
//	/**
//	 * @param notification the notification to set
//	 */
//	public void setNotification(BaseerahNotification notification) {
//		this.notification = notification;
//	}

	/**
	 * @return the notificationList
	 */
	public List<NotificationWFObject> getNotificationList() {
		return notificationList;
	}

	/**
	 * @param notificationList the notificationList to set
	 */
	public void setNotificationList(List<NotificationWFObject> notificationList) {
		this.notificationList = notificationList;
	}

	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @return the notificationWf
	 */
	public NotificationWFObject getNotificationWf() {
		return notificationWf;
	}

	/**
	 * @param notificationWf the notificationWf to set
	 */
	public void setNotificationWf(NotificationWFObject notificationWf) {
		this.notificationWf = notificationWf;
	}

	/**
	 * @return the selectedPair
	 */
	public KeyValuePair getSelectedPair() {
		return selectedPair;
	}

	/**
	 * @param selectedPair the selectedPair to set
	 */
	public void setSelectedPair(KeyValuePair selectedPair) {
		this.selectedPair = selectedPair;
	}
	
		

}
