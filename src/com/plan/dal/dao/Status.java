/**
 * 
 */
package com.plan.dal.dao;

/**
 * @author Analysis
 *
 */
public class Status {
    private boolean success;
    public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private String description;
     
    // other setter and getter
}
