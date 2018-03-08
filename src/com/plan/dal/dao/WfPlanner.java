package com.plan.dal.dao;

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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.plan.utils.DateUtils;
import com.plan.utils.MessageConstants;



@Entity
@Table (name = "wf_planner")
public class WfPlanner
{
	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="DATE_TIME")
	private Date eventTime;
	
	@Column(name ="TOPIC")
	private String topic;
	
	@Column(name ="LOCATION")
	private String location;
	
	@Column (name = "CHAIRED_BY")
	private String chairedBy;
	
	@ManyToOne (cascade=CascadeType.ALL)
	@JoinColumn (name = "insert_by")
	private ApplicationUsers insertBy;
	
	@ManyToOne 
	@JoinColumn (name = "update_by")
	private ApplicationUsers updateBy;
	
	@Column(name = "insert_date")
	private Date insertDate;
	
	@Column(name = "update_date")
	private Date updateDate;
	
	
	@OneToMany(mappedBy = "plannerId" , cascade = CascadeType.ALL)
	@OrderBy("activityTime DESC")
	private  List<WfTrackReport> trackReport;
	
	@OneToMany(mappedBy = "plannerId" , cascade = CascadeType.ALL)
	@OrderBy("id DESC")
	private  List<WfAttendedBy> attendedBy;
	
	
	@Column(name ="IS_DELETE")
	private String isDelete;
	
	public WfPlanner() 
	{
		this.insertDate = new Date();

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public ApplicationUsers getInsertBy() {
		return insertBy;
	}

	public void setInsertBy(ApplicationUsers insertBy) {
		this.insertBy = insertBy;
	}

	public ApplicationUsers getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(ApplicationUsers updateBy) {
		this.updateBy = updateBy;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
	public List<WfTrackReport> getTrackReport() {
		return trackReport;
	}

	public void setTrackReport(List<WfTrackReport> trackReport) {
		this.trackReport = trackReport;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getChairedBy() {
		return chairedBy;
	}

	public void setChairedBy(String chairedBy) {
		this.chairedBy = chairedBy;
	}

	public List<WfAttendedBy> getAttendedBy() {
		return attendedBy;
	}

	public void setAttendedBy(List<WfAttendedBy> attendedBy) {
		this.attendedBy = attendedBy;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getAttendedByText() {
		if(this.attendedBy.size()>0){
			return attendedBy.get(0).getName()+"  ...";
		}else
			return "";
	}
	
	public String getAttendedByDetail() {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		for (WfAttendedBy attendee : this.getAttendedBy()) {
			if(i>0)
				builder.append(", ");
			builder.append(attendee.getName() );
			i++;
		}
		return builder.toString();
	}
	
	
	public int getStyle(){
		if(DateUtils.isWithinMinutesFuture(this.eventTime, MessageConstants.Constants.BLINK_TIME))
			return 1;
		if( DateUtils.isToday(this.eventTime))
			return 2;
		else if( DateUtils.isWithinDaysFuture(this.eventTime,1))
			return 3;
		else 
			return 4;
	}
	
}
