package com.pacs.dal.dao;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name = "dashboard_tickets_count")
public class DashBoardTicketsCount 
{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name="study_count")
	private Integer studyCount;
	
	@Column(name="study_growth")
	private Integer studyGrowth;
	
	@Column(name="storage_size")
	private Double storageSize;
	
	@Transient
	private String storageSizeMb;
	@Transient
	private String storageSizeGb;
	
//	@Transient
//	private Double storageSizeMb;
//	@Transient
//	private Double storageSizeGb;
	
	@Column(name="storage_growth")
	private Double storageGrowth;
	
	
	@Column(name="machines_count")
	private Integer machinesCount;
	
	@Column(name="date_insert")
	private Date dateInsert;
	
	@Transient
	private Integer studyIncInWeek;
	@Transient
	private Integer storageIncInWeek;
	@Transient
	private Integer machinesIncInWeek;
	
	
	public DashBoardTicketsCount() 
	{
		// TODO Auto-generated constructor stub
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getStudyCount() {
		return studyCount;
	}


	public void setStudyCount(Integer studyCount) {
		this.studyCount = studyCount;
	}


	public Integer getStudyGrowth() {
		return studyGrowth;
	}


	public void setStudyGrowth(Integer studyGrowth) {
		this.studyGrowth = studyGrowth;
	}


	public Double getStorageSize() {
		return storageSize;
	}


	public void setStorageSize(Double storageSize) {
		this.storageSize = storageSize;
	}


	public Double getStorageGrowth() {
		return storageGrowth;
	}


	public void setStorageGrowth(Double storageGrowth) {
		this.storageGrowth = storageGrowth;
	}


	public Integer getMachinesCount() {
		return machinesCount;
	}


	public void setMachinesCount(Integer machinesCount) {
		this.machinesCount = machinesCount;
	}


	public Date getDateInsert() {
		return dateInsert;
	}


	public void setDateInsert(Date dateInsert) {
		this.dateInsert = dateInsert;
	}


	public String getStorageSizeMb() 
	{
		System.out.println("Storage size : " + storageSize);
		if(storageSize!=null && storageSize>0)
		{
			NumberFormat nf = NumberFormat.getInstance();			
			nf.setMaximumFractionDigits(2);
			DecimalFormat df = new DecimalFormat("###.##");
//			this.storageSizeMb=Double.valueOf((nf.format((storageSize/1024.00))));
//			this.storageSizeMb = Double.valueOf((storageSize/1024.00));
//			this.storageSizeMb = Double.valueOf((storageSize/1024.00));
//			df.format(storageSize/1024.00);
			this.storageSizeMb=nf.format((storageSize/1024.00));
		}
		else
		{
			this.storageSizeMb="0.0";
		}
		return storageSizeMb;
	}


	public void setStorageSizeMb(String storageSizeMb) {
		this.storageSizeMb = storageSizeMb;
	}


	public String getStorageSizeGb() 
	{
		if(storageSize!=null && storageSize>0)
		{
			NumberFormat nf = NumberFormat.getInstance();			
			nf.setMaximumFractionDigits(2);
//			this.storageSizeGb= Double.valueOf((nf.format((storageSize/1024.00/1024.00))));
			this.storageSizeGb= nf.format((storageSize/1024.00/1024.00));
			
		}
		else
		{
			this.storageSizeGb="0.0";
		}
		return storageSizeGb;
	}


	public void setStorageSizeGb(String storageSizeGb) {
		this.storageSizeGb = storageSizeGb;
	}


	public Integer getStudyIncInWeek() {
		return studyIncInWeek;
	}


	public void setStudyIncInWeek(Integer studyIncInWeek) {
		this.studyIncInWeek = studyIncInWeek;
	}


	public Integer getStorageIncInWeek() {
		return storageIncInWeek;
	}


	public void setStorageIncInWeek(Integer storageIncInWeek) {
		this.storageIncInWeek = storageIncInWeek;
	}


	public Integer getMachinesIncInWeek() {
		return machinesIncInWeek;
	}


	public void setMachinesIncInWeek(Integer machinesIncInWeek) {
		this.machinesIncInWeek = machinesIncInWeek;
	}
	

}
