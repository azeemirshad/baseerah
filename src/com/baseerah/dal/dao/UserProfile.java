package com.baseerah.dal.dao;

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




@Entity
@Table(name = "user_profile")
public class UserProfile {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DOB")
	private Date dateOfBirth;
	
	@Column(name="mobile_no")
	private String phone;
	
	@Column(name = "insert_date")
	private Date insertDate;
	
	@Column(name ="IS_DELETE")
	private Integer isDelete;
	
	@Column(name="GUARDIAN")
	private String guardian;
	
	@Column(name="NATIONALITY")
	private String nationality;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="OCCUPATION")
	private String occupation;
	
	@Column(name ="IS_NAZIRA")
	private Integer isNazira;
	
	@Column(name ="IS_ARABIC")
	private Integer isArabic;
	
	@Column(name ="IS_TAJWEED")
	private Integer isTajweed;
	
	@ManyToOne 
	@JoinColumn (name = "source_id")
	private SourceType sourceType ;
	
	@OneToMany(mappedBy = "userProfile" , cascade = CascadeType.ALL)
	//@OrderBy("id DESC")
	private  List<UserEvent> userEvents;
	
    public UserProfile() {
		// TODO Auto-generated constructor stub
    	this.isDelete=0;
    	sourceType = new SourceType();
	}
    
    public UserProfile(String name,String phone){
        
        this.name=name;
        this.phone=phone;
        this.isDelete=0;
    }

   
    @Override
    public String toString()
    {
        return name+" | "+phone;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the guardian
	 */
	public String getGuardian() {
		return guardian;
	}

	/**
	 * @param guardian the guardian to set
	 */
	public void setGuardian(String guardian) {
		this.guardian = guardian;
	}

	/**
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * @param nationality the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the occupation
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * @param occupation the occupation to set
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	/**
	 * @return the isNazira
	 */
	public Integer getIsNazira() {
		return isNazira;
	}

	/**
	 * @param isNazira the isNazira to set
	 */
	public void setIsNazira(Integer isNazira) {
		this.isNazira = isNazira;
	}

	/**
	 * @return the isArabic
	 */
	public Integer getIsArabic() {
		return isArabic;
	}

	/**
	 * @param isArabic the isArabic to set
	 */
	public void setIsArabic(Integer isArabic) {
		this.isArabic = isArabic;
	}

	/**
	 * @return the isTajweed
	 */
	public Integer getIsTajweed() {
		return isTajweed;
	}

	/**
	 * @param isTajweed the isTajweed to set
	 */
	public void setIsTajweed(Integer isTajweed) {
		this.isTajweed = isTajweed;
	}

	/**
	 * @return the sourceType
	 */
	public SourceType getSourceType() {
		return sourceType;
	}

	/**
	 * @param sourceType the sourceType to set
	 */
	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}

	/**
	 * @return the userEvents
	 */
	public List<UserEvent> getUserEvents() {
		return userEvents;
	}

	/**
	 * @param userEvents the userEvents to set
	 */
	public void setUserEvents(List<UserEvent> userEvents) {
		this.userEvents = userEvents;
	}

	
}
