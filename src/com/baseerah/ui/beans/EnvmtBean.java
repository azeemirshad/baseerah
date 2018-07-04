package com.baseerah.ui.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.baseerah.utils.Environment;


@ManagedBean(name= "envmtBean")
@SessionScoped
public class EnvmtBean 
{
	private String businessClientName;
	private String applName;
	private String applFullName;
	
	private String homeImage;
	private String bannerFile;
	
	public EnvmtBean() 
	{
		// TODO Auto-generated constructor stub
	}

	
	

	public String getApplName() 
	{
		if(applName==null || applName.trim().length()<1)
		{
			this.applName=Environment.getApplName();
		}
		return applName;
	}


	public void setApplName(String applName) {
		this.applName = applName;
	}




	public String getBusinessClientName() 
	{
		if(this.businessClientName==null || businessClientName.trim().length()<1)
		{
			this.businessClientName = Environment.getBusinessClientName();
		}
		return businessClientName;
	}




	public void setBusinessClientName(String businessClientName) {
		this.businessClientName = businessClientName;
	}




	public String getApplFullName() 
	{
//		this.applFullName = getBusinessClientName()+" - "+getApplName();
		this.applFullName = getApplName();
		return applFullName;
	}




	public void setApplFullName(String applFullName) {
		this.applFullName = applFullName;
	}




	public String getHomeImage() 
	{
		if(this.homeImage==null || this.homeImage.trim().length()<1)
		{
			this.homeImage = "/images/homeImage"+getBusinessClientName()+".jpg";
			System.out.println("vlaue of homeimage is = "+this.homeImage);
		}
		return homeImage;
	}




	public void setHomeImage(String homeImage) {
		this.homeImage = homeImage;
	}



	public String getBannerFile() 
	{
		if(this.bannerFile==null || this.bannerFile.trim().length()<1)
		{
//			this.bannerFile = "/images/banner"+getBusinessClientName()+".png";
			this.bannerFile = "/images/HeadBanner.png";
			
		}
		return bannerFile;
	}




	public void setBannerFile(String bannerFile) {
		this.bannerFile = bannerFile;
	}

}
