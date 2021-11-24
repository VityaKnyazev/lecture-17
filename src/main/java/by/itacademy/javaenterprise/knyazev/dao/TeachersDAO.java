package by.itacademy.javaenterprise.knyazev.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.itacademy.javaenterprise.knyazev.dao.exceptions.DAOException;
import by.itacademy.javaenterprise.knyazev.entities.Teacher;

public class TeachersDAO extends AbstractDAO<Teacher> {
	private static final String SELECT_ALL = "SELECT t FROM teacher t";
	private static final Logger logger = LoggerFactory.getLogger(TeachersDAO.class);

	public TeachersDAO(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Long save(Teacher teacher) throws DAOException {
		if (teacher != null) {
			try {
				entityManager.getTransaction().begin();
				entityManager.persist(teacher);
				entityManager.getTransaction().commit();
				return teacher.getId();
			} catch (Exception e) {
				entityManager.getTransaction().rollback();
				logger.error("Transaction on method int save(Teacher teacher) failed: " + e.getMessage()
						+ "with name of class exception: " + e.getClass().getCanonicalName(), e);
				return null;
			}
		} else {
			throw new DAOException(
					"Expected Teacher object. Null was given in method Long save(Teacher teacher)");
		}
	}

	@Override
	public Teacher find(Long id) throws DAOException {

		if (id == null || id < 0L) {
			throw new DAOException(
					"Expected Teacher object. Null or bad ID was given in method Teacher find(Long id)");
		}

		try {
			return entityManager.find(Teacher.class, id);
		} catch (IllegalArgumentException e) {
			logger.error("Can't get Teacher object on id=" + id + " on method Teacher find(Integer id): "
					+ e.getMessage() + " with exception class name: " + e.getClass().getCanonicalName(), e);
		}

		return null;
	}

	@Override
	public List<Teacher> findAll() {

		try {
			return entityManager.createNamedQuery(SELECT_ALL, Teacher.class).getResultList();
		} catch (IllegalStateException | IllegalArgumentException | PersistenceException e) {
			logger.error("Error in method List<Teacher> findAll(): " + e.getMessage() + " from class exception name: "
					+ e.getClass().getCanonicalName(), e);
		}

		return Collections.emptyList();
	}

	@Override
	public void update(Teacher teacher) throws DAOException {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(teacher);
			entityManager.getTransaction().commit();
		} catch (IllegalStateException | IllegalArgumentException | PersistenceException e) {
			entityManager.getTransaction().rollback();
			logger.error("Error in method update(Teacher teacher): " + e.getMessage() + " from class exception name: "
					+ e.getClass().getCanonicalName());
			throw new DAOException("Error can't merge object on method void update(Teacher teacher)", e);
		}
	}

	@Override
	public void delete(Teacher teacher) throws DAOException {
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(teacher);
			entityManager.getTransaction().commit();
		} catch (IllegalStateException | IllegalArgumentException | PersistenceException e) {
			entityManager.getTransaction().rollback();
			logger.error("Error in method void delete(Teacher teacher): " + e.getMessage()
					+ " from class exception name: " + e.getClass().getCanonicalName());
			throw new DAOException("Error can't remove object on method void delete(Teacher teacher)", e);
		}
	}
}