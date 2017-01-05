/* nsimon2 
 *The Student has properties
*(instance variables) that matches fields on Student Survey Form */
package assignment;
import java.util.Date;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.persistence.*;


@Entity
@Table(name="STUDENT")

public class Student {
	@Id
	@GeneratedValue
	@Column(name= "SID")
	private int sid;
	@Column(name= "FIRSTNAME")
	private String firstName;
	@Column(name= "LASTNAME")
	private String lastName;
	@Column(name= "ADDRESS")
	private String streetAddress;
	@Column(name= "CITY")
	private String city;
	@Column(name= "STATE")
	private String state;
	@Column(name= "ZIP")
	private String zip;
	@Column(name= "PHONE")
	private String phone;
	@Column(name= "EMAIL")
	private String email;
	@Column(name= "SURVEYDATE")
	private Date surveydate;
	@Column(name= "SEMESTERSTARTDATE")
	private Date semesterstartdate;
	
	@Column(name= "INTEREST")
	private String interest;
	@Column(name= "RATING")
	private String rating;
	@Column(name= "DATAS")
	private String data;
	@Column(name= "COMMENTS")
	private String comments;
	

	@Transient

   private List<String> likedthings;
	
	public void setFirstName(String firstName)
	{
		this.firstName= firstName;
	}
    
	public String getFirstName()
	{
		return firstName;
	}
	public void setLastName(String lastName)
	{
		this.lastName= lastName;
	}
    
	public String getLastName()
	{
		return lastName;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
		System.out.println("inzip");
		System.out.println(zip);
		
		
		Client client = ClientBuilder.newClient();
         String s = client.target("http://ec2-52-35-123-130.us-west-2.compute.amazonaws.com/nsimon2_hw4/webresources/zips/")
				   .path(this.zip)
				   .request(MediaType.TEXT_PLAIN)
				   .get(String.class);
		  System.out.println(s);
		  setCity(s.split("-")[0]);
	       setState(s.split("-")[1]);
	
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	
	public void setLikedthings(List<String> likedthings) {
		this.likedthings = likedthings;
	}
	

	public List<String> getLikedthings() {
		return likedthings;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getSurveydate() {
		return surveydate;
	}

	public void setSurveydate(Date surveydate) {
		this.surveydate = surveydate;
	}

	public Date getSemesterstartdate() {
		return semesterstartdate;
	}

	public void setSemesterstartdate(Date semesterstartdate) {
		this.semesterstartdate = semesterstartdate;
	}
	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}
}
