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
import javax.faces.model.SelectItem;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.util.Base64;

import com.baseerah.bll.EventBll;
import com.baseerah.bll.SearchBll;
import com.baseerah.dal.dao.BaseerahNotification;
import com.baseerah.dal.dao.Campus;
import com.baseerah.dal.dao.Event;
import com.baseerah.dal.dao.EventType;
import com.baseerah.dal.dao.Institute;
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



@ManagedBean(name="eventsBean")
@SessionScoped
public class EventsBean implements Serializable
{
	

//	private BaseerahNotification notification;
	private Event selectedEvent;
	private List<Institute> institutes;
	
	private List<SelectItem> instituteItems;
	
	private List<SelectItem> campusItems;
	
	private List<SelectItem> eventTypeItems;
	
	private Institute institute;
	
	private EventType eventType;
	
	private Campus campus;
	
	private List<Campus> campuses =  new ArrayList<Campus>();
	
	private List<EventType> eventTypes =  new ArrayList<EventType>();
	
	private List<Event> eventList = new ArrayList<Event>();
	
	public EventsBean() 
	{
		// TODO Auto-generated constructor stub
		this.selectedEvent = new Event();
		institute = new Institute();
		campus = new Campus();
		eventType = new EventType();
	
		
	}
	
	public String createEvent(){
		
		try {
			this.selectedEvent = new Event();
			institute = new Institute();
			campus = new Campus();
			this.institutes = new EventBll().searchInstitutes();
			instituteItems = new ArrayList<>();
			for (Institute institute : institutes) {
				instituteItems.add(new SelectItem(institute.getId(), institute.getName()));
			}
			
			this.eventTypes = new EventBll().searchEventTypes();
			eventTypeItems = new ArrayList<>();
			for (EventType type : eventTypes) {
				eventTypeItems.add(new SelectItem(type.getId(), type.getEventType()));
			}
			
			
			
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
			e.printStackTrace();
			return "";
			
		}
		PageNavigationBean cb = (PageNavigationBean)FacesUtils.getManagedBean("navBean");
		return cb.navAddEventPage();
		
	}
	
	 public void onInstituteChange() {
		 try {
			 if(selectedEvent.getInstitute() !=null && selectedEvent.getInstitute().getId() !=null)
			 {
					campuses = new EventBll().searchCampuses(selectedEvent.getInstitute());
					campusItems = new ArrayList<>();
					for (Campus campus : campuses) {
						campusItems.add(new SelectItem(campus.getId(), campus.getName()));
						
					}
			 }	
				
			 else
	        	campuses = new ArrayList<>();
		 	} catch (Exception e) {
		 		FacesUtils.addErrorMessage(e.getMessage());
				e.printStackTrace();
			}
	}
	 
