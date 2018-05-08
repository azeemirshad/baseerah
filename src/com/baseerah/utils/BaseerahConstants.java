package com.baseerah.utils;


public final class BaseerahConstants {

	public static final class Messages {
		public static final String INVALID_VALUE = "Invalid Value";
		public static final String INVALID_USERNAME = "Invalid Username";
		public static final String INVALID_PASSWORD = "Invalid Password";
		public static final String SAVE_SUCCESS = "Data Saved Successfully";
		public static final String SAVE_FAILURE = "Data Could not be saved";
		public static final String VALUE_REQUIRED = "Value is required";
		public static final String MANDATORY_REQUIRED = "Mandatory fields are required";
		public static final String INVALID_DATE = "Date From should be less than Date To";
		public static final String UPDATE_SUCCESS = "Data has been updated successfully";
		public static final String UPDATE_FAILURE = "Data could not be updated";
		public static final String DELETE_SUCCESS = "Data has been deleted Successfully";
		public static final String DELETE_FAILURE = "Data could not be deleted";
		
		public static final String REQUIRED_CRITERIA = "You did not specify any search criteria";
		
	}
	public static final class DeleteStatus{
		public static final int Yes = 1;
		public static final int No = 0;
	}
	public static final class NotificationStatus{
		public static final String PENDING = "Pending";
		public static final String SENT = "Sent";
	}
	public static final class Constants 
	{
		public static final String TRUE_STRING = "true";
		public static final String FALSE_STRING = "false";
		public static final String ALL_STRING = "All";
		public static final String NONE_STRING = "None";
		
		public static final String DISPLAY_UNIT = "Display";
		public static final String PASSWORD_KEY = "1";
		public static final String PROFILE_CURRENT = "Current";
		public static final String SELECT_ONE_STRING = "Select One";
		public static final String NA_STRING = "NA";
		public static final String YES_STRING = "Yes";
		public static final String YES_PERM_STRING = "Yes_Permanent";
		public static final String NO_STRING = "No";
		public static final Integer AUTO_COMPLETE_SIZE = new Integer(30);		
		public static final Integer ID_OFFSET = new Integer(12345);
		
		public static final String NIL_STRING = "Nil";
		public static final String TRACE_STRING = "Trace";
		public static final String PRESENT_STRING = "Present";
		
		public static final String NEGATIVE_STRING = "Negative";
		public static final String POSITIVE_STRING = "Positive";
		public static final String WEAK_REACTIVE_STRING = "Weak Reactive";
		
		public static final String SEEN_STRING = "Seen";
		public static final String NOT_SEEN_STRING = "Not Seen";
		
		public static final String VERIFIED_STRING = "Verified";
		
		public static final Integer MINIMUM_INTERVAL = Integer.valueOf(Environment.getProperty("minimumInterval"));
		
		public static final Integer BLINK_TIME = Integer.valueOf(Environment.getProperty("blinkTime"));

		
		
		

		
		public static final class PageTitles 
		{			
			public static final String HOME_PAGE = "RIC PACS Solution - WELCOME";
			public static final String ADD_PLAN = "Add New IHD / CMT";
			public static final String UPDATE_PLAN = "Update Existing IHD/CMT";
			public static final String UPLOAD_SCANNED = "Upload Scanned Files";
			
			public static final String RESET_REPEATER = "Reset Repeater Clients";
			
			public static final String CASH_PAYMENT = "Cash and Accounts";
			
			public static final String GPE_XRAY = "G.P.E";
			
			public static final String RADIOLOGY = "Radiology";
			
			public static final String SAMPLE_COLLECTION = "Sample Collection";
			public static final String LAB_MGMT = "Lab Mgmt.";
			
			public static final String PATHOLOGY = "Pathology";
			
			public static final String DIRECTOR = "Director's Declaration";
			
			public static final String PRINT_REPORT = "Print Report";
			
			public static final String SEARCH_INDL = "Search Individual";
			
			public static final String SEARCH_NOTIFICATION = "Search Notification";
			
			public static final String RECYCLE_BIN = "Recycle Bin";
			
			public static final String ADMIN = "Admin Tasks";
			
			public static final String DASHBOARD = "Dash Board";
			
			
		}
		
		
	}

}
