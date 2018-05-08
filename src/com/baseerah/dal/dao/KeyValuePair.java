/**
 * 
 */
package com.baseerah.dal.dao;

/**
 * @author Azeem Irshad
 *
 */
public class KeyValuePair {
	private String key;
	private String value;
	
	public KeyValuePair(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
