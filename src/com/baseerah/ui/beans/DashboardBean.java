package com.baseerah.ui.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;

import com.pacs.bll.DashboardBll;

import com.pacs.dal.dao.DashBoardTicketsCount;

@ManagedBean(name="dashboardBean")
@SessionScoped
public class DashboardBean 
{
	private DashBoardTicketsCount dtc;
	
	private DashBoardTicketsCount dtcOneWeekOld;
	
	private LineChartModel studiesPerDayChart;
	private LineChartModel dataGrowthPerDayChart;
	
	public DashboardBean() 
	{
		// TODO Auto-generated constructor stub
		this.dtc=new DashBoardTicketsCount();
		
		popultaeOneWeekOldValues();
		
		popultaeLatestValues();
		
		this.studiesPerDayChart = new LineChartModel();
		this.dataGrowthPerDayChart = new LineChartModel();
	}
	
	
	private void popultaeOneWeekOldValues()
	{
		System.out.println("In Populate One Week Old Values Method");
		DashboardBll bll = new DashboardBll();
		this.dtcOneWeekOld = bll.getOneWeekOldValues();
		if(this.dtcOneWeekOld!=null && this.dtcOneWeekOld.getId()!=null
				&& this.dtcOneWeekOld.getId()>0)
		{
			System.out.println("Old Ticket object retrieved, with ID :"+this.dtcOneWeekOld.getId());	
		}		
	}
	
	private void popultaeLatestValues()
	{
		System.out.println("In Populate Latest Values Method");
		DashboardBll bll = new DashboardBll();
		this.dtc = bll.getLatestValue();
		System.out.println("Latest Ticket object retrieved, with ID :"+this.dtc.getId());
		calculateDeltaValues();
		
		
	}
	
	
	
	
	private void calculateDeltaValues()
	{
		if(this.dtcOneWeekOld!=null && this.dtcOneWeekOld.getId()!=null
				&& this.dtcOneWeekOld.getId()>0)
		{
			Integer studyIncInWeek = (this.dtc.getStudyCount() - this.dtcOneWeekOld.getStudyCount() )/this.dtcOneWeekOld.getStudyCount()*100;
			this.dtc.setStudyIncInWeek(studyIncInWeek);
			
			Double storageIncInWeek = (this.dtc.getStorageGrowth() - this.dtcOneWeekOld.getStorageGrowth())/this.dtcOneWeekOld.getStorageGrowth()*100;
			this.dtc.setStorageIncInWeek(storageIncInWeek.intValue());
			
			Integer machinesIncInWeek = (this.dtc.getMachinesCount() - this.dtcOneWeekOld.getMachinesCount())/this.dtcOneWeekOld.getMachinesCount()*100;
			this.dtc.setMachinesIncInWeek(machinesIncInWeek);
		}
		else
		{
			this.dtc.setStudyIncInWeek(0);
			this.dtc.setStorageIncInWeek(0);
			this.dtc.setMachinesIncInWeek(0);
		}
	}
	
	
	
	@PostConstruct
	private void initHomePageCharts()
	{
		initStudiesPerDayChart();
		initDataGrowthPerDayChart();
		
		
	}
	private void initStudiesPerDayChart()
	{
		DashboardBll bll = new DashboardBll();
		System.out.println("In initStudiesPerDayChart PostConstruct");
		this.studiesPerDayChart = new LineChartModel();
		this.studiesPerDayChart = bll.populateStudiesPerDayChart(studiesPerDayChart);
//		studiesPerDayChart.setTitle("Studies per Day");
		studiesPerDayChart.setSeriesColors("C7754C");
		studiesPerDayChart.setShowPointLabels(true);
		studiesPerDayChart.setAnimate(true);		
		studiesPerDayChart.setExtender("extStudy");

		
//		X and Y Axis have been configured in BLL 
	
	}
	
	private void initDataGrowthPerDayChart()
	{
		DashboardBll bll = new DashboardBll();
		System.out.println("In initDataGrowthPerDayChart PostConstruct");
		this.dataGrowthPerDayChart = new LineChartModel();
		this.dataGrowthPerDayChart = bll.populateDataGrowthPerDayChart(dataGrowthPerDayChart);
//		dataGrowthPerDayChart.setTitle("Storage Growth per Day");
		dataGrowthPerDayChart.setShowPointLabels(true);
		dataGrowthPerDayChart.setSeriesColors("00749F");
		dataGrowthPerDayChart.setAnimate(true);
		dataGrowthPerDayChart.setExtender("extData");

		
//		X and Y Axis have been configured in BLL
	
	}
	
//	Getters and Setters
	public DashBoardTicketsCount getDtc() {
		return dtc;
	}

	public void setDtc(DashBoardTicketsCount dtc) {
		this.dtc = dtc;
	}

	public DashBoardTicketsCount getDtcOneWeekOld() {
		return dtcOneWeekOld;
	}

	public void setDtcOneWeekOld(DashBoardTicketsCount dtcOneWeekOld) {
		this.dtcOneWeekOld = dtcOneWeekOld;
	}


	public LineChartModel getStudiesPerDayChart() {
		return studiesPerDayChart;
	}


	public void setStudiesPerDayChart(LineChartModel studiesPerDayChart) {
		this.studiesPerDayChart = studiesPerDayChart;
	}


	public LineChartModel getDataGrowthPerDayChart() {
		return dataGrowthPerDayChart;
	}


	public void setDataGrowthPerDayChart(LineChartModel dataGrowthPerDayChart) {
		this.dataGrowthPerDayChart = dataGrowthPerDayChart;
	}

}
