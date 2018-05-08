/**
 * 
 */
package com.baseerah.dal.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * @author Azeem Irshad
 *
 */
public class NotificationWFObject {
	private BaseerahNotification basNotif;
	
	private FcmNotification fcmNotification;


	
	public NotificationWFObject() {
		basNotif = new BaseerahNotification();
		fcmNotification = new FcmNotification();
	}
	
	public NotificationWFObject(BaseerahNotification baseerahNotification) {
		basNotif = baseerahNotification;
		Gson gson = new Gson();
		 JsonElement jelement = new JsonParser().parse(this.basNotif.getNotificationJson());
		 JsonObject jobject =  jelement.getAsJsonObject();
		 System.out.println("Processing Notification ..................." + this.basNotif.getId());
		 fcmNotification = new FcmNotification();
		 fcmNotification.setTo(jobject.get("to").getAsString());
		 JsonObject jobject1 = jobject.get("notification").getAsJsonObject();
		 fcmNotification.getNotification().setTitle(jobject1.get("title").getAsString());
		 fcmNotification.getNotification().setBody(jobject1.get("body").getAsString());
		 JsonElement dataElm = jobject.get("data");//.getAsJsonObject();
		 if(dataElm!=null){
			 java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<Map<String, String>>(){}.getType();
			 
			 System.out.println("Data element..........." + dataElm.toString());
			 
			 Map<String,String> myMap = (Map<String, String>) gson.fromJson(dataElm, type);
			 
			 for (Map.Entry<String,String> elm : myMap.entrySet()) {
				 System.out.println("Notification KeyValuePair " +  elm.getKey()+ " : " + elm.getValue());
					
					fcmNotification.getData().add(new KeyValuePair(elm.getKey(), elm.getValue()));
			}
		}
		 
		 System.out.println("To..........." + fcmNotification.getTo());
		 System.out.println("Title........" + fcmNotification.getNotification().getTitle());
		 System.out.println("Body........." + fcmNotification.getNotification().getBody());		 
//		fcmNotification = gson.fromJson(this.basNotif.getNotificationJson(), FcmNotification.class);
//		fcmNotification = getFcmNotification();
	}



		/**
		 * @return the fcmNotification
		 */
		public FcmNotification getFcmNotification() {
			
			
			return fcmNotification;
		}



		/**
		 * @return the basNotif
		 */
		public BaseerahNotification getBasNotif() {
			return basNotif;
		}



		/**
		 * @param basNotif the basNotif to set
		 */
		public void setBasNotif(BaseerahNotification basNotif) {
			this.basNotif = basNotif;
		}



		/**
		 * @param fcmNotification the fcmNotification to set
		 */
		public void setFcmNotification(FcmNotification fcmNotification) {
			this.fcmNotification = fcmNotification;
		}



}
