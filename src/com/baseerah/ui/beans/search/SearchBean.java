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

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.util.Base64;

import com.baseerah.dal.dao.UserProfile;
import com.baseerah.ui.beans.UserBean;
import com.baseerah.ui.beans.admin.CriteriaBean;
import com.baseerah.utils.Environment;
import com.baseerah.utils.BaseerahConstants;
import com.baseerah.utils.NavigationConstants;
import com.google.gson.Gson;
import com.iac.web.util.FacesUtils;
import com.pacs.bll.search.SearchBll;
import com.pacs.dal.dao.ApplicationUsers;
import com.pacs.utils.MessageUtils;



@ManagedBean(name="searchBean")
@SessionScoped
public class SearchBean implements Serializable
{
	
	private UserProfile toSearchUserProfile;
	
	private UserProfile selectedUserProfile;
	
	private List<UserProfile> userProfileList = new ArrayList<UserProfile>();
	
	public SearchBean() 
	{
		// TODO Auto-generated constructor stub
		this.toSearchUserProfile = new UserProfile();
		
	}

	public String searchUserProfile()
	{
		SearchBll bll = new SearchBll();
		System.out.println("In searchUserProfile method");
		
//		if(dateTo.after(dateFrom))
		{
			this.userProfileList = bll.searchUserProfileList(toSearchUserProfile);
			System.out.println("Patients list size :: " + this.userProfileList.size());
		
		}
//		else
//		{
//			MessageUtils.error(BaseerahConstants.Messages.INVALID_DATE);
//		}
		
		
		
		return "";
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
	
		

}