	 public void addInstitute() {
		 System.out.println("In addInstitute method");
		 EventBll bll = new EventBll();
		 try {
			if(institute.getName() !=null && !institute.getName().equals("") )
			{
				bll.addInstitute(institute);
				institutes = bll.searchInstitutes();
				instituteItems = new ArrayList<>();
				for (Institute institute : institutes) {
					instituteItems.add(new SelectItem(institute.getId(), institute.getName()));
				}
				institute = new Institute();
				MessageUtils.info("Institute added successfully");
			}
			else
				MessageUtils.info("You did not specify institute name");
		} catch (Exception e) {
	 		FacesUtils.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	 }
	 
	 public void addEventType() {
		 System.out.println("In addEventType method");
		 EventBll bll = new EventBll();
		 try {
			if(eventType.getEventType() !=null && !eventType.getEventType().equals("") )
			{
				bll.addEventType(eventType);
				eventTypes = bll.searchEventTypes();
				eventTypeItems = new ArrayList<>();
				for (EventType type : eventTypes) {
					eventTypeItems.add(new SelectItem(type.getId(), type.getEventType()));
				}
				eventType = new EventType();
				MessageUtils.info("Event type added successfully");
			}
			else
				MessageUtils.info("You did not specify event type");
		} catch (Exception e) {
	 		FacesUtils.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	 }
	 
	 public void addCampus() {
		 System.out.println("In addCampus method " + campus.getInstitute().getId());
		 EventBll bll = new EventBll();
		 try {
			if(campus.getName() !=null && !campus.getName().equals("") && campus.getInstitute().getId() !=null 
					&& campus.getInstitute().getId() != -1)
			{
				bll.addCampus(campus);
				campuses = bll.searchCampuses(selectedEvent.getInstitute());
				campusItems = new ArrayList<>();
				for (Campus campus : campuses) {
					campusItems.add(new SelectItem(campus.getId(), campus.getName()));
					
				}
				campus = new Campus();
				MessageUtils.info("Campus added successfully");
			}
			else
				FacesUtils.addErrorMessage("You did not specify campus attributes");
		} catch (Exception e) {
	 		FacesUtils.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	 }	
	 
	 public void addCampusEvent(ActionEvent event) {
		 System.out.println("In addCampus ActionEvent method");
		 EventBll bll = new EventBll();
		 try {
			if(campus.getName() !=null && !campus.getName().equals("") &&campus.getInstitute().getId() !=null )
			{
				bll.addCampus(campus);
				campuses = bll.searchCampuses(selectedEvent.getInstitute());
				campusItems = new ArrayList<>();
				for (Campus campus : campuses) {
					campusItems.add(new SelectItem(campus.getId(), campus.getName()));
					
				}
				campus = new Campus();
				MessageUtils.info("Campus added successfully");
			}
			else
				FacesUtils.addErrorMessage("You did not specify campus attributes");
		} catch (Exception e) {
	 		FacesUtils.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	 }	
	 
	public String editEvent(){
		
		try {
			this.institutes = new EventBll().searchInstitutes();
			if(selectedEvent.getEventType() == null)
			{
				selectedEvent.setEventType(new EventType());
			}
			this.institutes = new EventBll().searchInstitutes();
			instituteItems = new ArrayList<>();
			for (Institute institute : institutes) {
				instituteItems.add(new SelectItem(institute.getId(), institute.getName()));
			}
			
			this.eventTypes = new EventBll().searchEventTypes();
			eventTypeItems = new ArrayList<>();
			for (EventType type : eventTypes) {
				eventTypeItems.add(new SelectItem(type.getId(), type.getEventType()));
			}
			campuses = new EventBll().searchCampuses(selectedEvent.getInstitute());
			campusItems = new ArrayList<>();
			for (Campus campus : campuses) {
				campusItems.add(new SelectItem(campus.getId(), campus.getName()));
				
			}
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
			e.printStackTrace();
			return "";
		}

		return NavigationConstants.ADD_EVENT;
	}

	
	
	public String searchEvent()
	{
		EventBll bll = new EventBll();
		System.out.println("In searchEvent method");
		
		try {
			this.eventList = bll.searchEventList();
			System.out.println("Event list size :: " + this.eventList.size());
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
			
//		return NavigationConstants.SEARCH_NOTIFICATION;
		PageNavigationBean cb = (PageNavigationBean)FacesUtils.getManagedBean("navBean");
		return cb.navSearchEventPage();
	}
	
	
	public String deleteEvent()
	{
		EventBll bll = new EventBll();
		System.out.println("In deleteEvent method");
		
		System.out.println(this.selectedEvent.getTitle());
		try {
			bll.deleteEvent(selectedEvent);
			MessageUtils.info("Event deleted successfully");
			this.searchEvent();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			MessageUtils.error("Error deleting event " + e.getMessage());
		}
			
		
		return "";
	}
	
	public String saveEvent()
	{
		
		EventBll bll =new EventBll();
		
		
		if(selectedEvent ==null )
		{
			MessageUtils.error("Invalid Event / Course");
			return "";
		}
		
		
		try {
			if(bll.addEvent(selectedEvent))
			{
//			FacesUtils.addInfoMessage("Login credentials", BaseerahConstants.Messages.SAVE_SUCCESS);
				MessageUtils.info(BaseerahConstants.Messages.SAVE_SUCCESS);
				this.selectedEvent = new Event();
				
			}
			else
			{
//			FacesUtils.addErrorMessage("Login credentials", BaseerahConstants.Messages.SAVE_FAILURE);
				MessageUtils.error(BaseerahConstants.Messages.SAVE_FAILURE);
			}
			return this.searchEvent();
		} catch (Exception e) {
			System.out.println("Exception in Bean  .... sendNotification &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			e.printStackTrace();
			MessageUtils.error(e.getMessage());
		}
		
		return "";
	}
	
	
	
	/**
	 * @return the eventList
	 */
	public List<Event> getEventList() {
		return eventList;
	}

	/**
	 * @param eventList the eventList to set
	 */
	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}

	/**
	 * @return the selectedEvent
	 */
	public Event getSelectedEvent() {
		return selectedEvent;
	}

	/**
	 * @param selectedEvent the selectedEvent to set
	 */
	public void setSelectedEvent(Event selectedEvent) {
		this.selectedEvent = selectedEvent;
	}

	/**
	 * @return the institutes
	 */
	public List<Institute> getInstitutes() {
		return institutes;
	}

	/**
	 * @param institutes the institutes to set
	 */
	public void setInstitutes(List<Institute> institutes) {
		this.institutes = institutes;
	}

	/**
	 * @return the campuses
	 */
	public List<Campus> getCampuses() {
		return campuses;
	}

	/**
	 * @param campuses the campuses to set
	 */
	public void setCampuses(List<Campus> campuses) {
		this.campuses = campuses;
	}

	/**
	 * @return the institute
	 */
	public Institute getInstitute() {
		return institute;
	}

	/**
	 * @param institute the institute to set
	 */
	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	/**
	 * @return the campus
	 */
	public Campus getCampus() {
		return campus;
	}

	/**
	 * @param campus the campus to set
	 */
	public void setCampus(Campus campus) {
		this.campus = campus;
	}

	/**
	 * @return the instituteItems
	 */
	public List<SelectItem> getInstituteItems() {
		return instituteItems;
	}

	/**
	 * @param instituteItems the instituteItems to set
	 */
	public void setInstituteItems(List<SelectItem> instituteItems) {
		this.instituteItems = instituteItems;
	}

	/**
	 * @return the campusItems
	 */
	public List<SelectItem> getCampusItems() {
		return campusItems;
	}

	/**
	 * @param campusItems the campusItems to set
	 */
	public void setCampusItems(List<SelectItem> campusItems) {
		this.campusItems = campusItems;
	}

	/**
	 * @return the eventTypeItems
	 */
	public List<SelectItem> getEventTypeItems() {
		return eventTypeItems;
	}

	/**
	 * @param eventTypeItems the eventTypeItems to set
	 */
	public void setEventTypeItems(List<SelectItem> eventTypeItems) {
		this.eventTypeItems = eventTypeItems;
	}

	/**
	 * @return the eventTypes
	 */
	public List<EventType> getEventTypes() {
		return eventTypes;
	}

	/**
	 * @param eventTypes the eventTypes to set
	 */
	public void setEventTypes(List<EventType> eventTypes) {
		this.eventTypes = eventTypes;
	}

	/**
	 * @return the eventType
	 */
	public EventType getEventType() {
		return eventType;
	}

	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	
		

}
