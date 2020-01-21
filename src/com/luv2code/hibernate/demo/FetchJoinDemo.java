package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;

public class FetchJoinDemo {
	
	public static void main(String args[]) {
		
		SessionFactory factory=new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.addAnnotatedClass(Course.class)
				.buildSessionFactory();
		
		Session session=factory.getCurrentSession();
				
		try {
			
			session.beginTransaction();
			
			//get the instructor from database
			int theId = 1;

			Query<Instructor> query = 
					session.createQuery("select i from Instructor i "
							+"JOIN FETCH i.courses "
							+"WHERE i.id=:theInstructorId", 
							Instructor.class);
			
			query.setParameter("theInstructorId", theId);
			
			Instructor tempInstructor=query.getSingleResult();
			
			System.out.println("luv2code: Instructor: "+tempInstructor);
			
			session.getTransaction().commit();

			session.close();

			System.out.println("\nluv2code: The session is now closed!\n");
			
			//Retrieve the courses - already loaded data before session close
			System.out.println("luv2code: Courses: "+tempInstructor.getCourses());
			
			System.out.println("luv2code: Done!");
						
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			session.close();
			factory.close();
		}
	}
}
