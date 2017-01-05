/* nsimon2
 *  managed bean that contains a reference to Student model object, whose properties
     are mapped to fields on Survey page.
 */
package assignment;

import java.io.IOException;
import java.sql.Connection;

import java.sql.ResultSet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import javax.faces.context.FacesContext;

import javax.persistence.*;
import assignment.Studentjpa;

@ManagedBean


public class StudentService {

	private static final String ratingString =
			"very likely,likely,unlikely"; 
	private static final String[] ratingArray =
			ratingString.split(",");
			
	public Connection con= null;

	private WinningResult wr = new WinningResult ();
	private Student student = new Student();
	private Studentjpa sp = new Studentjpa();
	List<Student> studentmatch = new ArrayList<Student>();
	
	
	public List<Student> getStudentmatch() {
		return studentmatch;
	}

	public void setStudentmatch(List<Student> studentmatch) {
		this.studentmatch = studentmatch;
	}

	public WinningResult getWr() {
		return wr;
	}

	public void setWr(WinningResult wr) {
		this.wr = wr;
	}
 

	public Student getStudent()
	{
		return student;
	}
	
	public void setStudent(Student st){
		this.student= st;
	}
	
	 public StudentService()
	 {
		  
		  /*
		
		 try
		 {   
			 System.out.println("in dbconnection");
			 Class.forName("oracle.jdbc.driver.OracleDriver");
			   con = DriverManager.getConnection (    
			 "jdbc:oracle:thin:@apollo.ite.gmu.edu:1521:ite10g",
			 "nsimon2", "grutoo");

			  //Class.forName("com.mysql.jdbc.Driver");
			  //con = DriverManager.getConnection ("jdbc:mysql://127.0.0.1:3306/swe645?user=nsimon2&password=nopassword");
		
			
		 }
		 catch(Exception e)
		 {    e.printStackTrace();
			 System.out.println("Failed to connect to database");
		 } */
	 
	     }
	 
	 public List<String> completeRating(String ratingPrefix)
	 {
		 List<String> matches = new ArrayList<String>();
		 for(String possibleRating: ratingArray) {
		 if(possibleRating.toUpperCase().startsWith(ratingPrefix.toUpperCase())) {
		 matches.add(possibleRating);
		 }
		 }
		 return(matches);
		 
	 }
	  
	
	public String navigate() throws IOException, ParseException
	{  
		
		System.out.println("in enddate");
	
	FacesContext context = FacesContext.getCurrentInstance();
		System.out.println(student.getSemesterstartdate());
		System.out.println(student.getSurveydate());
	 if (!(student.getSurveydate().before(student.getSemesterstartdate()))) {
		 
		 FacesMessage errorMessage =
		 new FacesMessage("Semester start date must be after survey date");
		 errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
		 context.addMessage(null, errorMessage);
		 return null; 
		
   }
	 
		
	 else {
		 String[] num = student.getData().split(",");
			int[] datas = new int[num.length];
			for(int i=0;i<num.length;i++)
				datas[i]= Integer.parseInt(num[i]);
			if(num.length <10){
				FacesMessage msg = 
						new FacesMessage("Enter atleast 10 comma seperated numbers");
				      msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				      context.addMessage(null, msg);
						 return null;
			}
			for (int i=0; i<datas.length;i++)
		 	{      
		 		if(datas[i]< 1 || datas[i]> 100)
		 		{
		 		
		 			FacesMessage msg = 
		 					new FacesMessage("Enter numbers between 1 and 100");
		 			     msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		 			     context.addMessage(null, msg);
						 return null; 
		          }
		     }
			//ArrayList<String> sb = new ArrayList<String>();
			DateFormat formatter = null;
			 Student st = new Student();
			 st.setFirstName(this.student.getFirstName());
	         st.setLastName(this.student.getLastName());
	         st.setStreetAddress(this.student.getStreetAddress());
	         st.setCity(this.student.getCity());
	         st.setState(this.student.getState());
	         st.setZip(this.student.getZip());
	         st.setPhone(this.student.getPhone());
	         st.setEmail(this.student.getEmail());
	         st.setSurveydate(this.student.getSurveydate());
	          st.setSemesterstartdate(this.student.getSemesterstartdate());
	          st.setInterest(this.student.getInterest());
	          st.setRating(this.student.getRating());
	          st.setData(this.student.getData());
	          st.setComments(this.student.getComments());
	          sp.save(st);
		     compute(datas);
		
		if(wr.getMean() >90)
			return "winner";
		else
			return "thankyou";
		
	}}
	
	
	public void compute(int[] datas)
	{  
		double calc_mean;
		double calc_sd;
		calc_mean= mean(datas);
		calc_sd = deviation(datas);
		
		wr.setMean(calc_mean);
		wr.setDeviation(calc_sd);
		
		
		}

