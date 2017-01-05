/* nsimon2
 *   jpa functionalities
 */
package assignment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Studentjpa {
	private Student student = new Student();
	public Student getStudent()
	{
		return student;
	}
	
	public void setStudent(Student st){
		this.student= st;
	}
	private static final String PERSISTENCE_UNIT_NAME = "assign3";
	   private  EntityManager entityManager;
	   protected EntityManager getEntityManager() {
		    if (entityManager == null) {
		        entityManager = Persistence.createEntityManagerFactory(
		        		PERSISTENCE_UNIT_NAME).createEntityManager();
		    }
		    System.out.println("entity" + entityManager);
		    return entityManager;
		}
	   EntityTransaction tx = getEntityManager().getTransaction();
	   
	   
		
				
			 public void save(Student student) throws ParseException{
				
		        
		        try{
		        	System.out.println("in persist");
		        	
		        	tx.begin();
		            entityManager.persist(student);
		            tx.commit();
		            
		        }
		        catch (Exception e)
		        {   
		        	tx.rollback();
		        	e.printStackTrace();
		        }
			 }
		        

	public  ArrayList<ArrayList<String>>retrieve(){
		DateFormat formatter = null;
		ArrayList<ArrayList<String>> stud = new ArrayList<ArrayList<String>>(); 
		try{
			@SuppressWarnings("unchecked")
			List<Integer>students = entityManager.createQuery("select sid from Student").getResultList();
		 
			  System.out.println("stud"+ students);
		
		
		  int size = students.size();
		  int i = 0;
		  while(i< size)
	       {
	      Student sb = entityManager.find(Student.class,students.get(i));
	      System.out.println("date" + sb.getSurveydate());
	     // System.out.println("name" + sb.getFirstName());
	      ArrayList<String> temp = new ArrayList<String>();
	        temp.add(sb.getFirstName())  ;
		     
	        temp.add(sb.getLastName());
	        temp.add(sb.getStreetAddress());
	        temp.add(sb.getCity());
	        temp.add( sb.getState());
	        temp.add(sb.getZip());
	        temp.add(sb.getPhone());
	        temp.add(sb.getEmail());
	        formatter = new SimpleDateFormat(("E MMM dd HH:mm:ss Z yyyy"));
		     Date survey = sb.getSurveydate();
		     String srvydate = formatter.format(survey);
		     System.out.println("survey date"+ srvydate);
		     temp.add(srvydate);
		     formatter = new SimpleDateFormat(("E MMM dd HH:mm:ss Z yyyy"));
		     Date semsatetart = sb.getSemesterstartdate();
		     String semstartdate = formatter.format(semsatetart);
		     System.out.println("semstartdate"+ semstartdate);
		     temp.add(semstartdate);
	       
		   
		   // temp.add (sb.getLikedthings());
		    temp.add (sb.getInterest());
		    temp.add(sb.getRating());
		    temp.add(sb.getData());
		    temp.add(sb.getComments());
		    temp.add(sb.getSid()+"");
		     stud.add(temp);
		     i++;
		     
		    // System.out.println(students);
	     }
	  }
	  catch(Exception e)
	  {   e.printStackTrace();
		  System.out.println("Failed to get Studentinfo ");
	  }
	  
	return stud;
	 
	  
	}

	
	
public void del(int sid) {
		
		try{
			tx.begin();
			Student s= entityManager.find(Student.class, sid);
			entityManager.remove(s);
			tx.commit();
		}
		catch(Exception e){
			tx.rollback();
			e.printStackTrace();
		}
}
@SuppressWarnings("unchecked")
public  ArrayList<ArrayList<String>>searchlist(String firstname, String lastname, 
		                                          String city, String state){
	
	ArrayList<ArrayList<String>> stud = new ArrayList<ArrayList<String>>(); 
	DateFormat formatter = null;
	
	int fnamesize =(firstname.length()>0 ? 1:0);
	int lnamesize =(lastname.length()>0 ? 1:0);
	int citysize = (city.length()>0 ? 1:0);
	int statesize =(state.length()>0 ? 1:0);
	
	String fnameparam = (firstname.length() > 0 ? " where firstName LIKE ? " : " where firstName LIKE '%'");
	String lnameparam = (lastname.length() > 0 ? "and lastName LIKE ? " : "and lastName LIKE '%'");
    String cityparam = (city.length() > 0 ? " and city LIKE ? " : " and city LIKE '%'");
	String stateparam = (state.length() > 0 ? " and state LIKE ? " : "");
	
	Query query= entityManager.createQuery("from Student  " +fnameparam+lnameparam+cityparam+stateparam);
	
	if(fnamesize > 0){
		query.setParameter(1, firstname);
	}
	if(lnamesize > 0){
		query.setParameter(1+fnamesize, lastname);
	}
	if(citysize > 0){
		query.setParameter(1+fnamesize+lnamesize, city);
	}
	if(statesize > 0){
		query.setParameter(1+fnamesize+lnamesize+citysize, state);
	}
	  List<Student> students = query.getResultList();
	 int size = students.size();
	  int i = 0;
	  while(i< size)
      {
     Student sb = students.get(i);
     //System.out.println("date" + sb.getSurveydate());
    // System.out.println("name" + sb.getFirstName());
     ArrayList<String> temp = new ArrayList<String>();
       temp.add(sb.getFirstName())  ;
	     
       temp.add(sb.getLastName());
       temp.add(sb.getStreetAddress());
       temp.add(sb.getCity());
       temp.add( sb.getState());
       temp.add(sb.getZip());
       temp.add(sb.getPhone());
       temp.add(sb.getEmail());
       formatter = new SimpleDateFormat(("E MMM dd HH:mm:ss Z yyyy"));
	     Date survey = sb.getSurveydate();
	     String srvydate = formatter.format(survey);
	     System.out.println("survey date"+ srvydate);
	     temp.add(srvydate);
	     formatter = new SimpleDateFormat(("E MMM dd HH:mm:ss Z yyyy"));
	     Date semsatetart = sb.getSemesterstartdate();
	     String semstartdate = formatter.format(semsatetart);
	     System.out.println("semstartdate"+ semstartdate);
	     temp.add(semstartdate);
      
	   
	   // temp.add (sb.getLikedthings());
	    temp.add (sb.getInterest());
	    temp.add(sb.getRating());
	    temp.add(sb.getData());
	    temp.add(sb.getComments());
	    temp.add(sb.getSid()+"");
	     stud.add(temp);
	     i++;
	     
}
	  return stud;
}
}
