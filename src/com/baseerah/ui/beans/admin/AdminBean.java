package com.baseerah.ui.beans.admin;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.model.DualListModel;

import com.baseerah.ui.beans.UserBean;
import com.baseerah.utils.Environment;
import com.baseerah.utils.BaseerahConstants;
import com.baseerah.utils.MessageUtils;
import com.baseerah.utils.NavigationConstants;
import com.iac.web.util.FacesUtils;
import com.pacs.bll.admin.AdminBll;
import com.pacs.dal.dao.ApplicationUsers;


@ManagedBean(name="adminBean")
@SessionScoped
public class AdminBean 
{
	
	private ApplicationUsers toAddUser;
	private ApplicationUsers toSearchUser;
	private ApplicationUsers selectedUser;
	
	
	
	
	private List<ApplicationUsers> usersList;
	
	private ApplicationUsers currentUser;
	private String newPassword = "";
	private String newPasswordAgain = "";
	
	
	private DualListModel<String> aetRolesDual;
	private List<String> srcAetList;
	private List<String> tgtAetList;
	
	private DualListModel<String> modRolesDual;
	private List<String> srcModList;
	private List<String> tgtModList;
	
	private String scannedFileType;
	private CriteriaBean crt = (CriteriaBean)FacesUtils.getManagedBean("crit");
	
	public AdminBean() 
	{		
		// TODO Auto-generated constructor stub
		toAddUser = new ApplicationUsers();
		toSearchUser = new ApplicationUsers();
		this.selectedUser = new ApplicationUsers();
		
		this.usersList = new ArrayList<ApplicationUsers>();
		
		this.srcAetList = new ArrayList<String>();
		this.tgtAetList = new ArrayList<String>();
		this.srcModList = new ArrayList<String>();
		this.tgtModList = new ArrayList<String>();
		this.aetRolesDual = new DualListModel<String>(srcAetList, tgtAetList);
		this.modRolesDual = new DualListModel<String>(srcModList, tgtModList);
		
		
	}
	
	public String addNewUser()
	{
		
		AdminBll bll =new AdminBll();
		
		if(toAddUser==null || toAddUser.getUserId()==null || toAddUser.getUserId().trim().length()<1)
		{
			MessageUtils.error(BaseerahConstants.Messages.INVALID_USERNAME);
			return "";
		}
		
		if(bll.addNewUser(toAddUser))
		{
//			FacesUtils.addInfoMessage("Login credentials", BaseerahConstants.Messages.SAVE_SUCCESS);
			MessageUtils.info(BaseerahConstants.Messages.SAVE_SUCCESS);
			this.toAddUser = new ApplicationUsers();
			
		}
		else
		{
//			FacesUtils.addErrorMessage("Login credentials", BaseerahConstants.Messages.SAVE_FAILURE);
			MessageUtils.error(BaseerahConstants.Messages.SAVE_FAILURE);
		}
		
		return "";
	}

	public String searchUsers()
	{
//		if(this.usersList.size()==0)
		{
			
			AdminBll bll =new AdminBll();
			this.usersList = bll.searchAllUser(toSearchUser);			

		}
		return "";
	}
	
	
	
	
	public String testMethod()
	{
		System.out.println("tgt aet list elmns method");
		for(String s:aetRolesDual.getTarget())
		{
			System.out.println("tgt aet list elmns ="+s);
		}
		return "";
	}
	
//	Not being used now---- see updateRoles()
	public String updateUsers()
	{

		AdminBll bll =new AdminBll();
		if(usersList.size()>0 &&  bll.updateUsers(usersList))
		{
//			FacesUtils.addInfoMessage(BaseerahConstants.Messages.UPDATE_SUCCESS);
			MessageUtils.info(BaseerahConstants.Messages.UPDATE_SUCCESS);
			this.usersList = bll.searchAllUser(toSearchUser);			
		}
		else
		{
//			FacesUtils.addErrorMessage(BaseerahConstants.Messages.UPDATE_FAILURE);
			MessageUtils.error(BaseerahConstants.Messages.UPDATE_FAILURE);
		}

		return "";
	}
	
	
	
	
	
	public String resetPassword()
	{
		AdminBll bll =new AdminBll();
		newPassword = Environment.getDefaultPassword();
		if(bll.changePassword(toSearchUser,newPassword))
		{
//			FacesUtils.addInfoMessage("Login credentials", BaseerahConstants.Messages.UPDATE_SUCCESS);
			MessageUtils.info(BaseerahConstants.Messages.UPDATE_SUCCESS);
			return NavigationConstants.ADMIN_MANAGE_USERS_NAVIGATION;			
		}
		else
		{
//			FacesUtils.addErrorMessage("Login credentials", BaseerahConstants.Messages.UPDATE_FAILURE);
			MessageUtils.error(BaseerahConstants.Messages.UPDATE_FAILURE);
		}

		return "";
	}
	
	public String changePassword()
	{
		currentUser = ((UserBean)FacesUtils.getManagedBean("userBean")).getCurrentUser();
		AdminBll bll =new AdminBll();
		
		if(newPassword==null || newPasswordAgain==null || 
				newPassword.trim().length()==0 	|| newPasswordAgain.trim().length()==0
				)
		{
//			FacesUtils.addErrorMessage("Login credentials", "Invalid Password");
			MessageUtils.error(BaseerahConstants.Messages.INVALID_PASSWORD);
			return "";
		}
		
		if(!newPassword.equals(newPasswordAgain))
		{
			MessageUtils.error("Passwords do not match");
			return "";
		}
		
		if(bll.changePassword(currentUser,newPassword))
		{
			MessageUtils.info(BaseerahConstants.Messages.UPDATE_SUCCESS);
			return "";//NavigationConstants.HOME_NAVIGATION;			
		}
		else
		{
			MessageUtils.error(BaseerahConstants.Messages.UPDATE_FAILURE);
		}

		return "";
	}
	
	
	
	
	public ApplicationUsers getToAddUser() {
		return toAddUser;
	}

	public void setToAddUser(ApplicationUsers toAddUser) {
		this.toAddUser = toAddUser;
	}

	public List<ApplicationUsers> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<ApplicationUsers> usersList) {
		this.usersList = usersList;
	}

	public ApplicationUsers getToSearchUser() {
		return toSearchUser;
	}

	public void setToSearchUser(ApplicationUsers toSearchUser) {
		this.toSearchUser = toSearchUser;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordAgain() {
		return newPasswordAgain;
	}

	public void setNewPasswordAgain(String newPasswordAgain) {
		this.newPasswordAgain = newPasswordAgain;
	}
	public String getScannedFileType() {
		return scannedFileType;
	}

	public void setScannedFileType(String scannedFileType) {
		this.scannedFileType = scannedFileType;
	}

	
	public ApplicationUsers getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(ApplicationUsers selectedUser) {
		this.selectedUser = selectedUser;
	}

	public DualListModel<String> getAetRolesDual() {
		return aetRolesDual;
	}

	public void setAetRolesDual(DualListModel<String> aetRolesDual) {
		this.aetRolesDual = aetRolesDual;
	}

	public DualListModel<String> getModRolesDual() {
		return modRolesDual;
	}

	public void setModRolesDual(DualListModel<String> modRolesDual) {
		this.modRolesDual = modRolesDual;
	}

	
}
