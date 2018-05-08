/**
 * 
 */
package com.baseerah.ui.beans;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.baseerah.ui.beans.admin.CriteriaBean;
import com.baseerah.utils.BaseerahConstants;
import com.baseerah.utils.NavigationConstants;
import com.iac.web.util.FacesUtils;

/**
 * @author 
 *
 */
@ManagedBean(name = "navBean")
@ViewScoped
public class PageNavigationBean implements Serializable 
{

	private static final long serialVersionUID = 1L;
	private String pageName;
	
	private CriteriaBean cb = (CriteriaBean)FacesUtils.getManagedBean("crit");
	

	@PostConstruct
	public void init() {
		System.out.println("PageNavigationBean init");
		pageName = NavigationConstants.HOME_NAVIGATION;
	}

	public String navHomePage() {
		System.out.println("PageNavigationBean defaultPage.xhtml");
//		(( AddPlannerBean )FacesUtils.getManagedBean( "addPlannerBean" ) ).searchCurrentCmts();
		pageName = NavigationConstants.HOME_NAVIGATION;
//		pageTitle = BaseerahConstants.Constants.PageTitles.HOME_PAGE;
		cb.setPageTitle(BaseerahConstants.Constants.PageTitles.HOME_PAGE);
		return pageName;
	}
	
	
	
	public String navLogOut() {
		System.out.println("PageNavigationBean logout.xhtml");
//		UserBean.KEY_CURRENT_USER = null;
//		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
//		pageName = NavigationConstants.LOGOUT_NAVIGATION;
		return pageName;
	}

	
	
	public String navSearchIndividualPage() 
	{
		System.out.println("PageNavigationBean search/searchData.xhtml");
		FacesUtils.resetManagedBean("searchBean");
		
		pageName = NavigationConstants.SEARCH_NAVIGATION;
		cb.setPageTitle(BaseerahConstants.Constants.PageTitles.SEARCH_INDL);
		return pageName;
	}
	
	public String navSearchNotificationPage() 
	{
		System.out.println("PageNavigationBean search/searchNotification.xhtml");
		FacesUtils.resetManagedBean("searchBean");
		
		pageName = NavigationConstants.SEARCH_NOTIFICATION;
		cb.setPageTitle(BaseerahConstants.Constants.PageTitles.SEARCH_NOTIFICATION);
		return pageName;
	}
	
	

	public String navAdminAddUsersPage() {
		System.out.println("PageNavigationBean admin/addUsers.xhtml");
		FacesUtils.resetManagedBean("adminBean");
		
		pageName = NavigationConstants.ADMIN_ADD_USERS_NAVIGATION;
		cb.setPageTitle(BaseerahConstants.Constants.PageTitles.ADMIN);
		return pageName;
	}
	
	public String navAdminManageUsersPage() {
		System.out.println("PageNavigationBean admin/manageUsers.xhtml");
		FacesUtils.resetManagedBean("adminBean");
		pageName = NavigationConstants.ADMIN_MANAGE_USERS_NAVIGATION;
		cb.setPageTitle(BaseerahConstants.Constants.PageTitles.ADMIN);
		return pageName;
	}
	
	public String navAdminManageUsersDetailsPage() {
		System.out.println("PageNavigationBean admin/manageUsersDetails.xhtml");
		pageName = NavigationConstants.ADMIN_MANAGE_USERS_DETAILS_NAVIGATION;
		cb.setPageTitle(BaseerahConstants.Constants.PageTitles.ADMIN);
		return pageName;
	}

	public String navAdminModalityAliasPage() {
		System.out.println("PageNavigationBean admin/modalityAlias.xhtml");
		FacesUtils.resetManagedBean("adminBean");
		pageName = NavigationConstants.ADMIN_MODALITY_ALIAS_NAVIGATION;
		cb.setPageTitle(BaseerahConstants.Constants.PageTitles.ADMIN);
		return pageName;
	}
	
	
	public String navAdminChangePassword() 
	{
		FacesUtils.resetManagedBean("adminBean");
		pageName = NavigationConstants.ADMIN_CHANGE_PASSWORD;
		cb.setPageTitle(BaseerahConstants.Constants.PageTitles.ADMIN);
		return pageName;
	}
	
	public String navAdminResetClient() 
	{
		FacesUtils.resetManagedBean("resetClientBean");
		pageName = NavigationConstants.ADMIN_RESET_CLIENT;
		cb.setPageTitle(BaseerahConstants.Constants.PageTitles.ADMIN);
		return pageName;
	}


	
	
	/**
	 * @return the pageName
	 */
	public String getPageName() {
		return pageName;
	}

	/**
	 * @param pageName
	 *            the pageName to set
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

}