      public double mean(int[] datas){
		 int sum =0;
		 double mean=0;
		 for(int i=0;i<datas.length;i++)
		 {
			 sum = sum+ datas[i];
		 }
    	  
		 mean= sum/datas.length;
    	  return mean;
    	  
      }

   public double deviation(int[] datas){
	 
		double sum =0;
		double dev=0;
		double mean=mean(datas);
		for(int i=0;i<datas.length;i++)
		{
			sum = sum+ ((mean - datas[i]) * (mean - datas[i]));
		}
		dev= (Math.sqrt(sum / (datas.length - 1)));
		return dev;
	  
   }
   
   


	
	public List<Student> getStudents() throws ParseException
		{  
		
		List<Student> students = new ArrayList<Student>();
		 DateFormat formatter = null;
		   System.out.println("in retrieve info");
		   ArrayList<ArrayList<String>> s = new ArrayList<ArrayList<String>>();   
			 
		      s = sp.retrieve();
		      int i = 0;
				while (i < s.size()) {
					Student st = new Student();
					ArrayList<String> fr = s.get(i);
					st.setFirstName(fr.get(0));
					st.setLastName(fr.get(1));
					st.setStreetAddress(fr.get(2));
					st.setZip(fr.get(5));
					st.setCity(fr.get(3));
					st.setState(fr.get(4));
					st.setPhone(fr.get(6));
					st.setEmail(fr.get(7));
					formatter = new SimpleDateFormat(("E MMM dd HH:mm:ss Z yyyy"));
				       Date surveydate = (Date) formatter.parse(fr.get(8));
					st.setSurveydate(surveydate );
					formatter = new SimpleDateFormat(("E MMM dd HH:mm:ss Z yyyy"));
					 Date semdate = (Date) formatter.parse(fr.get(9));
					st.setSemesterstartdate(semdate ); 
					st.setInterest(fr.get(10));
					st.setRating(fr.get(11));
					st.setData(fr.get(12));
					st.setComments(fr.get(13));
					st.setSid(Integer.parseInt(fr.get(14)));
					i++;
					students.add(st);
				}
		 
		        return students;
		}
		  
		      

	
	public void delete(int sid) {
		try {
			
			sp.del(sid);
			search();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
		
	public String search() throws ParseException
	{   
		studentmatch.clear();
		DateFormat formatter = null;
		ArrayList<ArrayList<String>> s = new ArrayList<ArrayList<String>>();
		s=sp.searchlist(this.student.getFirstName(),this.student.getLastName(),
				this.student.getCity(),this.student.getState());
		if (s.size()==0){
		return "Nomatch";
		}
		 int i = 0;
		 
			while (i < s.size()) {
				
				Student st = new Student();
				ArrayList<String> fr = s.get(i);
				st.setFirstName(fr.get(0));
				st.setLastName(fr.get(1));
				st.setStreetAddress(fr.get(2));
				st.setZip(fr.get(5));
				st.setCity(fr.get(3));
				st.setState(fr.get(4));
				st.setPhone(fr.get(6));
				st.setEmail(fr.get(7));
				formatter = new SimpleDateFormat(("E MMM dd HH:mm:ss Z yyyy"));
			       Date surveydate = (Date) formatter.parse(fr.get(8));
				st.setSurveydate(surveydate );
				formatter = new SimpleDateFormat(("E MMM dd HH:mm:ss Z yyyy"));
				 Date semdate = (Date) formatter.parse(fr.get(9));
				st.setSemesterstartdate(semdate ); 
				st.setInterest(fr.get(10));
				st.setRating(fr.get(11));
				st.setData(fr.get(12));
				st.setComments(fr.get(13));
				st.setSid(Integer.parseInt(fr.get(14)));
				i++;
				studentmatch.add(st);
		
	}
			return "Searchmatch";
	}
	
}
	








	

