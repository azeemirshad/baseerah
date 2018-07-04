package com.baseerah.ui.beans.search;

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
import javax.faces.model.SelectItemGroup;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.util.Base64;

import com.baseerah.bll.EventBll;
import com.baseerah.bll.SearchBll;
import com.baseerah.dal.dao.Campus;
import com.baseerah.dal.dao.Event;
import com.baseerah.dal.dao.EventType;
import com.baseerah.dal.dao.Institute;
import com.baseerah.dal.dao.SourceType;
import com.baseerah.dal.dao.UserEvent;
import com.baseerah.dal.dao.UserProfile;
import com.baseerah.ui.beans.PageNavigationBean;
import com.baseerah.ui.beans.UserBean;
import com.baseerah.ui.beans.admin.CriteriaBean;
import com.baseerah.utils.Environment;
import com.baseerah.utils.BaseerahConstants;
import com.baseerah.utils.MessageUtils;
import com.baseerah.utils.NavigationConstants;
import com.google.gson.Gson;
import com.iac.web.util.FacesUtils;
import com.pacs.dal.dao.ApplicationUsers;



@ManagedBean(name="searchBean")
@SessionScoped
public class SearchBean implements Serializable
{
	
	private UserProfile toSearchUserProfile;
	
	private UserProfile selectedUserProfile;
	
	private SourceType sourceType;
	
	private List<SelectItem> sourceTypeItems;
	
	private List<Event> eventList;
	
	private Event selectedEvent;
	
	private boolean nazira;
	
	private boolean tajweed;
	
	private boolean arabic;
	
	private boolean updateUser;
	
	private String searchEventTitle;
	
	private Integer searchEventTypeId;;
	private List<SelectItem> eventTypeItems;
	
	private List<UserProfile> userProfileList = new ArrayList<UserProfile>();
	
