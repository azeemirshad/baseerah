package com.pacs.utils;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class Environment 
{
	public Environment() 
	{
		
	}
	
	private static ResourceBundle resources;
	
	static
	{
		loadResources();
	}
	
	private static void loadResources()
	{
		resources = ResourceBundle.getBundle( "bundle" );
	}
	
	public static String getApplName()
	{
		return getProperty("applName");
	}
	
	public static String getBusinessClientName()
	{
		return getProperty("businessClientName");
	}
	
	
	public static String getDefaultPassword()
	{
		return getProperty("defaultPassword");
	}
	
	public static String getWeasisServerPath()
	{
		return getProperty("weasisServerPath");
	}
	
	
	
	public static String getFileMaxSize()
	{
		return getProperty("fileMaxSize");
	}
	
	public static String getSyncStatusOption()
	{
		return getProperty("syncStatusOption");
	}
	
	public static String getBarChartWidth()
	{
		return getProperty("barChartWidth");
	}
	
	
	public static String getProperty(String key) {
		String value = resources.getString(key);
		return value;
	}
	
	
	//only for testing
		private static void printResources( ResourceBundle resources )
		{
			Enumeration printEnum = resources.getKeys();
			while( printEnum.hasMoreElements() )
			{
				String key = printEnum.nextElement().toString();
				//Console.printlnPrompt( key + " : " + resources.getString(key) );
			}
		}

}
