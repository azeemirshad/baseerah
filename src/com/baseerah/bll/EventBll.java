package com.baseerah.bll;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.baseerah.utils.HibernateUtilsAnnot;
import com.baseerah.utils.MessageUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.iac.util.StringUtils;
import com.iac.web.util.FacesUtils;
import com.pacs.dal.dao.ApplicationUsers;
import com.sun.xml.internal.fastinfoset.algorithm.BuiltInEncodingAlgorithm.WordListener;

public class EventBll 
{
	UserBean ub = (UserBean)FacesUtils.getManagedBean("userBean");
	private CriteriaBean crit = (CriteriaBean)FacesUtils.getManagedBean("crit");
	
	public EventBll() 
	{
		// TODO Auto-generated constructor stub
	
	}

	
	
	
	public List<Event> searchEventList() throws Exception
	{
		Session session = null;
		List<Event> list = new ArrayList<Event>();
		System.out.println("In search client Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();			
			Criteria cr = session.createCriteria(Event.class);
			cr.addOrder(Order.desc("id"));
			cr.add(Restrictions.eq("isDelete", BaseerahConstants.DeleteStatus.No));
			list = cr.list();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e);
//			return null;
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return list;
	}
	
	public List<Institute> searchInstitutes() throws Exception
	{
		Session session = null;
		List<Institute> list = new ArrayList<Institute>();
		System.out.println("In search Institutes Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();			
			Criteria cr = session.createCriteria(Institute.class);
			cr.addOrder(Order.asc("name"));
//			cr.add(Restrictions.eq("isDelete", BaseerahConstants.DeleteStatus.No));
			list = cr.list();
			
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw new Exception(e);
//			return null;
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return list;
	}
	
	public List<EventType> searchEventTypes() throws Exception
	{
		Session session = null;
		List<EventType> list = new ArrayList<EventType>();
		System.out.println("In searchEventTypes Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();			
			Criteria cr = session.createCriteria(EventType.class);
			cr.addOrder(Order.asc("eventType"));
//			cr.add(Restrictions.eq("isDelete", BaseerahConstants.DeleteStatus.No));
			list = cr.list();
			
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw new Exception(e);
//			return null;
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return list;
	}
	
	public List<Campus> searchCampuses(Institute institute) throws Exception
	{
		Session session = null;
		List<Campus> list = new ArrayList<Campus>();
		System.out.println("In search Institutes Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();			
			Criteria cr = session.createCriteria(Campus.class);
			cr.addOrder(Order.asc("name"));
			cr.add(Restrictions.eq("institute.id", institute.getId()));
			list = cr.list();
			
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw new Exception(e);
//			return null;
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return list;
	}
	
	
	
	public boolean deleteEvent(Event event) throws Exception
	{
		Session session = null;
		Transaction tx = null;
		System.out.println("In deleteEvent Method bll");
		try
		{
			
			
			session = HibernateUtilsAnnot.currentSession();
			tx = session.beginTransaction();
			
			event.setIsDelete(BaseerahConstants.DeleteStatus.Yes);
			session.update(event);
			tx.commit();
			
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			tx.rollback();
			throw new Exception(e);
//			return false;
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return true;
	}
	
	public boolean updateNotification(BaseerahNotification selectedNotification) throws Exception
	{
		Session session = null;
		Transaction tx = null;
		System.out.println("In updateNotification Method bll");
		try
		{
			
			
			session = HibernateUtilsAnnot.currentSession();
			tx = session.beginTransaction();
			session.update(selectedNotification);
			tx.commit();
			
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			tx.rollback();
			throw new Exception(e);
//			return false;
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return true;
	}
	
	public boolean addInstitute(Institute notification) throws Exception
	{
		Session session = null;
		Transaction tx = null;
		System.out.println("In addInstitute Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();
			tx = session.beginTransaction();
			session.save(notification);
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			throw new Exception(e);
//			return false;
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return true;
	}
	
	public boolean addEventType(EventType notification) throws Exception
	{
		Session session = null;
		Transaction tx = null;
		System.out.println("In addEventType Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();
			tx = session.beginTransaction();
			session.save(notification);
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			throw new Exception(e);
//			return false;
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return true;
	}
	
	public boolean addCampus(Campus campus) throws Exception
	{
		Session session = null;
		Transaction tx = null;
		System.out.println("In addCampus Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();
			tx = session.beginTransaction();
			session.save(campus);
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			throw new Exception(e);
//			return false;
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return true;
	}
	
		
	public boolean addEvent(Event notification) throws Exception
	{
		Session session = null;
		Transaction tx = null;
		System.out.println("In addEvent Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(notification);
			
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			throw new Exception(e);
//			return false;
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return true;
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
