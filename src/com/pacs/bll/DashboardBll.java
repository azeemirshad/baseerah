package com.pacs.bll;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.pacs.dal.dao.DashBoardTicketsCount;
import com.pacs.utils.HibernateUtilsAnnot;

public class DashboardBll 
{
	
	public DashboardBll() 
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public DashBoardTicketsCount getLatestValue()
	{
		Session session = null;
		DashBoardTicketsCount dtc = new DashBoardTicketsCount();
		System.out.println("In getLatestValue bll");
		try
		{
			session = HibernateUtilsAnnot.currentSession();
			
			DetachedCriteria maxQuery = DetachedCriteria.forClass( DashBoardTicketsCount.class );
			maxQuery.setProjection( Projections.max( "dateInsert" ) );

			Criteria cr = session.createCriteria( DashBoardTicketsCount.class );
			cr.add( Property.forName( "dateInsert" ).eq( maxQuery ) );
			
			
			List<DashBoardTicketsCount> list = cr.list();
			if(list.size()>0)
			{
				dtc = list.get(0);
			}
			
			
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return dtc;
	}
	
	public DashBoardTicketsCount getOneWeekOldValues()
	{
		Session session = null;
		DashBoardTicketsCount dtc = new DashBoardTicketsCount();
		System.out.println("In onewekold bll");
		Calendar cal = new GregorianCalendar();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		cal.add(Calendar.DAY_OF_MONTH, -7);
		String sevenDaysAgoString = dateFormat.format(cal.getTime());
		
		try
		{
			session = HibernateUtilsAnnot.currentSession();
			Date sevenDaysAgo=new SimpleDateFormat("yyyy-MM-dd").parse(sevenDaysAgoString);
			
			Criteria cr = session.createCriteria( DashBoardTicketsCount.class );
			cr.add(Restrictions.eq("dateInsert", sevenDaysAgo));
			
			
			List<DashBoardTicketsCount> list = cr.list();
			if(list.size()==1)
			{
				dtc = list.get(0);
			}
			else
			{
				return null;
			}
			
			
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
		catch(ParseException e)
		{
			e.printStackTrace();
			
		}

		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		
		return dtc;
	}

	public LineChartModel populateStudiesPerDayChart(LineChartModel studiesModel)
	{
		
		Session session=null;
		List<DashBoardTicketsCount> list = new ArrayList<DashBoardTicketsCount>();
		Calendar cal = new GregorianCalendar();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		cal.add(Calendar.DAY_OF_MONTH, -10);
		String oldDateString = dateFormat.format(cal.getTime());
		
		try
		{
			Date oldDate=new SimpleDateFormat("yyyy-MM-dd").parse(oldDateString);
			
			LineChartSeries series1 = new LineChartSeries();
	        series1.setLabel("Studies");
	        
			session = HibernateUtilsAnnot.currentSession();
			Criteria cr = session.createCriteria(DashBoardTicketsCount.class);
			cr.add(Restrictions.ge("dateInsert", oldDate));
			cr.addOrder(Order.asc("dateInsert"));
			list = cr.list();
			Integer largestValue=0;
			for(DashBoardTicketsCount dtc:list)
			{
				Date xDate = dtc.getDateInsert();
				String x = dateFormat.format(xDate);				
				Integer y = dtc.getStudyGrowth();
				series1.set(x,y);
				if(y>largestValue)
				{
					largestValue=y;
				}
			}
			
			studiesModel.addSeries(series1);
			
//			XAxis
			DateAxis axis = new DateAxis("Dates");
			axis.setTickAngle(-50);
			String minDate = dateFormat.format(cal.getTime());
			axis.setMin(minDate);
			cal = new GregorianCalendar();
			String maxDate = dateFormat.format(cal.getTime());
			axis.setMax(maxDate);
			axis.setTickFormat("%#d %b");
			studiesModel.getAxes().put(AxisType.X, axis);
			
//			YAxis
			Axis yAxis = studiesModel.getAxis(AxisType.Y);
			yAxis.setLabel("No of Studies Received");
			yAxis.setTickFormat("%#d");
			yAxis.setMin(0);			
			yAxis.setMax(largestValue+1);
//			yAxis.setTickInterval("1");
			
			
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			HibernateUtilsAnnot.closeSession();
		}
		catch(ParseException e)
		{
			e.printStackTrace();
			HibernateUtilsAnnot.closeSession();
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		return studiesModel;
	}
	
	
	public LineChartModel populateDataGrowthPerDayChart(LineChartModel dataGrowthModel)
	{
		
		Session session=null;
		List<DashBoardTicketsCount> list = new ArrayList<DashBoardTicketsCount>();
		Calendar cal = new GregorianCalendar();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		cal.add(Calendar.DAY_OF_MONTH, -10);
		String oldDateString = dateFormat.format(cal.getTime());
		
		try
		{
			Date oldDate=new SimpleDateFormat("yyyy-MM-dd").parse(oldDateString);
			
			LineChartSeries series1 = new LineChartSeries();
	        series1.setLabel("Storage Growth");
	        
			session = HibernateUtilsAnnot.currentSession();
			Criteria cr = session.createCriteria(DashBoardTicketsCount.class);
			cr.add(Restrictions.ge("dateInsert", oldDate));
			cr.addOrder(Order.asc("dateInsert"));
			list = cr.list();
			Double largestValue=0.0;
			for(DashBoardTicketsCount dtc:list)
			{
				Date xDate = dtc.getDateInsert();
				String x = dateFormat.format(xDate);				
				Double y = dtc.getStorageGrowth()/1024.00;
				series1.set(x,y);
				if(y>largestValue)
				{
					largestValue=y;
				}
			}
			
			dataGrowthModel.addSeries(series1);
			
//			XAxis
			DateAxis axis = new DateAxis("Dates");
			axis.setTickAngle(-50);
			String minDate = dateFormat.format(cal.getTime());
			axis.setMin(minDate);
			cal = new GregorianCalendar();
			String maxDate = dateFormat.format(cal.getTime());
			axis.setMax(maxDate);
			axis.setTickFormat("%#d %b");			
			dataGrowthModel.getAxes().put(AxisType.X, axis);
			
//			YAxis
			Axis yAxis = dataGrowthModel.getAxis(AxisType.Y);
			yAxis.setLabel("Storage Growth");
//			yAxis.setTickFormat("%#d.%2f MB");
			yAxis.setMin(0);			
			yAxis.setMax(largestValue);
			
			
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			HibernateUtilsAnnot.closeSession();
		}
		catch(ParseException e)
		{
			e.printStackTrace();
			HibernateUtilsAnnot.closeSession();
		}
		finally
		{
			HibernateUtilsAnnot.closeSession();
		}
		return dataGrowthModel;
	}
	
}