	public SearchBean() 
	{
		// TODO Auto-generated constructor stub
		this.toSearchUserProfile = new UserProfile();
		selectedUserProfile = new UserProfile();
		sourceType = new SourceType();
		sourceTypeItems = new ArrayList<>();
		selectedEvent = new Event();
		 List<EventType> eventTypes;
		try {
//			System.out.println("Inside searchBean constructor");
			eventTypes = new EventBll().searchEventTypes();
			eventTypeItems = new ArrayList<>();
			String baseerahEvents = Environment.getProperty("baseerahEvents");
//			System.out.println("Baseerah Events .... " + baseerahEvents);
			String[] events = baseerahEvents.split(",");
			int[] ints = new int[events.length];
			for (int i = 0; i < events.length; i++) {
			    ints[i] = Integer.parseInt(events[i]);
			}
//			System.out.println("Baseerah Events array size .... " + ints.length);
			List<SelectItem> otherEventTypeItems = new ArrayList<>();
			List<SelectItem> baseerahEventTypeItems = new ArrayList<>();
			SelectItemGroup g1 = new SelectItemGroup("Baseerah");
			SelectItemGroup g2 = new SelectItemGroup("Others");
			boolean flag = false;
			for (EventType type : eventTypes) {
				
				for (int i = 0; i < ints.length; i++) {
					if(ints[i] == type.getId().intValue()){
//						System.out.println("Adding to Baseerah Events .... " + type.getEventType());
						baseerahEventTypeItems.add(new SelectItem(type.getId(), type.getEventType()));
						flag = true;
					}
						
				}
				if(!flag){
					System.out.println("Adding to Other Events .... " + type.getEventType());
					otherEventTypeItems.add(new SelectItem(type.getId(), type.getEventType()));
				}
				flag = false;
//				if(baseerahEvents.contains(String.valueOf(type.getId())))
//					eventTypeItems.add(new SelectItem(type.getId(), type.getEventType()));
			}
//			System.out.println("COnverting list to array .... " );
			g1.setSelectItems(baseerahEventTypeItems.toArray(new SelectItem[0]));
//			System.out.println("COnverting list to array g1.... " + g1.getSelectItems().length);
			g2.setSelectItems(otherEventTypeItems.toArray(new SelectItem[0]));
//			System.out.println("COnverting list to array g2.... " + g2.getSelectItems().length);
			
			eventTypeItems.add(g1);
			eventTypeItems.add(g2);
			System.out.println("Event type items length.... " + eventTypeItems.size());
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public String searchEvents()
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
			
		return "";
		
	}
	
	public void addEvent()
	{
		
		System.out.println("In addEvent method");
		if(this.selectedEvent!=null && selectedEvent.getId()!=null)
		{
			System.out.println("Adding event to user profile");
			UserEvent userEvent = new UserEvent();
			userEvent.setEvent(selectedEvent);
			userEvent.setUserProfile(selectedUserProfile);
			userEvent.setIsDelete(BaseerahConstants.DeleteStatus.No);
			if(selectedUserProfile.getUserEvents() == null){
				List<UserEvent> userEvents = new ArrayList<>();
				selectedUserProfile.setUserEvents(userEvents);
			}
			selectedUserProfile.getUserEvents().add(userEvent);
			selectedEvent = new Event();
		}
		
	}
	
	public void deleteEvent()
	{
		
		System.out.println("In deleteEvent method");
		if(this.selectedEvent!=null && selectedEvent.getId()!=null)
		{
			System.out.println("Deleting event from user profile events " +  selectedUserProfile.getUserEvents().size());
			for (UserEvent userEvent : selectedUserProfile.getUserEvents()) {
				if(userEvent.getEvent().getId().intValue() == selectedEvent.getId().intValue()){
					selectedUserProfile.getUserEvents().remove(userEvent);
				}
			}
			selectedEvent = new Event();
			System.out.println("After deletion user profile events " +  selectedUserProfile.getUserEvents().size());
		}
		
	}

	
	
	public String searchUserProfile()
	{
		SearchBll bll = new SearchBll();
		System.out.println("In searchUserProfile method");
		
//		if(dateTo.after(dateFrom))
//		{
			try {
				this.userProfileList = bll.searchUserProfileList(toSearchUserProfile, searchEventTitle, searchEventTypeId);
				System.out.println("Individuals list size :: " + this.userProfileList.size());
			
			} catch (Exception e) {
				MessageUtils.error(e.getMessage());
				e.printStackTrace();
			}
			
		
//		}
//		else
//		{
//			MessageUtils.error(BaseerahConstants.Messages.INVALID_DATE);
//		}
		 CriteriaBean cb = (CriteriaBean)FacesUtils.getManagedBean("crit");
		cb.setPageTitle(BaseerahConstants.Constants.PageTitles.SEARCH_INDL);
		
		return NavigationConstants.SEARCH_NAVIGATION;
		
		
//		return "";
	}
	
	 public void addSourceType() {
		 System.out.println("In addSourceType method");
		 SearchBll bll = new SearchBll();
		 try {
			if(sourceType.getName() !=null && !sourceType.getName().equals("") )
			{
				bll.addSourceType(sourceType);
				this.sourceTypeItems = new SearchBll().searchSourceTypeItems();
				sourceType = new SourceType();
				MessageUtils.info("Source added successfully");
			}
			else
				MessageUtils.info("You did not specify source name");
		} catch (Exception e) {
	 		FacesUtils.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	 }
	 
	 
	 public String saveUserProfile()
		{
			
			SearchBll bll =new SearchBll();
			
			
			if(selectedUserProfile ==null )
			{
				MessageUtils.error("Invalid user profile");
				return "";
			}

			try {
				if(tajweed) selectedUserProfile.setIsTajweed(1);
				if(arabic) selectedUserProfile.setIsArabic(1);
				if(nazira) selectedUserProfile.setIsNazira(1);
				if(!updateUser && !bll.validateMobileNo(selectedUserProfile.getPhone())){
//					if( )
//					{
						MessageUtils.error("Mobile No already exists. Visit Search Individual page for details");
						return "";
//					}
				}else{
					if(bll.addUserProfile(selectedUserProfile))
					{
	//				FacesUtils.addInfoMessage("Login credentials", BaseerahConstants.Messages.SAVE_SUCCESS);
						MessageUtils.info(BaseerahConstants.Messages.SAVE_SUCCESS);
						this.selectedUserProfile = new UserProfile();
						
					}
					else
					{
	//				FacesUtils.addErrorMessage("Login credentials", BaseerahConstants.Messages.SAVE_FAILURE);
						MessageUtils.error(BaseerahConstants.Messages.SAVE_FAILURE);
					}
//					return NavigationConstants.SEARCH_NAVIGATION;
					return this.searchUserProfile();
				}
			} catch (Exception e) {
				System.out.println("Exception in Bean  .... sendNotification &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
				e.printStackTrace();
				MessageUtils.error(e.getMessage());
			}
			
			return "";
		}
	 
	 
	 public String saveUserProfileAndContinue()
		{
			
			SearchBll bll =new SearchBll();
			
			
			if(selectedUserProfile ==null )
			{
				MessageUtils.error("Invalid user profile");
				return "";
			}
			
			
			try {
				if(tajweed) selectedUserProfile.setIsTajweed(1);
				if(arabic) selectedUserProfile.setIsArabic(1);
				if(nazira) selectedUserProfile.setIsNazira(1);
				if(!bll.validateMobileNo(selectedUserProfile.getPhone()) )
				{
					MessageUtils.error("Mobile No already exists. Visit Search Individual page for details");
					return "";
				}else{
					if(bll.addUserProfile(selectedUserProfile))
					{
	//				FacesUtils.addInfoMessage("Login credentials", BaseerahConstants.Messages.SAVE_SUCCESS);
						MessageUtils.info(BaseerahConstants.Messages.SAVE_SUCCESS);
						this.selectedUserProfile = new UserProfile();
						
					}
					else
					{
	//				FacesUtils.addErrorMessage("Login credentials", BaseerahConstants.Messages.SAVE_FAILURE);
						MessageUtils.error(BaseerahConstants.Messages.SAVE_FAILURE);
					}
					return "";
			}
			} catch (Exception e) {
				System.out.println("Exception in Bean  .... sendNotification &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
				e.printStackTrace();
				MessageUtils.error(e.getMessage());
			}
			
			return "";
		}
	
	public String createUserProfile(){
		
		try {
			updateUser = false;
			this.selectedUserProfile = new UserProfile();
			arabic = false;
			tajweed = false;
			nazira = false;
			
			sourceType = new SourceType();
			
			this.sourceTypeItems = new SearchBll().searchSourceTypeItems();
			
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
			e.printStackTrace();
			return "";
			
		}
		PageNavigationBean cb = (PageNavigationBean)FacesUtils.getManagedBean("navBean");
		return cb.navAddIndividualPage();
		
	}
	
	public String updateUserProfile(){
		
		try {
			updateUser = true;
			arabic = (selectedUserProfile.getIsArabic()!= null &&  selectedUserProfile.getIsArabic() == 1);
			tajweed = (selectedUserProfile.getIsTajweed()!=null && selectedUserProfile.getIsTajweed() == 1);
			nazira = (selectedUserProfile.getIsNazira()!=null && selectedUserProfile.getIsNazira() == 1);
			if(selectedUserProfile.getSourceType() == null)
				selectedUserProfile.setSourceType(new SourceType());
			
			this.sourceTypeItems = new SearchBll().searchSourceTypeItems();
			
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
			e.printStackTrace();
			return "";
			
		}
		PageNavigationBean cb = (PageNavigationBean)FacesUtils.getManagedBean("navBean");
		return cb.navUpdateIndividualPage();
		
	}
	
	public String cancelUpdateUser(){
		
		updateUser = false;
		
		return searchUserProfile();
		
	}
	
	public void validateMobileNo(){
		try {
			if (new SearchBll().validateMobileNo(this.selectedUserProfile.getPhone())){
				System.out.println("validateMobileNo .... true &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			}else
			{
				System.out.println("validateMobileNo .... false &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
				MessageUtils.error("Mobile No already exists. Visit Search Individual page for details");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			FacesUtils.addErrorMessage(e.getMessage());
		}
	}
	
	public void onRowEdit(RowEditEvent event) {
		UserProfile user = ((UserProfile) event.getObject());
		try {
			new SearchBll().updateUserProfile(user);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated ", "User " + ((UserProfile) event.getObject()).getName());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Updating ", "User " + ((UserProfile) event.getObject()).getName()
					+ " : " +e.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
        
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edit " , "Cancelled. " );
        FacesContext.getCurrentInstance().addMessage(null, msg);
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

	/**
	 * @return the toSearchUserProfile
	 */
	public UserProfile getToSearchUserProfile() {
		return toSearchUserProfile;
	}

	/**
	 * @param toSearchUserProfile the toSearchUserProfile to set
	 */
	public void setToSearchUserProfile(UserProfile toSearchUserProfile) {
		this.toSearchUserProfile = toSearchUserProfile;
	}

	/**
	 * @return the selectedUserProfile
	 */
	public UserProfile getSelectedUserProfile() {
		return selectedUserProfile;
	}

	/**
	 * @param selectedUserProfile the selectedUserProfile to set
	 */
	public void setSelectedUserProfile(UserProfile selectedUserProfile) {
		this.selectedUserProfile = selectedUserProfile;
	}
	/**
	 * @return the userProfileList
	 */
	public List<UserProfile> getUserProfileList() {
		return userProfileList;
	}

	/**
	 * @param userProfileList the userProfileList to set
	 */
	public void setUserProfileList(List<UserProfile> userProfileList) {
		this.userProfileList = userProfileList;
	}

	/**
	 * @return the nazira
	 */
	public boolean isNazira() {
		return nazira;
	}

	/**
	 * @param nazira the nazira to set
	 */
	public void setNazira(boolean nazira) {
		this.nazira = nazira;
	}

	/**
	 * @return the tajweed
	 */
	public boolean isTajweed() {
		return tajweed;
	}

	/**
	 * @param tajweed the tajweed to set
	 */
	public void setTajweed(boolean tajweed) {
		this.tajweed = tajweed;
	}

	/**
	 * @return the arabic
	 */
	public boolean isArabic() {
		return arabic;
	}

	/**
	 * @param arabic the arabic to set
	 */
	public void setArabic(boolean arabic) {
		this.arabic = arabic;
	}

	/**
	 * @return the sourceType
	 */
	public SourceType getSourceType() {
		return sourceType;
	}

	/**
	 * @param sourceType the sourceType to set
	 */
	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}

	/**
	 * @return the sourceTypeItems
	 */
	public List<SelectItem> getSourceTypeItems() {
		return sourceTypeItems;
	}

	/**
	 * @param sourceTypeItems the sourceTypeItems to set
	 */
	public void setSourceTypeItems(List<SelectItem> sourceTypeItems) {
		this.sourceTypeItems = sourceTypeItems;
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
	 * @return the updateUser
	 */
	public boolean isUpdateUser() {
		return updateUser;
	}

	/**
	 * @param updateUser the updateUser to set
	 */
	public void setUpdateUser(boolean updateUser) {
		this.updateUser = updateUser;
	}

		/**
	 * @return the searchEventTitle
	 */
	public String getSearchEventTitle() {
		return searchEventTitle;
	}

	/**
	 * @param searchEventTitle the searchEventTitle to set
	 */
	public void setSearchEventTitle(String searchEventTitle) {
		this.searchEventTitle = searchEventTitle;
	}

	/**
	 * @return the searchEventTypeId
	 */
	public Integer getSearchEventTypeId() {
		return searchEventTypeId;
	}

	/**
	 * @param searchEventTypeId the searchEventTypeId to set
	 */
	public void setSearchEventTypeId(Integer searchEventTypeId) {
		this.searchEventTypeId = searchEventTypeId;
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

	
	

}
