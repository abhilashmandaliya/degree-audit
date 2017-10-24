package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
	private static SessionFactory factory = null;
	
	public static Session getSession() {
		if (factory == null)
			factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		return factory.openSession();
	}
}
