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

import javax.faces.model.SelectItem;

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
import com.baseerah.dal.dao.Event;
import com.baseerah.dal.dao.Institute;
import com.baseerah.dal.dao.KeyValuePair;
import com.baseerah.dal.dao.NotificationWFObject;
import com.baseerah.dal.dao.SourceType;
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
	
	public List<UserProfile> searchUserProfileList(UserProfile toSearch) throws Exception
	{
		Session session = null;
		List<UserProfile> list = new ArrayList<UserProfile>();
		System.out.println("In search client Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();			
			Criteria cr = session.createCriteria(UserProfile.class);
//			cr.createAlias("userEvents", "uEvents");
			
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
			for (UserProfile userProfile : list) {
				Hibernate.initialize(userProfile.getUserEvents());
			}
			

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return list;
	}
	
	public List<UserProfile> searchUserProfileList(UserProfile toSearch, String searchEventTitle, Integer searchEventTypeId) throws Exception
	{
		Session session = null;
		List<UserProfile> list = new ArrayList<UserProfile>();
		System.out.println("In searchUserProfileList bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();			
			Criteria cr = session.createCriteria(UserProfile.class);
//			cr.createAlias("userEvents", "uEvents");
			
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
				if(searchEventTitle !=null && searchEventTitle.trim().length()>0 
						|| searchEventTypeId!= null && searchEventTypeId.intValue() != -1)
				{
					
					cr.createAlias("userEvents", "uEvents");
					cr.createAlias("uEvents.event", "eve");
					cr.createAlias("eve.eventType", "eveT");
					if(searchEventTitle !=null && searchEventTitle.trim().length()>0 ){
						cr.add(Restrictions.ilike("eve.title", searchEventTitle,MatchMode.ANYWHERE));
					}
					if( searchEventTypeId!= null && searchEventTypeId.intValue() != -1){
						cr.add(Restrictions.eq("eveT.id", searchEventTypeId));
					}
				}
				
//				cr.add(Restrictions.between("eventTime", dateFrom, dateTo));
			}
//			cr.addOrder(Order.asc("eventTime"));
			list = cr.list();
			for (UserProfile userProfile : list) {
				Hibernate.initialize(userProfile.getUserEvents());
			}
			

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return list;
	}
	
	public List<NotificationWFObject> searchNotificationList() throws Exception
	{
		Session session = null;
		List<BaseerahNotification> list = new ArrayList<BaseerahNotification>();
		System.out.println("In search client Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();			
			Criteria cr = session.createCriteria(BaseerahNotification.class);
			cr.addOrder(Order.desc("deliveryDate"));
			cr.add(Restrictions.eq("isDelete", BaseerahConstants.DeleteStatus.No));
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
		List<NotificationWFObject> wflist = new ArrayList<NotificationWFObject>();
		NotificationWFObject wfObject = null;
		for (BaseerahNotification baseerahNotification : list) {
			wfObject = new NotificationWFObject(baseerahNotification);
			System.out.println("Notification " + wfObject.getBasNotif().getId() +
					":: " + wfObject.getFcmNotification().getTo());
			wflist.add(wfObject);
		}
		
		
		return wflist;
	}
	
	
	public boolean deleteNotification(BaseerahNotification selectedNotification) throws Exception
	{
		Session session = null;
		Transaction tx = null;
		System.out.println("In deleteNotification Method bll");
		try
		{
			
			
			session = HibernateUtilsAnnot.currentSession();
			tx = session.beginTransaction();
			
			selectedNotification.setIsDelete(BaseerahConstants.DeleteStatus.Yes);
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
	
//	private void parseData(Cursor cursor, OfflineDataBaseHandler offlineDataBaseHandler ){        
//        OfflineCartRequestData offlineCartRequestData = new OfflineCartRequestData();
//        List<AddToCartRequest> cartList = new ArrayList<AddToCartRequest>();
//        try {
//            // use for converting Dynomic TYPE for JSON Object.
//            Gson gson = new Gson();
//            Type type = new TypeToken<Map<String, String>>(){}.getType();
//            cursor.moveToFirst();
//            do{
//                AddToCartRequest cartRequest = new AddToCartRequest();
//                ProductData request = new ProductData();
//                request.setAmount(cursor.getInt(cursor.getColumnIndex("amount"))+"");
//                request.setProductId(cursor.getString(cursor.getColumnIndex("productId")));
//                request.setExtra((Map<String, String>) gson.fromJson(cursor.getString(cursor.getColumnIndex("extra")), type));
//                request.setProductOptions((Map<String, String>) gson.fromJson(cursor.getString(cursor.getColumnIndex("productOption")), type));
//                cartRequest.setProductData(request);
//                cartList.add(cartRequest);
//            }while(cursor.moveToNext());
// 
//            offlineCartRequestData.setOfflineCArtData(cartList);
//            offlineCartRequestData.setUrserId(AppSharedPref.getCustomerId(Cart.this));
//            addToOfflineCart(offlineDataBaseHandler, offlineCartRequestData);
//        }catch (SQLException e){
//            cursor.close();
//            offlineDataBaseHandler.close();
//            e.printStackTrace();
//        }
//    }
	
	
	public boolean addUserProfile(UserProfile notification) throws Exception
	{
		Session session = null;
		Transaction tx = null;
		System.out.println("In addUserProfile Method bll");
		if(notification.getSourceType().getId() == null){
			System.out.println("getSourceType().getId() is null");
			notification.setSourceType(null);
		}
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
	
	public boolean validateMobileNo(String mobileNo) throws Exception
	{
		Session session = null;
		Transaction tx = null;
		System.out.println("In validateMobileNo Method bll");
		List<UserProfile> users = new ArrayList<>();
		try
		{
			session = HibernateUtilsAnnot.currentSession();
			tx = session.beginTransaction();
			
			Criteria crit = session.createCriteria(UserProfile.class);
			crit.add(Restrictions.eq("phone", mobileNo));
			users = crit.list();
			
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
		 if(users.size()>0){
			 return false;
		 }
		
		return true;
	}
	
	public boolean addSourceType(SourceType notification) throws Exception
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
	
	public List<SelectItem> searchSourceTypeItems() throws Exception
	{
		List<SourceType> sourceTypes = searchSourceTypes();
		List<SelectItem> sourceTypeItems = new ArrayList<>();
		for (SourceType institute : sourceTypes) {
			sourceTypeItems.add(new SelectItem(institute.getId(), institute.getName()));
		}
		return sourceTypeItems;
	}
	
	
	public List<SourceType> searchSourceTypes() throws Exception
	{
		Session session = null;
		List<SourceType> list = new ArrayList<SourceType>();
		System.out.println("In search Institutes Method bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();			
			Criteria cr = session.createCriteria(SourceType.class);
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
	
	public boolean sendNotification(NotificationWFObject notificationWf) throws Exception{
		try{
			Gson gson = new Gson();
			
			System.out.println("Notification KeyValuePair list size " + notificationWf.getFcmNotification().getData().size()); 
			Map<String, Object> subMap = new LinkedHashMap<String, Object>();
			for (KeyValuePair pair : notificationWf.getFcmNotification().getData()) {
				System.out.println("Notification KeyValuePair " +  pair.getKey()+ " : " + pair.getValue());
				if(!pair.getKey().trim().equals("") && !pair.getValue().trim().equals(""))
				subMap.put(pair.getKey(), pair.getValue());
			}
			notificationWf.getFcmNotification().setData(null);
			JsonElement jsonElement = gson.toJsonTree(notificationWf.getFcmNotification());
			System.out.println("Notification " + notificationWf.getBasNotif().getNotificationJson());
//			   Map<String, Object> subMap = new LinkedHashMap<String, Object>(); 
//		       subMap.put("url", "http://baseerah.net/en/events/lecture-iqbals-concept-of-khudi"); 
//			   
		    JsonElement jsonElementData = gson.toJsonTree(subMap);
		    System.out.println("jsonElementData .... \n" + jsonElementData.toString());
			jsonElement.getAsJsonObject().add("data",jsonElementData);
			   
			notificationWf.getBasNotif().setNotificationJson( gson.toJson(jsonElement));
			System.out.println("Final Notification " + notificationWf.getBasNotif().getNotificationJson());
			notificationWf.getBasNotif().setStatus(BaseerahConstants.NotificationStatus.PENDING);
			if(addNotification(notificationWf.getBasNotif())){
				System.out.println("Notification added to database");
				if(pushFCMNotification(notificationWf)){
					notificationWf.getBasNotif().setStatus(BaseerahConstants.NotificationStatus.SENT);
					updateNotification(notificationWf.getBasNotif());
					return true;
				}
			
			}
		}catch(Exception ex){
			System.out.println("Exception in  bll.... sendNotification &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			throw new Exception(ex.getMessage());
		}
		return false;
	}
	
	public boolean resendNotification(NotificationWFObject notificationWf) throws Exception{
		try{
			Gson gson = new Gson();
			
			System.out.println("Resend Notification " + notificationWf.getBasNotif().getNotificationJson());
				if(pushFCMNotification(notificationWf)){
					notificationWf.getBasNotif().setStatus(BaseerahConstants.NotificationStatus.SENT);
					updateNotification(notificationWf.getBasNotif());
					return true;
				}
			

		}catch(Exception ex){
			System.out.println("Exception in  bll.... sendNotification &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			throw new Exception(ex.getMessage());
		}
		return false;
	}
	
	
	public boolean pushFCMNotification(NotificationWFObject notificationWf) throws Exception {

		   String authKey = Environment.getProperty("serverKey"); // You FCM AUTH key
		   String FMCurl = Environment.getProperty("fcmUrl");
		   try{
		   URL url = new URL(FMCurl);
		   HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		   conn.setUseCaches(false);
		   conn.setDoInput(true);
		   conn.setDoOutput(true);

		   conn.setRequestMethod("POST");
		   conn.setRequestProperty("Authorization","key="+authKey);
		   conn.setRequestProperty("Content-Type","application/json");

		   
//		   FcmNotification fcmNotification = new FcmNotification();
//		   fcmNotification.to = userDeviceIdKey.trim();
//		   fcmNotification.notification.title = "Baseerah";
//		   fcmNotification.notification.body = "Hello Test notification";
		   
		   System.out.println("Notification " + notificationWf.getBasNotif().getNotificationJson());
		   
		   OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		   wr.write(notificationWf.getBasNotif().getNotificationJson());
		   wr.flush();
		   wr.close();
	       BufferedReader br = new BufferedReader(new InputStreamReader(
	               (conn.getInputStream())));

	       int result = conn.getResponseCode();
	       System.out.println("Output from Server .... \n");
	       String output;
	       while ((output = br.readLine()) != null) {
	           System.out.println(output);
	       }
	       if(result == 200){
	    	   return true;
	       }else{
	    	   return false;
	       }
		   }catch(Exception ex){
			   System.out.println("Exception in  .... pushFCMNotification &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			   ex.printStackTrace();
			   throw new Exception(ex.getMessage());
		   }
	       
	       
	}

	
	public boolean addNotification(BaseerahNotification notification) throws Exception
	{
		Session session = null;
		Transaction tx = null;
		System.out.println("In addNotification Method bll");
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
