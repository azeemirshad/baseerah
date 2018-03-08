package com.plan.bll.wf;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.iac.web.util.FacesUtils;
import com.plan.dal.dao.ApplicationUsers;
import com.plan.dal.dao.WfAttendedBy;
import com.plan.dal.dao.WfPlanner;
import com.plan.dal.dao.WfTrackReport;
import com.plan.ui.beans.UserBean;
import com.plan.ui.beans.admin.CriteriaBean;
import com.plan.utils.HibernateUtilsAnnot;
import com.plan.utils.MessageConstants;



public class AddPlannerBll 
{	
	private UserBean ub = ((UserBean)FacesUtils.getManagedBean("userBean"));
	private CriteriaBean cb = ((CriteriaBean)FacesUtils.getManagedBean("crit"));
	
	public AddPlannerBll() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public List<WfPlanner> getCurrentCmts()
	{
		Session session = null;
		List<WfPlanner> list = null;
		System.out.println("In getCurrentCmts Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();			
			Criteria cr = session.createCriteria(WfPlanner.class);
			
			cr.add(Restrictions.ge("eventTime", Calendar.getInstance().getTime()));
			cr.add(Restrictions.eq("isDelete", MessageConstants.Constants.NO_STRING));
			cr.addOrder(Order.asc("eventTime"));
			list = cr.list();
			for (WfPlanner wfPlanner : list) {
				Hibernate.initialize(wfPlanner.getAttendedBy());
				Hibernate.initialize(wfPlanner.getTrackReport());
			}
			
				
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return list;
	}
	
	
	public List<String> getAllSections()
	{
		Session session = null;
		List<String> list = null;
		System.out.println("In getCurrentCmts Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();			
			Criteria cr = session.createCriteria(ApplicationUsers.class);
			cr.setProjection(Projections.distinct(Projections.property("section")));
			cr.add(Restrictions.eq("profileStatus", MessageConstants.Constants.PROFILE_CURRENT));
			cr.add(Restrictions.ne("section","Display"));
			cr.addOrder(Order.asc("section"));
			list = cr.list();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		System.out.println("Sections List size "+ list.size());
		System.out.println( list);
		
		return list;
	}
	
	public List<String> getAllLocations()
	{
		Session session = null;
		List<String> list = null;
		System.out.println("In getAllLocations Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();			
			Criteria cr = session.createCriteria(WfPlanner.class);
			cr.setProjection(Projections.distinct(Projections.property("location")));
			cr.add(Restrictions.eq("isDelete", MessageConstants.Constants.NO_STRING));
			cr.addOrder(Order.asc("location"));
			list = cr.list();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return list;
	}
	
	public List<WfAttendedBy> getAllAttendedBys()
	{
		Session session = null;
		List<String> list = new ArrayList<String>();
		System.out.println("In getAllAttendedBys Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();			
			Criteria cr = session.createCriteria(WfAttendedBy.class);
			cr.setProjection(Projections.distinct(Projections.property("name")));
			cr.add(Restrictions.eq("isDelete", MessageConstants.Constants.NO_STRING));
			cr.addOrder(Order.asc("name"));
			list = cr.list();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		List<WfAttendedBy> alist= new ArrayList<WfAttendedBy>();
		WfAttendedBy  attendedBy = null;
		for (String attendedByName : list) {
			attendedBy = new WfAttendedBy();
			attendedBy.setIsDelete(MessageConstants.Constants.NO_STRING);
			attendedBy.setName(attendedByName);
			alist.add(attendedBy);
		}
		
		return alist;
	}
	
	public List<String> getAllChairedBys()
	{
		Session session = null;
		List<String> list = null;
		System.out.println("In getAllChairedBys Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();			
			Criteria cr = session.createCriteria(WfPlanner.class);
			cr.setProjection(Projections.distinct(Projections.property("chairedBy")));
			cr.add(Restrictions.eq("isDelete", MessageConstants.Constants.NO_STRING));
			cr.addOrder(Order.asc("chairedBy"));
			list = cr.list();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return list;
	}
	
	public List<String> getAllAppointments()
	{
		Session session = null;
		List<String> list = null;
		System.out.println("In getCurrentCmts Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();			
			Criteria cr = session.createCriteria(ApplicationUsers.class);
			cr.setProjection(Projections.distinct(Projections.property("appointment")));
			cr.add(Restrictions.eq("profileStatus", MessageConstants.Constants.PROFILE_CURRENT));
			cr.addOrder(Order.asc("appointment"));
			list = cr.list();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return list;
	}
	public boolean addPlan(WfPlanner toAdd) throws Exception
	{
		System.out.println("in add planner bll method");
		boolean flag = true;
		
		Session session = null;
		Transaction tx = null;
		
		try
		{
			session = HibernateUtilsAnnot.currentSession();
			tx = session.beginTransaction();
			ApplicationUsers currentUser = new ApplicationUsers(); 
			currentUser = ub.getCurrentUser();
//			Calendar sDate = Calendar.getInstance(); 
//			sDate.setTime(toAdd.getEventTime());
//			sDate.add(Calendar.MINUTE, -MessageConstants.Constants.MINIMUM_INTERVAL);
//			
//			Calendar eDate = Calendar.getInstance(); 
//			eDate.setTime(toAdd.getEventTime());
//			eDate.add(Calendar.MINUTE, MessageConstants.Constants.MINIMUM_INTERVAL);
//			
//			Criteria cr = session.createCriteria(WfPlanner.class);
//			cr.add(Restrictions.between("eventTime", sDate.getTime(), eDate.getTime()));
//			cr.setProjection(Projections.rowCount());
			
				if(toAdd.getId() == null || toAdd.getId()<1)
				{
//					Integer count =(Integer) cr.uniqueResult();
//					if(count == 0){
					
					toAdd.setInsertBy(currentUser);	
					toAdd.setUpdateBy(currentUser);	
					session.save(toAdd);
					session.flush();
					
					
	//				Adding track report
					saveTrackReport(MessageConstants.Constants.TrackActions.PLAN_SAVED, toAdd, session);
//					}else
//					{
//						throw new Exception("This time slot is not aval, please choose a different one.");
//					}
				}
				else if(toAdd.getId() != null && toAdd.getId()>=1)
				{
					
//					cr.add(Restrictions.ne("id", toAdd.getId()));
//					Integer count =(Integer) cr.uniqueResult();
//					if(count == 0){
	//				Adding track report
						//saveTrackReport(MessageConstants.Constants.TrackActions.PLAN_UPDATED, toAdd, session);
						List<WfAttendedBy> attendees  = session.createCriteria(WfAttendedBy.class).add(Restrictions.eq("plannerId.id", toAdd.getId())).list();
						for (WfAttendedBy object : attendees) {
							session.delete(object);
						}
//						WfTrackReport report = new WfTrackReport();
//						report.setOperator(ub.getCurrentUser());
//						report.setActivity(MessageConstants.Constants.TrackActions.PLAN_UPDATED);
//						report.setPlannerId(toAdd);
//						toAdd.getTrackReport().add(report);
						toAdd.setUpdateBy(currentUser);
						toAdd.setUpdateDate(new Date());
						session.merge(toAdd);
						saveTrackReport(MessageConstants.Constants.TrackActions.PLAN_UPDATED, toAdd, session);
//					}else
//					{
//						throw new Exception("This time slot is not aval, please choose a different one.");
//					}
				}
			
			
			
			
			tx.commit();
					
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			tx.rollback();
			flag = false;
			throw new Exception(e.getMessage());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			flag = false;
			throw new Exception(e.getMessage());
			
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		
		return flag;
	}
	
	
	
	public boolean deletePlan(WfPlanner toAdd) throws Exception
	{
		System.out.println("in delete planner bll method");
		boolean flag = true;
		
		Session session = null;
		Transaction tx = null;
		
		try
		{
			session = HibernateUtilsAnnot.currentSession();
			tx = session.beginTransaction();
			ApplicationUsers currentUser = new ApplicationUsers(); 
			currentUser = ub.getCurrentUser();
			toAdd.setUpdateBy(currentUser);
			toAdd.setUpdateDate(new Date());
			session.saveOrUpdate(toAdd);
			saveTrackReport(MessageConstants.Constants.TrackActions.PLAN_DELETED, toAdd, session);
			
			tx.commit();
					
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			tx.rollback();
			flag = false;
			throw new Exception(e.getMessage());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			flag = false;
			throw new Exception(e.getMessage());
			
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		
		return flag;
	}
	
	
	public void saveTrackReport(String activity, WfPlanner plannerId, Session session)throws HibernateException
	{
		WfTrackReport report = new WfTrackReport();
		report.setOperator(ub.getCurrentUser());
		report.setActivity(activity);
		report.setPlannerId(plannerId);
		
		session.save(report);
	}
	
	
	public List<WfPlanner> advSearchPlans(WfPlanner toSearchPlan, Date dateFrom , Date dateTo)
	{
		Session session = null;
		List<WfPlanner> list = new ArrayList<WfPlanner>();
		System.out.println("In search client Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();			
			Criteria cr = session.createCriteria(WfPlanner.class);
			if(toSearchPlan!=null)
			{
				if(toSearchPlan.getId()!=null && toSearchPlan.getId()>0)
				{
					cr.add(Restrictions.eq("id", toSearchPlan.getId()));
				}
				if(toSearchPlan.getChairedBy()!=null && toSearchPlan.getChairedBy().trim().length()>0)
				{
					cr.add(Restrictions.ilike("chairedBy", toSearchPlan.getChairedBy()));
				}
				if(toSearchPlan.getTopic()!=null && toSearchPlan.getTopic().trim().length()>0)
				{
					cr.add(Restrictions.ilike("topic", toSearchPlan.getTopic(), MatchMode.ANYWHERE));
				}
				if(toSearchPlan.getLocation()!=null && toSearchPlan.getLocation().trim().length()>0)
				{
					cr.add(Restrictions.eq("location", toSearchPlan.getLocation()));
				}
				cr.add(Restrictions.between("eventTime", dateFrom, dateTo));
			}
			cr.addOrder(Order.asc("eventTime"));
			list = cr.list();
			for(WfPlanner c:list)
			{
				Hibernate.initialize(c.getAttendedBy());
				
				Hibernate.initialize(c.getTrackReport());
				
			}
	
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
	
	

	
}
