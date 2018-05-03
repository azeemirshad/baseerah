package com.pacs.bll.search;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.baseerah.dal.dao.UserProfile;
import com.baseerah.ui.beans.UserBean;
import com.baseerah.ui.beans.admin.CriteriaBean;
import com.iac.util.StringUtils;
import com.iac.web.util.FacesUtils;

import com.pacs.utils.HibernateUtilsAnnot;
import com.pacs.utils.MessageConstants;
import com.pacs.utils.MessageUtils;
import com.sun.xml.internal.fastinfoset.algorithm.BuiltInEncodingAlgorithm.WordListener;

public class SearchBll 
{
	UserBean ub = (UserBean)FacesUtils.getManagedBean("userBean");
	private CriteriaBean crit = (CriteriaBean)FacesUtils.getManagedBean("crit");
	
	public SearchBll() 
	{
		// TODO Auto-generated constructor stub
		init();
	}

	private void init()
	{
		
	}
	
	public List<UserProfile> searchUserProfileList(UserProfile toSearch)
	{
		Session session = null;
		List<UserProfile> list = new ArrayList<UserProfile>();
		System.out.println("In search client Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();			
			Criteria cr = session.createCriteria(UserProfile.class);
			if(toSearch!=null)
			{
				if(toSearch.getId()!=null && toSearch.getId()>0)
				{
					cr.add(Restrictions.eq("id", toSearch.getId()));
				}
				if(toSearch.getPhone()!=null && toSearch.getPhone().trim().length()>0)
				{
					cr.add(Restrictions.ilike("phone", toSearch.getPhone(), MatchMode.ANYWHERE));
				}
				if(toSearch.getName()!=null && toSearch.getName().trim().length()>0)
				{
					cr.add(Restrictions.ilike("name", toSearch.getName(),MatchMode.ANYWHERE));
				}
				
//				cr.add(Restrictions.between("eventTime", dateFrom, dateTo));
			}
//			cr.addOrder(Order.asc("eventTime"));
			list = cr.list();
			
			

			
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return list;
	}
	
	
	
	
	public boolean updateUserProfile(UserProfile userProfile) throws Exception
	{
		Session session = null;
		Transaction tx = null;
		System.out.println("In updateUserProfile Method bll");
		try
		{
			
			
			session = HibernateUtilsAnnot.currentSession();
			tx = session.beginTransaction();
			
			
			session.update(userProfile);
			
			
			tx.commit();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			throw new Exception(e);
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return true;
	}
	
	
}
