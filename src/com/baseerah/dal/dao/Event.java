/**
 * 
 */
package com.baseerah.dal.dao;

/**
 * @author Analysis
 *
 */

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "event")
public class Event 
{
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne 
	@JoinColumn (name = "institute_id")
	private Institute institute;
	
	@ManyToOne 
	@JoinColumn (name = "event_type_id")
	private EventType eventType;
	
	@ManyToOne 
	@JoinColumn (name = "campus_id")
	private Campus campus;
	
	@Column(name="title")
	private String title;
	
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="from_date")
	private Date fromDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="to_date")
	private Date toDate;

	
	@Column(name="is_delete")
	private Integer isDelete;
	
	
	public Event() 
	{
		// TODO Auto-generated constructor stub
		institute = new Institute();
		campus = new Campus();
		eventType = new EventType();
		isDelete = 0;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the institute
	 */
	public Institute getInstitute() {
		return institute;
	}

	/**
	 * @param institute the institute to set
	 */
	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	/**
	 * @return the campus
	 */
	public Campus getCampus() {
		return campus;
	}

	/**
	 * @param campus the campus to set
	 */
	public void setCampus(Campus campus) {
		this.campus = campus;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the isDelete
	 */
	public Integer getIsDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the eventType
	 */
	public EventType getEventType() {
		return eventType;
	}

	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}


}
