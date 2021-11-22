package by.itacademy.javaenterprise.knyazev.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {
	private final static EntityManagerFactory entityManagerFactory;
	private static JpaUtil jpaUtil;
	
	static {
		entityManagerFactory = Persistence.createEntityManagerFactory("persistentContext");
	}
	
	private JpaUtil() {	}
	
	public static 	JpaUtil getInstance() {
		if (jpaUtil == null) {
			synchronized (JpaUtil.class) {
				if (jpaUtil == null) jpaUtil = new JpaUtil();
			}			
		} 
		
		return jpaUtil;
	}
	
	public EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
	
	public void closeEntityManagerFactory() {
		if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
			entityManagerFactory.close();
		}
	}
	
}
