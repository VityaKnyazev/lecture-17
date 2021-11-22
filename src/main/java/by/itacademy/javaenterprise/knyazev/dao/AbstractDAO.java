package by.itacademy.javaenterprise.knyazev.dao;

import javax.persistence.EntityManager;

public abstract class AbstractDAO<T> implements DAO<T> {
	
	protected EntityManager entityManager;
	
	protected AbstractDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void closeEntityManager() {
		if (entityManager != null && entityManager.isOpen()) {
			entityManager.close();
		}
	}
}
