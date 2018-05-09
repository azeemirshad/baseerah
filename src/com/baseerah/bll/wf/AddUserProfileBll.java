package com.baseerah.bll.wf;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.baseerah.dal.dao.UserProfile;
import com.baseerah.utils.HibernateUtilsAnnot;




public class AddUserProfileBll 
{	
	
	
	public AddUserProfileBll() 
	{
		// TODO Auto-generated constructor stub
	}
	
		
		
		
		
	
	public boolean addMobileUserProfile(UserProfile toAdd) throws Exception
	{
		System.out.println("in addUserProfile bll method " + toAdd.toString());
		boolean flag = true;
		
		Session session = null;
		Transaction tx = null;
		List<UserProfile> profiles = null;
		try
		{
			session = HibernateUtilsAnnot.currentSession();
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(UserProfile.class)
							.add(Restrictions.eq("phone", toAdd.getPhone()));
			profiles = crit.list();
			
			
			if(profiles.size() == 0) 
			{
				session.save(toAdd);
//				session.flush();
			}
			else 

				System.out.println("Contact mobile no already exists.");
			
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
	
	
	

	
}
